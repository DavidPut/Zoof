package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.concurrent.ExecutionException;

import AsyncTasks.DiscoverTagTask;


public class CommentActivity extends ActionBarActivity {

    private String pid;
    private String id;
    Button btn_camera;
    Button btn_discover;
    Button btn_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        id = intent.getStringExtra("id");
        Log.e("PID IS", pid);

        //Buttons
        //Camera
        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommentActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });


        //Profile
        btn_profile = (Button) findViewById(R.id.button_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(CommentActivity.this, ProfileActivity.class);
                intent2.putExtra("phone_id", id);
                startActivity(intent2);
            }
        });

        //Discover
        btn_discover = (Button) findViewById(R.id.imageButton);
        btn_discover.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                JSONObject jresponse = null;
                try {
                    try {

                        DiscoverTagTask discover = new DiscoverTagTask();
                        discover.execute();

                        jresponse = new JSONObject(String.valueOf(discover.get()));
                        String discoverTag = jresponse.getString("discover_tag");
                        Intent i= new Intent(CommentActivity.this, TimedPhotoActivity.class);
                        i.putExtra("tag", discoverTag);
                        i.putExtra("id",id );
                        startActivity(i);


                    } catch (JSONException e) {
                        e.printStackTrace();


                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
