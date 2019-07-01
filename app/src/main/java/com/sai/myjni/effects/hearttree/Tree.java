package com.sai.myjni.effects.hearttree;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sai.sailib.log.DLog;

import java.util.Iterator;
import java.util.LinkedList;

public class Tree {
    //绘制树干 贝塞尔
    //绘制花瓣 曲线
    //平移动画
    //花瓣掉落

    /**
     *     树的快照图像
     */
    private  Snapshot treeSnapshot;
    private static final float CROWN_RADIUS_FACTOR = 0.35f;
    private static final float STAND_FACTOR = (CROWN_RADIUS_FACTOR / 0.28f);
    private static final float BRANCHES_FACTOR = 1.3f * STAND_FACTOR;
    private float resolutionFactor;

    private float branchesDx;
    private float branchesDy;

    private float snapshotDx;
    private float xOffset;
    private Paint snapshotPaint = new Paint();
    private final float maxXOffset;

    private enum Step{
        Draw_Tree,
        Draw_Flower,
        Draw_Move,
        Draw_Down
    }

    private static Step step = Step.Draw_Tree;

    /**
     * 储存树枝
     */
    private LinkedList<Branch> growingBranches = new LinkedList<>();

    private LinkedList<Bloom> growingBlooms = new LinkedList<>();
    private LinkedList<Bloom> cacheBlooms = new LinkedList<>();
    private static final int BLOOM_NUM = 240;
    private static final int BLOOMING_NUM = BLOOM_NUM /4 ;
    private float bloomsDx;
    private float bloomsDy;

    private LinkedList<FallingBloom> fallingBlooms = new LinkedList<>();
    private float fMaxY;


    public Tree(int canvasWidth, final int canvasHeight) {

        resolutionFactor = canvasHeight / 1080f;
        Bloom.initDisplayParam(resolutionFactor);
        CommonUtil.init(canvasHeight, CROWN_RADIUS_FACTOR);

        // snapshot
        float snapshotWidth = 816f * STAND_FACTOR * resolutionFactor;
        treeSnapshot = new Snapshot(Bitmap.createBitmap(Math.round(snapshotWidth), canvasHeight, Bitmap.Config.ARGB_8888));

        //----树干
        float branchesWidth = 375f * BRANCHES_FACTOR * resolutionFactor;
        float branchesHeight = 490f * BRANCHES_FACTOR * resolutionFactor;
        branchesDx = (snapshotWidth - branchesWidth) / 2f - 40f * STAND_FACTOR;
        branchesDy = canvasHeight - branchesHeight;
        growingBranches.add(CommonUtil.getBranchDate());


        //-----树冠
        bloomsDx = snapshotWidth / 2f;
        bloomsDy = 435f * STAND_FACTOR * resolutionFactor;
        //构建花瓣位置,颜色等
        CommonUtil.fillBlooms(cacheBlooms, BLOOM_NUM);

        maxXOffset = (canvasWidth - snapshotWidth) / 2f - 40f;


        //落
        fMaxY = canvasHeight - bloomsDy;
        CommonUtil.fillFallingBlooms(fallingBlooms, 3);

        snapshotDx = (canvasWidth - snapshotWidth) / 2f;

    }


    public void draw(Canvas canvas){
        canvas.drawColor(0xffffffee);
        canvas.save();
        canvas.translate(snapshotDx + xOffset, 0);
        switch (step) {
            case Draw_Down:
                drawSnapshot(canvas);
                doBloom(canvas);
                break;
            case Draw_Move:
                doMove();
                drawSnapshot(canvas);
                break;
            case Draw_Flower:
                doFlower();
                drawSnapshot(canvas);
                break;
            case Draw_Tree:
                doTree();
                drawSnapshot(canvas);
                break;
                default:
                    break;
        }
        canvas.restore();

    }

    private void drawSnapshot(Canvas canvas) {
        canvas.drawBitmap(treeSnapshot.bitmap, 0, 0, snapshotPaint);
    }


    private void doTree(){

        if (!growingBranches.isEmpty()) {
            LinkedList<Branch> tempBranches = null;

            //保存画布
            treeSnapshot.canvas.save();
            treeSnapshot.canvas.translate(branchesDx, branchesDy);

            Iterator<Branch> iterator = growingBranches.iterator();
            //遍历 绘制所有树枝
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                //绘制树枝  在之前的画布上绘制-treeSnapshot
                boolean grow = branch.grow(treeSnapshot.canvas, BRANCHES_FACTOR * resolutionFactor);
                //绘制完一条,移除该树枝
                if (!grow){
                    iterator.remove();

                    if (branch.childList!=null) {
                        if (tempBranches == null) {
                            tempBranches = branch.childList;
                        } else {
                            tempBranches.addAll(branch.childList);
                        }
                    }
                }
            }

            treeSnapshot.canvas.restore();

            if (tempBranches != null) {
                growingBranches.addAll(tempBranches);
            }
        }

        if (growingBranches.isEmpty()) {
            step = Step.Draw_Flower;
            DLog.e("------树干完成-------");
        }
    }
    private void doFlower(){
        //添加到花瓣集合中
        while (growingBlooms.size() < BLOOMING_NUM && !cacheBlooms.isEmpty()) {
            growingBlooms.add(cacheBlooms.pop());
        }

        Iterator<Bloom> iterator = growingBlooms.iterator();
        treeSnapshot.canvas.save();
        treeSnapshot.canvas.translate(bloomsDx, bloomsDy);
        while (iterator.hasNext()) {
            Bloom bloom = iterator.next();
            if (!bloom.grow(treeSnapshot.canvas)) {
                iterator.remove();
            }
        }
        treeSnapshot.canvas.restore();

        if (growingBlooms.isEmpty() && cacheBlooms.isEmpty()) {
            step = Step.Draw_Move;
            DLog.e("------树冠完成-------");
        }

    }
    private void doMove(){
        if (xOffset > maxXOffset) {
            step = Step.Draw_Down;
            DLog.e("------移动完成-------");
        } else {
            xOffset += 4f;
        }
    }
    private void doBloom(Canvas canvas){
        Iterator<FallingBloom> iterator = fallingBlooms.iterator();
        canvas.save();
        canvas.translate(bloomsDx, bloomsDy);
        while (iterator.hasNext()) {
            FallingBloom bloom = iterator.next();
            if (!bloom.fall(canvas, fMaxY)) {
                iterator.remove();
                CommonUtil.recycleBloom(bloom);
            }
        }
        canvas.restore();

        if (fallingBlooms.size() < 3) {
            CommonUtil.fillFallingBlooms(fallingBlooms, CommonUtil.random(1, 2));
        }

    }


}
