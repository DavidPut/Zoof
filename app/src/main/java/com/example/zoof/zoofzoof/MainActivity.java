package com.example.zoof.zoofzoof;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import AsyncTasks.PictureGetTask;
import library.JSONParser;


public class MainActivity extends ActionBarActivity {

    private JSONObject jobj;
    private JSONArray jarr;
    private static final String TAG_PICTURES = "pictures";
    private static final String TAG_LIKES = "likes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get task (can add params for a constructor if needed)
        PictureGetTask myTask = new PictureGetTask(this); //
        //Run task (here is where you would pass data to doInBackground())
        myTask.execute();




        RelativeLayout relative = (RelativeLayout)findViewById(R.id.main);
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
                Log.e("LIKES:", likes);
                Log.e("OBJECT:", String.valueOf(c));

                TextView valueLikes = new TextView(this);
                valueLikes.setText(likes);
                valueLikes.setId(i);

                relative.addView(valueLikes);


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
