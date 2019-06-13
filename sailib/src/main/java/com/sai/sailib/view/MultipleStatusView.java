package com.sai.sailib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sai.sailib.R;
import com.sai.sailib.log.DLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 多种 View 状态
 *  空 View , 错误 View , 加载中, 无网络, 等
 * @author dianxiaoer
 */
public class MultipleStatusView extends RelativeLayout {
    private LayoutInflater mInflater;
    private  Context context;

    private static final int NULL_RESOURCE_ID = -1;

    private int vEmptyViewId, mContentViewResId, mNoNetWorkViewResId,mErrorViewResId ,mLoadingViewResId;
    private View vEmptyView, vContentView, vNoNetWorkView,vErrorView ,vLoadingView;

    private int mViewStatus;
    public static final int STATUS_CONTENT    = 0x00;
    public static final int STATUS_LOADING    = 0x01;
    public static final int STATUS_EMPTY      = 0x02;
    public static final int STATUS_ERROR      = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;


    private OnClickListener mOnRetryClickListener;

    /**
     * 记录各种状态view 的 id,在显示正常内容时,隐藏
     */
    private final ArrayList<Integer> mOtherIds = new ArrayList<>();
    private HashMap<String, View> mOtherView = new HashMap<>();
    /**
     * 默认布局参数
     */
    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS =
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);



    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs,defStyleAttr);
    }

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    private void init(AttributeSet attrs,int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);
        vEmptyViewId = array.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.empty_view);
        mContentViewResId = array.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID);
        mNoNetWorkViewResId = array.getResourceId(R.styleable.MultipleStatusView_noNetWork,R.layout.no_network_view);
        mErrorViewResId = array.getResourceId(R.styleable.MultipleStatusView_errorView,R.layout.error_view);
        mLoadingViewResId = array.getResourceId(R.styleable.MultipleStatusView_loadingView,R.layout.loading_view);

        array.recycle();
        mInflater = LayoutInflater.from(getContext());
    }


    /**
     * 当我们的XML布局被加载完后，
     * 就会回调onFinshInfalte这个方法，
     * 在这个方法中我们可以初始化控件和数据。
     */
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        showContent();
//    }

    /**
     *  获取当前状态
     *  STATUS_EMPTY 空
     *  STATUS_ERROR 错误
     *  STATUS_LOADING 加载中
     *  STATUS_NO_NETWORK 无网络
     *  STATUS_CONTENT 显示内容
     */
    private int getViewState(){
        return mViewStatus;
    }

    public final void showNoNetwork() {
        showNoNetwork(mNoNetWorkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图
     * @param layoutId 自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showNoNetwork(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示无网络视图
     * @param view 自定义视图
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No network view is null!");
        DLog.e("---","网络 id=>"+ view.getId());
        mViewStatus = STATUS_NO_NETWORK;
        if (null == vNoNetWorkView) {
            vNoNetWorkView = view;
            if (null != mOnRetryClickListener ) {
                vNoNetWorkView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(vNoNetWorkView.getId());
            mOtherView.put("vNoNetWorkView",vNoNetWorkView);
            addView(vNoNetWorkView, 0, layoutParams);
        }
        showView("vNoNetWorkView");
//        showViewById(vNoNetWorkView.getId());
    }

    public final void showErrorView(int layoutId, ViewGroup.LayoutParams params){
        showErrorView(inflateView(layoutId),params);
    }
    public final void showErrorView(){
        showErrorView(mErrorViewResId,DEFAULT_LAYOUT_PARAMS);
    }
    public final void showErrorView(View view,ViewGroup.LayoutParams params){
        checkNull(view, "----->>>> MultipleStatusView # error view is null!");
        mViewStatus = STATUS_ERROR;
        DLog.e("---","错误 id=>"+ view.getId());
        if (null == vErrorView) {
            vErrorView = view;
            if (null != mOnRetryClickListener ) {
                vErrorView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(vErrorView.getId());
            mOtherView.put("vErrorView",vErrorView);
            addView(vErrorView,0, params);
        }
        showView("vErrorView");
//        showViewById(vErrorView.getId());
    }

    public final void showLoadingView(){
        showLoadingView(mLoadingViewResId,DEFAULT_LAYOUT_PARAMS);
    }
    public final void showLoadingView(int layoutId,ViewGroup.LayoutParams params){
        showLoadingView(inflateView(layoutId),params);
    }
    public final void showLoadingView(View view,ViewGroup.LayoutParams params){
        checkNull(view, "----->>>> MultipleStatusView # loading view is null!");
        mViewStatus = STATUS_LOADING;

        if (null == vLoadingView) {
            vLoadingView = view;
            mOtherIds.add(vLoadingView.getId());
            mOtherView.put("vLoadingView",vLoadingView);
            addView(vLoadingView,0, params);
        }
//        showViewById(vLoadingView.getId());
        showView("vLoadingView");
    }

    /**
     * 显示空视图
     */
    public final void showEmptyView() {
        showEmptyView(vEmptyViewId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图
     * @param layoutId 自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showEmptyView(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showEmptyView(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示空布局
     * @param view 自定义视图
     * @param layoutParams 布局参数
     */
    public final void showEmptyView(View view, ViewGroup.LayoutParams layoutParams){
        checkNull(view, "----->>>> MultipleStatusView # Empty view is null!");
        //设置状态
        mViewStatus = STATUS_EMPTY;
        //显示空布局
        if (null == vEmptyView) {
            vEmptyView = view;
            //设置点击事件 点击重新加载
            if (null != mOnRetryClickListener ) {
                vEmptyView.setOnClickListener(mOnRetryClickListener);
            }

            //添加到根布局
            mOtherIds.add(vEmptyView.getId());
            mOtherView.put("vEmptyView",vEmptyView);

            addView(vEmptyView, 0, layoutParams);
        }
        showView("vEmptyView");
//        showViewById(vEmptyView.getId());
    }

    /**
     * 找到 id 对应的 view 确保显示状态
     * @param viewId view id
     */
    private void showViewById(int viewId) {

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }
    private void showView(String viewName){

        View view = mOtherView.get(viewName);
        if (null !=view) {
            showViewById(view.getId());
        }

        for (Map.Entry<String, View> entry : mOtherView.entrySet()) {
            if (entry.getKey().equals(viewName)) {
                entry.getValue().setVisibility(VISIBLE);
            }else {
                entry.getValue().setVisibility(GONE);
            }
        }

    }


    /**
     * 隐藏 所有状态的 view 显示内容 view
     */
    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : View.VISIBLE);
        }

//        for (Map.Entry<String, View> entry : mOtherView.entrySet()) {
//            entry.getValue().setVisibility(GONE);
//        }
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == vContentView && mContentViewResId != NULL_RESOURCE_ID) {
            vContentView = mInflater.inflate(mContentViewResId, null);
            addView(vContentView, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }


    /**
     * 检测是否为空
     * @param object
     * @param hint
     */
    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }
    /**
     * 设置 点击事件 重试点击事件
     * @param mOnRetryClickListener 点击事件
     */
    public void setOnRetryClickListener(OnClickListener OnRetryClickListener) {
        this.mOnRetryClickListener = mOnRetryClickListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear(vEmptyView, vLoadingView, vErrorView, vNoNetWorkView);
        if (null != mOtherIds) {
            mOtherIds.clear();
        }
        if (null != mOnRetryClickListener) {
            mOnRetryClickListener = null;
        }
        mInflater = null;
    }
    private void clear(View... views) {
        if (null == views) {
            return;
        }
        try {
            for (View view : views) {
                if (null != view) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
