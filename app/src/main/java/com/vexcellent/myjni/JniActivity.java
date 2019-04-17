package com.vexcellent.myjni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JniActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        getSupportActionBar().setTitle("Jni 测试");

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
//        TextView vTextView = findViewById(R.id.textView);
        tv.setText(stringFromJNI());

//        String utils = new JniUtils().stringFromJniUtils();
//        vTextView.setText(utils);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String StrFromJni();
}
