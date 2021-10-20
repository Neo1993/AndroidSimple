
package com.example.androidsimple.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.androidsimple.R;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

public class PushActivity extends UmengNotifyClickActivity {
    private static final String TAG = PushActivity.class.getSimpleName();
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
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        final String extra = intent.getStringExtra(AgooConstants.MESSAGE_ACCS_EXTRA);
        final String ext = intent.getStringExtra(AgooConstants.MESSAGE_EXT);
        Log.i(TAG, body);
        Log.i(TAG, "extara: " + extra);
        Log.i(TAG, "ext: " + ext);
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
