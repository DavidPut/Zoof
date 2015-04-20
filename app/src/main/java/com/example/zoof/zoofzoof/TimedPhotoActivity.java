package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import AsyncTasks.LoadPicturesTask;
import AsyncTasks.RandomPictureTask;


public class TimedPhotoActivity extends ActionBarActivity {

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public TextView text;
    private final long startTime = 10 * 1000;
    private final long interval = 1 * 1000;
    Button btn_camera;
    private ImageView imageView;
    Bitmap photo;
    private String phone_id;
    private String tag;
    private JSONObject jobj;
    private static final String TAG_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_photo);


        //Get filter tag
        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");

        //Get phone_id
        String phone_id = intent.getStringExtra("id");

        //Get random info
        new RandomPictureTask(phone_id,tag).execute();

        //Set actionbar title to filtered tag
        getSupportActionBar().setTitle(tag);

        //Get task
        RandomPictureTask myTask = new RandomPictureTask(phone_id, tag); //
        //Run task
        myTask.execute();

        JSONObject jresponse = null;
        try {
            try {
                jresponse = new JSONObject(String.valueOf(myTask.get()));
                String responseString = jresponse.getString("url");
                Log.e("HALLOOOO" , responseString);
                new LoadPicturesTask((ImageView) findViewById(R.id.Picture)).execute("http://zoofzoof.nl/pictures/"+responseString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        //Timer
        setContentView(R.layout.activity_timed_photo);
        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime / 1000));

        countDownTimer.start();
        timerHasStarted = true;

        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Start camera on startup
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("next");
//
//
//            try {
//                jobj = myTask.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//
//            //moet url krijgen
//            new LoadPicturesTask((ImageView) findViewById(R.id.Picture)).execute();


        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("" + millisUntilFinished / 1000);
        }
    }




}
