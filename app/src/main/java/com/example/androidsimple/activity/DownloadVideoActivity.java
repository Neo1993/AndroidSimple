package com.example.androidsimple.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsimple.R;
import com.example.androidsimple.adapter.DownloadVideoAdapter;
import com.example.androidsimple.base.BaseActivity;
import com.jeffmony.downloader.VideoDownloadManager;
import com.jeffmony.downloader.listener.DownloadListener;
import com.jeffmony.downloader.listener.IDownloadInfosCallback;
import com.jeffmony.downloader.model.VideoTaskItem;
import com.jeffmony.downloader.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 视频下载页面
 */
public class DownloadVideoActivity extends BaseActivity {
    public final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private DownloadVideoAdapter adapter = new DownloadVideoAdapter();
    private VideoTaskItem[] items = new VideoTaskItem[7];
    private long mLastProgressTimeStamp;
    private long mLastSpeedTimeStamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_video);
        initData();
        initView();
        addListener();
    }

    private void initData(){
        VideoTaskItem item1 = new VideoTaskItem("https://v3.dious.cc/20201224/v04Vp1ES/index.m3u8", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test1", "group-1");
        VideoTaskItem item2 = new VideoTaskItem("https://v3.dious.cc/20201224/6Q1yAHRu/index.m3u8", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test2", "group-1");
        VideoTaskItem item3 = new VideoTaskItem("https://v3.dious.cc/20201224/aQKzuq6G/index.m3u8", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test3", "group-1");
        VideoTaskItem item4 = new VideoTaskItem("https://v3.dious.cc/20201224/WWTyUxS6/index.m3u8", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test3", "group-1");
        VideoTaskItem item5 = new VideoTaskItem("http://videoconverter.vivo.com.cn/201706/655_1498479540118.mp4.main.m3u8", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test4", "group-2");
        VideoTaskItem item6 = new VideoTaskItem("https://europe.olemovienews.com/hlstimeofffmp4/20210226/fICqcpqr/mp4/fICqcpqr.mp4/master.m3u8", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test5", "group-2");
        VideoTaskItem item7 = new VideoTaskItem("https://rrsp-1252816746.cos.ap-shanghai.myqcloud.com/0c1f023caa3bbefbe16a5ce564142bbe.mp4", "https://i.loli.net/2021/04/18/WuAUZc85meB6D2Q.jpg", "test6", "group-2");

        items[0] = item1;
        items[1] = item2;
        items[2] = item3;
        items[3] = item4;
        items[4] = item5;
        items[5] = item6;
        items[6] = item7;
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        adapter.setList(Arrays.asList(items));
        VideoDownloadManager.getInstance().setGlobalDownloadListener(mListener);
        VideoDownloadManager.getInstance().fetchDownloadItems(mInfosCallback);
    }

    private void addListener(){
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            VideoTaskItem item = adapter.getItem(position);
            if (item.isInitialTask()) {
                VideoDownloadManager.getInstance().startDownload(item);
            } else if (item.isRunningTask()) {
                VideoDownloadManager.getInstance().pauseDownloadTask(item.getUrl());
            } else if (item.isInterruptTask()) {
                VideoDownloadManager.getInstance().resumeDownload(item.getUrl());
            }
        });
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()){
                case R.id.download_play_btn:        //点击播放
                    break;
            }
        });
    }

    private DownloadListener mListener = new DownloadListener() {

        @Override
        public void onDownloadDefault(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadDefault: " + item);
            notifyChanged(item);
        }

        @Override
        public void onDownloadPending(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadPending: " + item);
            notifyChanged(item);
        }

        @Override
        public void onDownloadPrepare(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadPrepare: " + item);
            notifyChanged(item);
        }

        @Override
        public void onDownloadStart(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadStart: " + item);
            notifyChanged(item);
        }

        @Override
        public void onDownloadProgress(VideoTaskItem item) {
            long currentTimeStamp = System.currentTimeMillis();
            if (currentTimeStamp - mLastProgressTimeStamp > 1000) {
                LogUtils.w(TAG, "onDownloadProgress: " + item.getPercentString() + ", curTs=" + item.getCurTs() + ", totalTs=" + item.getTotalTs());
                notifyChanged(item);
                mLastProgressTimeStamp = currentTimeStamp;
            }
        }

        @Override
        public void onDownloadSpeed(VideoTaskItem item) {
            long currentTimeStamp = System.currentTimeMillis();
            if (currentTimeStamp - mLastSpeedTimeStamp > 1000) {
                notifyChanged(item);
                mLastSpeedTimeStamp = currentTimeStamp;
            }
        }

        @Override
        public void onDownloadPause(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadPause: " + item.getUrl());
            notifyChanged(item);
        }

        @Override
        public void onDownloadError(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadError: " + item.getUrl());
            notifyChanged(item);
        }

        @Override
        public void onDownloadSuccess(VideoTaskItem item) {
            LogUtils.w(TAG,"onDownloadSuccess: " + item);
            notifyChanged(item);
        }
    };

    private void notifyChanged(final VideoTaskItem item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<adapter.getItemCount(); i++){
                    VideoTaskItem videoTaskItem = adapter.getItem(i);
                    if(videoTaskItem.equals(item)){
                        adapter.setData(i, item);
                    }
                }
            }
        });
    }

    private IDownloadInfosCallback mInfosCallback = new IDownloadInfosCallback() {
                @Override
                public void onDownloadInfos(List<VideoTaskItem> items) {
                    for (VideoTaskItem item : items) {
                        notifyChanged(item);
                    }
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoDownloadManager.getInstance().removeDownloadInfosCallback(mInfosCallback);
    }

}
