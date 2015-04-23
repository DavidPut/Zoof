package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.provider.Settings;
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

import java.util.concurrent.ExecutionException;

import AsyncTasks.DiscoverTagTask;
import AsyncTasks.GetProfileTask;
import AsyncTasks.UpdateProfileTask;


public class ProfileActivity extends ActionBarActivity {

    Button btn_camera;
    Button btn_discover;
    Button btn_profile;
    Button btn_upload;
    String phone_id;
    String alias;
    String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        phone_id = Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);

        GetProfileTask profileTask = new GetProfileTask(phone_id);
        profileTask.execute();

        JSONObject jresponse = null;
        try {
            try {
                jresponse = new JSONObject(String.valueOf(profileTask.get()));
                String responseString = jresponse.getString("alias");
                success = jresponse.getString("success");


                EditText aliasText;
                aliasText= (EditText)findViewById(R.id.aliasText);
                aliasText.setText(responseString);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        btn_upload = (Button) findViewById(R.id.Button_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        //Camera
        btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });


        //Profile
        btn_profile = (Button) findViewById(R.id.button_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProfileActivity.this, ProfileActivity.class);
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
                            Intent i= new Intent(ProfileActivity.this, TimedPhotoActivity.class);
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

    private void upload() {

        EditText aliasText;
        aliasText= (EditText)findViewById(R.id.aliasText);
        alias = aliasText.getText().toString();

        // Upload image to server
        new UpdateProfileTask(phone_id,alias).execute();

        if(success.equals("1")){
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            Toast.makeText(ProfileActivity.this,
                    "Alias successfully changed.", Toast.LENGTH_LONG).show();
            finish();
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            Toast.makeText(ProfileActivity.this,
                    "Something went wrong, alias not changed.", Toast.LENGTH_LONG).show();
            finish();
            startActivity(intent);
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
