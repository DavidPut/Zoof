package com.example.zoof.zoofzoof;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import AsyncTasks.DiscoverTagTask;
import AsyncTasks.GetWipeTimeTask;
import AsyncTasks.LoadPicturesTask;
import AsyncTasks.PhoneSaveTask;
import AsyncTasks.PictureGetTask;


public class MainActivity extends ActionBarActivity {

    private JSONObject jobj;
    private JSONArray jarr;
    private static final String TAG_PICTURES = "pictures";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_URL = "url";
    private static final String TAG_PID = "pid";
    private int []popularIDs = new int[] {R.id.popular1, R.id.popular2, R.id.popular3, R.id.popular4, R.id.popular5, R.id.popular6, R.id.popular7, R.id.popular8, R.id.popular9};

    Button btn_camera;
    Button btn_discover;
    Button btn_profile;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public TextView text;
    //private final long startTime = 86400000; // dag
    private long startTime = 20000; //

    //


    private final long interval = 1 * 1000;
    String phone_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetWipeTimeTask myTimeTask = new GetWipeTimeTask(); //
        //Run task
        myTimeTask.execute();

        JSONObject jresponse = null;
        try {
            try {
                jresponse = new JSONObject(String.valueOf(myTimeTask.get()));
                String responseString = jresponse.getString("wipetime");
                startTime = Long.parseLong(responseString);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //Unique hardware id
        phone_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //Save unique hardware id
        new PhoneSaveTask(phone_id).execute();

        //Timer
        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime / 1000));

        countDownTimer.start();
        timerHasStarted = true;

        //Search
        handleIntent(getIntent());


        //Camera
        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });


        //Profile
        btn_profile = (Button) findViewById(R.id.button_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, ProfileActivity.class);
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
                        Intent i= new Intent(MainActivity.this, TimedPhotoActivity.class);
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


        //Get task
        PictureGetTask myTask = new PictureGetTask(this); //
        //Run task
        myTask.execute();

        LinearLayout linear = (LinearLayout)findViewById(R.id.main);
        //Full json result
        try {
          jobj = myTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {

                   // Getting JSON Array
            jarr = jobj.getJSONArray(TAG_PICTURES);

            for(int i = 0 ; i < jarr.length(); i++) {
                JSONObject c = jarr.getJSONObject(i);
                // Storing  JSON item in a Variable
                String likes = c.getString(TAG_LIKES);
                String url = c.getString(TAG_URL);

//                Log.e("URL", url);
                // Moet de thumbnails inladen van alle bestanden en in d

                new LoadPicturesTask((ImageView) findViewById(popularIDs[i]))
                        .execute(url);


                //Setonclick
                ImageView img = (ImageView) findViewById(popularIDs[i]);

                JSONObject a = jarr.getJSONObject(i);
                final  String curUrl = a.getString("url");
                final String pid = a.getString(TAG_PID);

                img.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent detail = new Intent(MainActivity.this, PhotoDetailActivity.class);
                        detail.putExtra("url", curUrl);
                        detail.putExtra("id", phone_id);
                        detail.putExtra("pid", pid);
                        startActivity(detail);
                    }
                });





//              TextView valueLikes = new TextView(this);
//              valueLikes.setText(likes);
//              valueLikes.setId(i);
//
//              linear.addView(valueLikes);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Log.e("QUERY", query);

            Intent i=new Intent(this,TimedPhotoActivity.class);
            i.putExtra("tag", query);
            i.putExtra("id", phone_id);

            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
           searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

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


    // Timer object
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {

            // refresh de screen (alle elementen naar de standaard kleur)
            for(int i = 0 ; i < 9; i++) {
                ImageView img= (ImageView) findViewById(popularIDs[i]);
                img.setImageDrawable(null);
            }

            text.setText("All photos deleted");

        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText(""+String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }
    }
}
