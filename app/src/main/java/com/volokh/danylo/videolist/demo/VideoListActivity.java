package com.volokh.danylo.videolist.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;
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

    private static final int CAPTURE_MEDIA = 368;
    private Toolbar mToolbar;
    private static Activity sActivityHandle;
    private static String sServerAddress = "";
    private static final int RESTAPIPORT = 3000;
    private static final int HTTPSERVERPORT = 3002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_list);

        // Set a ToolBar to replace the ActionBar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MobJobs");

        setSupportActionBar(mToolbar);
        sActivityHandle = this;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new VideoRecyclerViewFragment())
                    .commit();
        }
    }

    // showImagePicker is boolean value: Default is true
    public static void launchCamera() {
        new SandriosCamera(sActivityHandle, CAPTURE_MEDIA)
                .setShowPicker(true)
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH) // default is CameraConfiguration.MEDIA_ACTION_BOTH
                .enableImageCropping(true)
                .launchCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
            Toast.makeText(this, "Media captured." + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH), Toast.LENGTH_SHORT).show();
        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.set_server_addr:
                GetServerAddressFromUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    public static String GetRestApiEndPoint() {
        //return sServerAddress + RESTAPIPORT;
        return "192.168.86.102:" + RESTAPIPORT;
    }

    public static String GetHttpServerEndPoint() {
        //return sServerAddress + RESTAPIPORT;
        return "192.168.86.102:" + HTTPSERVERPORT;
    }

    private void GetServerAddressFromUser()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Server end point address");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sServerAddress = input.getText().toString();
                Toast.makeText(sActivityHandle.getBaseContext(), "Setting the rest end point to " + sServerAddress, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
