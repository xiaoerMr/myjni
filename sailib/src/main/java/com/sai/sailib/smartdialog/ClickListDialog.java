package com.sai.sailib.smartdialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;


import com.sai.sailib.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClickListDialog extends TitleBranchDialog<ClickListDialog> {
    private List mItems = new ArrayList();
    private boolean mItemCenter = true;

    private ListView mListView;
    private ClickListAdapter mClickListAdapter;
    private OnItemClickListener mOnItemClickListener;
    private boolean isScrollBar = false;

    public ClickListDialog items(List items) {
        if (!mItems.equals(items)) {
            mItems.clear();
            mItems.addAll(items);
            applyItems(mNestedDialog);
        }
        return this;
    }

    public ClickListDialog items(Object[] items) {
        items(Arrays.asList(items));
        return this;
    }

    protected void applyItems(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        ViewGroup.LayoutParams lp = mListView.getLayoutParams();
        if (mItems.size() <= 5) {
            lp.height = ListView.LayoutParams.WRAP_CONTENT;
        } else {
            lp.height = Utils.dpToPx(50) * 5;
        }
        mListView.setLayoutParams(lp);
        mClickListAdapter.setItems(mItems);
    }

    public ClickListDialog itemCenter(boolean itemCenter) {
        mItemCenter = itemCenter;
        applyItemCenter(mNestedDialog);
        return this;
    }

    protected void applyItemCenter(AppCompatDialog dialog) {
        if (dialog != null) {
            mClickListAdapter.setItemCenter(mItemCenter);
        }
    }

    public ClickListDialog itemClickListener(OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
        return this;
    }

    @Override
    protected int provideBodyLayout() {
        return R.layout.smart_show_click_list_dialog;
    }

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        mListView = bodyViewWrapper.findViewById(R.id.smart_show_list_view);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDivider(new ColorDrawable(Color.parseColor("#cccccc")));
        mListView.setDividerHeight(Utils.dpToPx(0.5f));
        mListView.setVerticalScrollBarEnabled(isScrollBar);
        mClickListAdapter = new ClickListAdapter();
        mListView.setAdapter(mClickListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(ClickListDialog.this, position, mItems.get(position));
                } else {
                    ClickListDialog.this.dismiss();
                }
            }
        });

    }

    public ClickListDialog setShowScrollBar(boolean isScrollBar){
        this.isScrollBar = isScrollBar;
        return this;
    }

    @Override
    protected void applyBody(AppCompatDialog dialog) {
        super.applyBody(dialog);
        applyItems(dialog);
        applyItemCenter(dialog);
    }

    @Override
    protected int provideFooterLayout() {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(ClickListDialog dialog, int position, Object data);
    }
}
