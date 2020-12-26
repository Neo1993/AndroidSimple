package com.example.androidsimple.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.androidsimple.R;
import com.example.androidsimple.base.BaseActivity;

public class PushActivity extends BaseActivity {
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

}
