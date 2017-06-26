package com.volokh.danylo.videolist.video_list_demo.adapter.items;

import android.app.Activity;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;

import java.io.IOException;

public class ItemFactory {

    // (ItemFactory.createItemFromAsset("Obama for Hope", "video_sample_1.mp4", R.drawable.video_sample_1_pic, getActivity(), mVideoPlayerManager));

    public static BaseVideoItem createItemFromAsset(String channelTitle, String assetName, int imageResource, Activity activity, VideoPlayerManager<MetaData> videoPlayerManager, String place, boolean isOpen) throws IOException {
        if(assetName.contains("http://"))
            return new AssetVideoItem(channelTitle, assetName, null, videoPlayerManager, channelTitle, Picasso.with(activity), imageResource, place, isOpen);
        else
            return new AssetVideoItem(assetName, "", activity.getAssets().openFd(assetName), videoPlayerManager, channelTitle, Picasso.with(activity), imageResource, place, isOpen);

    }
}
