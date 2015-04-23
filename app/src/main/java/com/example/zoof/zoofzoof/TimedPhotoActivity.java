package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.net.URL;
import java.util.concurrent.ExecutionException;

import AsyncTasks.DiscoverTagTask;
import AsyncTasks.LikePictureTask;
import AsyncTasks.LoadPicturesTask;
import AsyncTasks.RandomPictureTask;


public class TimedPhotoActivity extends ActionBarActivity {

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public TextView text;
    private final long startTime = 10 * 1000;
    private final long interval = 1 * 1000;
    Button btn_camera;
    ImageButton btn_message;
    ImageButton btn_like;
    private ImageView imageView;
    Bitmap photo;
    Button btn_discover;
    Button btn_profile;
    private String phone_id;
    private String tag;
    private JSONObject jobj;
    private static final String TAG_URL = "url";
    private RandomPictureTask myTask;
    String pid1;
    String pid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_photo);

        //Get filter tag
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

        //Get phone_id
        phone_id = intent.getStringExtra("id");
        Log.e("phone_Id" , phone_id);
        Log.e("search_tag", tag);
//
//        //Get random info
//        new RandomPictureTask(phone_id,tag).execute();

        //Set actionbar title to filtered tag
        getSupportActionBar().setTitle(tag);

        //Get task
        myTask = new RandomPictureTask(phone_id, tag);
        //Run task
        myTask.execute();

        //Buttons
        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Start camera on startup
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);

            }

        });

        btn_message = (ImageButton) findViewById(R.id.button_message);


        btn_like = (ImageButton) findViewById(R.id.button_like);


        //Timer
        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime / 1000));


        countDownTimer.start();
        timerHasStarted = true;

        JSONObject jresponse = null;
        try {
            try {
                jresponse = new JSONObject(String.valueOf(myTask.get()));
                String responseString = jresponse.getString("url");



                final String pid1 = jresponse.getString("pid");
                Log.e("PID1", pid1);
                btn_like.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //Add like
                        LikePictureTask like = new LikePictureTask(pid1);
                        like.execute();
                        btn_like.setVisibility(View.INVISIBLE);
                    }

                });

                ImageView image = (ImageView) findViewById(R.id.main_image);
                LoadPicturesTask loadpictures = new LoadPicturesTask((image));
                loadpictures.execute("http://zoofzoof.nl/pictures/" + responseString);
                btn_message.setVisibility(View.VISIBLE);
                btn_like.setVisibility(View.VISIBLE);


                btn_message = (ImageButton) findViewById(R.id.button_message);
                btn_message.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //Start comment page
                        Intent intent = new Intent(TimedPhotoActivity.this, CommentActivity.class);
                        intent.putExtra("pid", pid1);
                        intent.putExtra("id",phone_id);
                        finish();
                        startActivity(intent);

                    }

                });

            } catch (JSONException e) {
//                e.printStackTrace();
                ImageView image = (ImageView) findViewById(R.id.main_image);
                LoadPicturesTask loadpictures = new LoadPicturesTask((image));
                loadpictures.execute("http://zoofzoof.nl/pictures/default/Error.png");
                btn_message.setVisibility(View.INVISIBLE);
                btn_like.setVisibility(View.INVISIBLE);

                //Empty timer
                countDownTimer.cancel();
                text.setText("");

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //Profile
        btn_profile = (Button) findViewById(R.id.button_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TimedPhotoActivity.this, ProfileActivity.class);
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
                        Intent i= new Intent(TimedPhotoActivity.this, TimedPhotoActivity.class);
                        i.putExtra("tag", discoverTag);
                        i.putExtra("id",phone_id );

                        //Restart timer
                        countDownTimer.cancel();
                        countDownTimer.start();
                        finish();
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

    //Show picture in imageview
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("test", "komt erin bij foto");
        if (requestCode == 100 && resultCode == RESULT_OK ) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        else {
            Log.e("test", "terug");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timed_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public class MyCountDownTimer extends CountDownTimer
    {

        public MyCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            text.setText(""+0);
            RandomPictureTask myTaskAfterTimer = new RandomPictureTask(phone_id, tag); //
            //Run task
            myTaskAfterTimer.execute();

            JSONObject jresponse = null;
            try {
                try {
                    jresponse = new JSONObject(String.valueOf(myTaskAfterTimer.get()));
                    String responseString = jresponse.getString("url");
                    final String pid2 = jresponse.getString("pid");
                    Log.e("PID2", pid2);
                    btn_like.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            //Add like
                            LikePictureTask like = new LikePictureTask(pid2);
                            like.execute();

                            btn_like.setVisibility(View.INVISIBLE);
                        }

                    });

                    ImageView image = (ImageView) findViewById(R.id.main_image);
                    LoadPicturesTask loadpictures = new LoadPicturesTask((image));
                    loadpictures.execute("http://zoofzoof.nl/pictures/" + responseString);

                    btn_message.setVisibility(View.VISIBLE);
                    btn_like.setVisibility(View.VISIBLE);

                    btn_message = (ImageButton) findViewById(R.id.button_message);
                    btn_message.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            //Start comment page
                            Intent intent = new Intent(TimedPhotoActivity.this, CommentActivity.class);
                            intent.putExtra("pid", pid2);
                            intent.putExtra("id",phone_id);
                            finish();
                            startActivity(intent);

                        }

                    });

                    //Restart timer
                    countDownTimer.cancel();
                    countDownTimer.start();



                } catch (JSONException e) {
//                e.printStackTrace();
                    ImageView image = (ImageView) findViewById(R.id.main_image);
                    LoadPicturesTask loadpictures = new LoadPicturesTask((image));
                    loadpictures.execute("http://zoofzoof.nl/pictures/default/Error.png");
                     btn_message.setVisibility(View.INVISIBLE);
                    btn_like.setVisibility(View.INVISIBLE);

                    //Empty timer
                    text.setText("");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("" + millisUntilFinished / 1000);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }






}
