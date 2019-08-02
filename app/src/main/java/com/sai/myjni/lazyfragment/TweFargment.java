package com.sai.myjni.lazyfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sai.myjni.R;
import com.sai.myjni.dia.DiaLogActivity;

public class TweFargment extends LazyFragment {
    private static final String TAG = "--TweFargment--";


    private static TweFargment fargment;
    public static TweFargment newInstance() {
        if (fargment == null) {
            fargment = new TweFargment();
        }
        return fargment;
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lazy, container, false);

        TextView text = view.findViewById(R.id.textView4);
        text.setText("这里是第二个");
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DiaLogActivity.class));
            }
        });
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        Log.e(TAG, "第一次可见,进行当前Fragment初始化操作");
    }
    @Override
    protected void onFragmentResume() {
        Log.e(TAG, "onFragmentResume 执行网络请求以及，UI操作");
    }
    @Override
    protected void onFragmentStop() {
        Log.e(TAG, "onFragmentStop 中断网络请求，UI操作");
    }
}
