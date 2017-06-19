package com.volokh.danylo.videolist.demo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.volokh.danylo.videolist.R;
import com.volokh.danylo.videolist.video_list_demo.fragments.VideoListFragment;
import com.volokh.danylo.videolist.video_list_demo.fragments.VideoRecyclerViewFragment;
import com.volokh.danylo.videolist.video_player_manager_demo.fragments.VideoPlayerManagerFragment;
import com.volokh.danylo.videolist.visibility_demo.fragments.VisibilityUtilsFragment;

/**
 * This activity contains a fragment and gives the switch option between two fragments.
 * 1. {@link VideoRecyclerViewFragment}
 * 2. {@link VideoListFragment}
 */
public class VideoListActivity extends ActionBarActivity implements VisibilityUtilsFragment.VisibilityUtilsCallback {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_list);

        // Set a ToolBar to replace the ActionBar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MobJobs");

        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new VideoRecyclerViewFragment())
                    .commit();
        }
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }
}
