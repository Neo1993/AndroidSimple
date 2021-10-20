package com.example.androidsimple.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.androidsimple.MainActivity;
import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

public class PushActivity extends UmengNotifyClickActivity {
    public static final String TAG = PushActivity.class.getSimpleName();
    private TextView pushShowTV;

    public static void startActivity(Activity activity, Bundle bundle){
        Intent intent = new Intent(activity,PushActivity.class);
        activity.startActivity(intent,bundle);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        initView();
    }

    private void initView(){
        pushShowTV = findViewById(R.id.pushShowTV);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String data = bundle.getString("data");
            pushShowTV.setText(data);
        }
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pushShowTV.setText(body);
                }
            });
        }
    }

}
