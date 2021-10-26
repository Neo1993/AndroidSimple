package com.example.androidsimple.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidsimple.R;
import com.jeffmony.downloader.model.VideoTaskItem;
import com.jeffmony.downloader.model.VideoTaskState;

import org.jetbrains.annotations.NotNull;

public class DownloadVideoAdapter extends BaseQuickAdapter<VideoTaskItem, BaseViewHolder> {
    {
        addChildClickViewIds(R.id.download_play_btn);
    }

    public DownloadVideoAdapter() {
        super(R.layout.item_download_video);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, VideoTaskItem videoTaskItem) {

        holder.setText(R.id.url_text, videoTaskItem.getUrl());
        switch (videoTaskItem.getTaskState()){
            case VideoTaskState.PENDING:
            case VideoTaskState.PREPARE:
                holder.setVisible(R.id.download_play_btn, false);
                holder.setText(R.id.status_txt, "等待中");
                break;
            case VideoTaskState.START:
            case VideoTaskState.DOWNLOADING:
                holder.setText(R.id.status_txt, "下载中...");
                holder.setText(R.id.download_txt, "进度:" + videoTaskItem.getPercentString() + ", 速度:" + videoTaskItem.getSpeedString() +", 已下载:" + videoTaskItem.getDownloadSizeString());
                break;
            case VideoTaskState.PAUSE:
                holder.setVisible(R.id.download_play_btn, false);
                holder.setText(R.id.status_txt,"下载暂停, 已下载=" + videoTaskItem.getDownloadSizeString());
                holder.setText(R.id.download_txt, "进度:" + videoTaskItem.getPercentString());
                break;
            case VideoTaskState.SUCCESS:
                holder.setVisible(R.id.download_play_btn, true);
                holder.setText(R.id.status_txt, "下载完成, 总大小=" + videoTaskItem.getDownloadSizeString());
                holder.setText(R.id.download_txt, "进度:" + videoTaskItem.getPercentString());
                break;
            case VideoTaskState.ERROR:
                holder.setVisible(R.id.download_play_btn, false);
                holder.setText(R.id.status_txt, "下载错误");
                break;
            default:
                holder.setVisible(R.id.download_play_btn, false);
                holder.setText(R.id.status_txt, "未下载");
                break;
        }
    }
}
