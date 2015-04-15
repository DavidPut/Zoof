package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import AsyncTasks.LoadPicturesTask;
import AsyncTasks.PictureGetTask;
import library.JSONParser;


public class MainActivity extends ActionBarActivity {

    private JSONObject jobj;
    private JSONArray jarr;
    private static final String TAG_PICTURES = "pictures";
    private static final String TAG_LIKES = "likes";
    Button btn_camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        //Get task (can add params for a constructor if needed)
        PictureGetTask myTask = new PictureGetTask(this); //
        //Run task (here is where you would pass data to doInBackground())
        myTask.execute();




//        RelativeLayout relative = (RelativeLayout)findViewById(R.id.main);
//        //Full json result
//        try {
//          jobj = myTask.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        try {
//        // Getting JSON Array
//        jarr = jobj.getJSONArray(TAG_PICTURES);
//
//            for(int i = 0 ; i < jarr.length(); i++) {
//                JSONObject c = jarr.getJSONObject(i);
//                // Storing  JSON item in a Variable
//                String likes = c.getString(TAG_LIKES);
//                Log.e("LIKES:", likes);
//                //Log.e("OBJECT:", String.valueOf(c));
//
////              TextView valueLikes = new TextView(this);
////              valueLikes.setText(likes);
////              valueLikes.setId(i);
////
////              relative.addView(valueLikes);
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        new LoadPicturesTask((ImageView) findViewById(R.id.popular1))
                .execute("https://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

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
