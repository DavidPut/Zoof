package com.example.zoof.zoofzoof;


import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import AsyncTasks.LoadPicturesTask;
import AsyncTasks.PhoneSaveTask;
import AsyncTasks.PictureGetTask;


public class MainActivity extends ActionBarActivity {

    private JSONObject jobj;
    private JSONArray jarr;
    private static final String TAG_PICTURES = "pictures";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_URL = "url";
    private int []popularIDs = new int[] {R.id.popular1, R.id.popular2, R.id.popular3, R.id.popular4, R.id.popular5, R.id.popular6, R.id.popular7, R.id.popular8, R.id.popular9};

    Button btn_camera;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public TextView text;
    private final long startTime = 86400000;

    private final long interval = 1 * 1000;
    String phone_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);

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
                // Moet de thumbnails inladen van alle bestanden en in d

                new LoadPicturesTask((ImageView) findViewById(popularIDs[i]))
                        .execute(url);
              TextView valueLikes = new TextView(this);
              valueLikes.setText(likes);
              valueLikes.setId(i);

                linear.addView(valueLikes);

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
            i.putExtra("id",phone_id);
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
            text.setText("Wipe!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText(""+String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }
    }
}
