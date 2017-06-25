package com.volokh.danylo.videolist.video_list_demo.adapter.items;

import android.content.res.AssetFileDescriptor;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.Config;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.video_player_manager.utils.Logger;
import com.volokh.danylo.videolist.video_list_demo.adapter.holders.VideoViewHolder;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;

public class AssetVideoItem extends BaseVideoItem{

    private static final String TAG = AssetVideoItem.class.getSimpleName();
    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;

    private final AssetFileDescriptor mAssetFileDescriptor;
    private final String mAssetUrl;
    private final String mTitle;
    private final String mChannelName;

    private final Picasso mImageLoader;
    private final int mImageResource;

    public AssetVideoItem(String title, String assetUrl, AssetFileDescriptor assetFileDescriptor, VideoPlayerManager<MetaData> videoPlayerManager, String channelName, Picasso imageLoader, int imageResource) {
        super(videoPlayerManager);
        mTitle = title;
        mAssetUrl = assetUrl;
        if (mAssetUrl.isEmpty())
            mAssetFileDescriptor = assetFileDescriptor;
        else
            mAssetFileDescriptor = null;
        mChannelName = channelName;
        mImageLoader = imageLoader;
        mImageResource = imageResource;
    }

    @Override
    public void update(int position, final VideoViewHolder viewHolder, VideoPlayerManager videoPlayerManager) {
        if(SHOW_LOGS) Logger.v(TAG, "update, position " + position);

        viewHolder.mTitle.setText(mChannelName);
        viewHolder.mCover.setVisibility(View.VISIBLE);
        mImageLoader.load(mImageResource).into(viewHolder.mCover);
    }


    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        if (mAssetUrl.isEmpty())
            videoPlayerManager.playNewVideo(currentItemMetaData, player, mAssetFileDescriptor);
        else
            videoPlayerManager.playNewVideo(currentItemMetaData, player, mAssetUrl);
    }

    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }

    @Override
    public String toString() {
        return getClass() + ", mTitle[" + mTitle + "]";
    }
}
