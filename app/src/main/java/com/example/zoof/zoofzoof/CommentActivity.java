package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.concurrent.ExecutionException;

import AsyncTasks.CommentUploadTask;
import AsyncTasks.DiscoverTagTask;


public class CommentActivity extends ActionBarActivity {

    private String pid;
    private String id;
    private String userComment;
    Button btn_camera;
    Button btn_discover;
    Button btn_profile;
    Button btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        id = intent.getStringExtra("id");

        //Buttons
        btn_upload = (Button) findViewById(R.id.Button_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Get user comment
                EditText commentText;
                commentText= (EditText)findViewById(R.id.commentText);
                userComment = commentText.getText().toString();

                //Execute task
                CommentUploadTask comment = new CommentUploadTask(id, pid, userComment);
                comment.execute();

                JSONObject jresponse = null;
                try {
                    try {
                        jresponse = new JSONObject(String.valueOf(comment.get()));
                        String responseString = jresponse.getString("success");

                        if(responseString.equals("1")){
                            Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                            Toast.makeText(CommentActivity.this,
                                    "Comment successfully uploaded.", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                            Toast.makeText(CommentActivity.this,
                                    "Something went wrong, comment not uploaded.", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(intent);
                        }

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

        //Logo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
