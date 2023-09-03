package com.example.androidsimple;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.androidsimple.activity.PushActivity;
import com.example.androidsimple.utils.UtilsConfig;
import com.jeffmony.downloader.VideoDownloadConfig;
import com.jeffmony.downloader.VideoDownloadManager;
import com.jeffmony.downloader.common.DownloadConstants;
import com.jeffmony.downloader.utils.VideoStorageUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.io.File;
import java.util.Map;

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getName();
    @Override
    public void onCreate() {
        super.onCreate();
        UtilsConfig.context = this;
        initUMeng();
        initDownloadVideoConfig();
    }

    private void initUMeng(){
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
//        UMConfigure.init(this, "应用申请的Appkey", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "Push推送业务的secret 填充Umeng Message Secret对应信息");
        //初始化SDK
        UMConfigure.init(this, "5fd5d509498d9e0d4d8be198", "dev", UMConfigure.DEVICE_TYPE_PHONE, "d04542f93e4bfe5fbb3e93d9324fe2cc");
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

        // 选用MANUAL页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);

        //获取消息推送代理示例
//        PushAgent mPushAgent = PushAgent.getInstance(this);
////        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //服务端控制声音
//
//        setMessageHandler();
//        setNotificationClickHandler();
//
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//            }
//        });


        /**
         * 初始化厂商通道
         */
        //小米通道
        MiPushRegistar.register(this, "2882303761518896394", "5291889623394");
        //华为通道，注意华为通道的初始化参数在minifest中配置
        HuaWeiRegister.register(this);
        //魅族通道
        MeizuRegister.register(this, "填写您在魅族后台APP对应的app id", "填写您在魅族后台APP对应的app key");
        //OPPO通道
        OppoRegister.register(this, "填写您在OPPO后台APP对应的app key", "填写您在魅族后台APP对应的app secret");
        //VIVO 通道，注意VIVO通道的初始化参数在minifest中配置
        VivoRegister.register(this);
    }

    private void setMessageHandler(){
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                Log.d(TAG,"收到通知 id===" +msg.builder_id);
                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder = new Notification.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
//                                R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
//                                getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
//                                getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        PushAgent.getInstance(this).setMessageHandler(messageHandler);
    }


    private void setNotificationClickHandler(){
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
//                super.openActivity(context, msg);
                Map<String,String> extraMap = msg.extra;
                if(extraMap != null){
                    for (Map.Entry<String,String> entry : extraMap.entrySet()){
                        if (entry.getKey().equals("data")){
                            Log.d(TAG,"push data ====> " + entry.getValue());
                            Toast.makeText(context, entry.getValue(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(new Intent(context,PushActivity.class));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle bundle = new Bundle();
                            bundle.putString("data", entry.getValue());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Log.d(TAG,"custom =====>" + msg.custom);
                Log.d(TAG,"extra =====>" + msg.extra);
            }
        };

        PushAgent.getInstance(this).setNotificationClickHandler(notificationClickHandler);
    }

    private void initDownloadVideoConfig() {
        File file = VideoStorageUtils.getVideoCacheDir(this);
        if (!file.exists()) {
            file.mkdir();
        }
        VideoDownloadConfig config = new VideoDownloadManager.Build(this)
                .setCacheRoot(file.getAbsolutePath())
                .setTimeOut(DownloadConstants.READ_TIMEOUT, DownloadConstants.CONN_TIMEOUT)
                .setConcurrentCount(DownloadConstants.CONCURRENT)
                .setIgnoreCertErrors(false)
                .setShouldM3U8Merged(false)
                .buildConfig();
        VideoDownloadManager.getInstance().initConfig(config);
    }

}
