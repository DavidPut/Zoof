package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import AsyncTasks.GetProfileTask;


public class ProfileActivity extends ActionBarActivity {

    Button btn_camera;
    Button btn_discover;
    Button btn_profile;
    String phone_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        phone_id = intent.getStringExtra("phone_id");

        GetProfileTask profileTask = new GetProfileTask(phone_id);
        profileTask.execute();

        JSONObject jresponse = null;
        try {
            try {
                jresponse = new JSONObject(String.valueOf(profileTask.get()));
                String responseString = jresponse.getString("alias");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
