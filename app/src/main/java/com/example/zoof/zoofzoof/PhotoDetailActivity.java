package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import AsyncTasks.CommentGetTask;
import AsyncTasks.DiscoverTagTask;
import AsyncTasks.GetProfileTask;
import AsyncTasks.LoadPicturesTask;


public class PhotoDetailActivity extends ActionBarActivity {

    String url;
    String phone_id;
    String picture_id;
    private ListView lv;
    Button btn_camera;
    Button btn_discover;
    Button btn_profile;
    private static final String TAG_COMMENTS = "comments";
    private static final String TAG_COMMENT = "comment";
    private String userAlias;
    private JSONObject jobj;
    private JSONArray jarr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        //Get url
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        phone_id = intent.getStringExtra("id");
        picture_id =  intent.getStringExtra("pid");


        //Execute task getcomments
        CommentGetTask comments = new CommentGetTask(phone_id, picture_id);
        comments.execute();

        //Execute task get alias
        GetProfileTask alias = new GetProfileTask(phone_id);
        alias.execute();

        JSONObject jresponse = null;
        try {
            try {
                jresponse = new JSONObject(String.valueOf(alias.get()));
                 userAlias = jresponse.getString("alias");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //Listview
        lv = (ListView) findViewById(R.id.listView);

        //Messages
        List<String> messages = new ArrayList<String>();

        try {
            jobj = comments.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {

            // Getting JSON Array
            jarr = jobj.getJSONArray(TAG_COMMENTS);

            for(int i = 0 ; i < jarr.length(); i++) {
                JSONObject c = jarr.getJSONObject(i);
                // Storing  JSON item in a Variable
                String comment = c.getString(TAG_COMMENT);
                messages.add(userAlias + " - " + comment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Fill list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                messages );
        lv.setAdapter(arrayAdapter);



        //Load image
        ImageView image = (ImageView) findViewById(R.id.main_image);
        LoadPicturesTask loadpictures = new LoadPicturesTask((image));
        loadpictures.execute(url);

        //Buttons
        //Camera
        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoDetailActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });


        //Profile
        btn_profile = (Button) findViewById(R.id.button_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PhotoDetailActivity.this, ProfileActivity.class);
                intent2.putExtra("phone_id", phone_id);
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
                        Intent i= new Intent(PhotoDetailActivity.this, TimedPhotoActivity.class);
                        i.putExtra("tag", discoverTag);
                        i.putExtra("id",phone_id );
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
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
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
