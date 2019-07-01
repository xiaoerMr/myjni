package com.sai.myjni.effects.hearttree;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.LinkedList;
import java.util.Random;

public class CommonUtil {

    private final static Paint paint =new Paint();
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static float r;
    private static float c;

    public static void init(int canvasHeight, float crownRadiusFactor){
        r = canvasHeight * crownRadiusFactor;
        c = r * 1.35f;
    }

    public static Paint getPaint(){
        return  paint;
    }

    /**
     * 获取树的数据
     */
    public static Branch getBranchDate(){

        /**
         * 树干和树枝 贝塞尔 的控制点
         * 共10列，分别是id, parentId, 贝塞尔曲线控制点(3点，6列)， 最大半径， 长度
         */
        int[][] data = new int[][]{
                {0, -1, 217, 490, 252, 60, 182, 10, 30, 100},
                {1, 0, 222, 310, 137, 227, 22, 210, 13, 100},
                {2, 1, 132, 245, 116, 240, 76, 205, 2, 40},
                {3, 0, 232, 255, 282, 166, 362, 155, 12, 100},
                {4, 3, 260, 210, 330, 219, 343, 236, 3, 80},
                {5, 0, 221, 91, 219, 58, 216, 27, 3, 40},
                {6, 0, 228, 207, 95, 57, 10, 54, 9, 80},
                {7, 6, 109, 96, 65, 63, 53, 15, 2, 40},
                {8, 6, 180, 155, 117, 125, 77, 140, 4, 60},
                {9, 0, 228, 167, 290, 62, 360, 31, 6, 100},
                {10, 9, 272, 103, 328, 87, 330, 81, 2, 80}
        };

        int n = data.length;

        Branch[] branches = new Branch[n];
        for (int i = 0; i < n; i++) {
            branches[i] = new Branch(data[i]);
            int parent = data[i][1];
            if (parent != -1) {
                branches[parent].addChild(branches[i]);
            }
        }
        return branches[0];
    }


    private static final float SCALE_FACTOR = 10f;
    private static Path PATH = new Path();
    private static final float RADIUS = 18 * SCALE_FACTOR;

    public static float getRadius(){
        return RADIUS;
    }
    /**
     * 获取心形路径
     * @return 心形路径
     */
    public static Path getHeartPath(){
        int n = 101;
        Dot[] points = new Dot[n];
        float t = 0f;
        float d = (float) (2 * Math.PI / (n - 1));
        for (int i = 0; i < n; i++) {
            float x = (float) (16 * Math.pow(Math.sin(t), 3));
            float y = (float) (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t));
            points[i] = new Dot(SCALE_FACTOR * x  , -SCALE_FACTOR * y );
            t += d;
        }

        PATH.moveTo(points[0].x, points[0].y);
        for (int i = 1; i < n; i++) {
            PATH.lineTo(points[i].x, points[i].y);
        }
        PATH.close();

        return PATH;
    }

    public static int random(int n){
        return RANDOM.nextInt(n+1);
    }

    public static int random(int m, int n){
        int d = n - m;
        return m + RANDOM.nextInt(d+1);
    }

    public static void fillBlooms(LinkedList<Bloom> blooms, int bloomNum) {
        int n = 0;
        while (n < bloomNum) {
            float x = random(-c, c);
            float y = random(-c, c);
            if (inHeart(x, y, r)) {
                blooms.add(new Bloom(new Dot(x, -y)));
                n++;
            }
        }
    }

    public static float random(float m , float n){
        float d = n - m;
        return m + RANDOM.nextFloat() * d;
    }

    private static boolean inHeart(float px, float py, float r) {
        //  (x^2+y^2-1)^3-x^2*y^3=0
        float x = px / r;
        float y = py / r;
        float sx = x * x;
        float sy = y * y;
        float a = sx + sy - 1;
        return a * a * a - sx * sy * y < 0;
    }

    private static FallingBloom[] sRecycleBlooms = new FallingBloom[8];
    private static int p;

    public static void fillFallingBlooms(LinkedList<FallingBloom> blooms, int num) {
        int n = 0;
        while(n < num && p > 0){
            blooms.add(sRecycleBlooms[--p]);
            n++;
        }
        while(n < num){
            float x = random(-c, c);
            float y = random(-c, c);
            if (inHeart(x, y, r)) {
                blooms.add(new FallingBloom(new Dot(x, -y)));
                n++;
            }
        }
    }

    public static void recycleBloom(FallingBloom bloom){
        if(p < sRecycleBlooms.length){
            while (true){
                float x = CommonUtil.random(-c, c);
                float y = CommonUtil.random(-c, c);
                if (inHeart(x, y, r)) {
                    bloom.reset(x, -y);
                    break;
                }
            }
            sRecycleBlooms[p++] =bloom;
        }
    }
}
