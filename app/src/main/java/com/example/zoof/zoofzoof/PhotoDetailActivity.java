package com.example.zoof.zoofzoof;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import AsyncTasks.LoadPicturesTask;


public class PhotoDetailActivity extends ActionBarActivity {

    String url;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        //Listview
        lv = (ListView) findViewById(R.id.listView);

        //Messages
        List<String> messages = new ArrayList<String>();
        messages.add("Hallo");
        messages.add("Super vette comment");
        messages.add("Wow");
        messages.add("Such");

        //Fill list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                messages );
        lv.setAdapter(arrayAdapter);

        //Get url
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        //Load image
        ImageView image = (ImageView) findViewById(R.id.main_image);
        LoadPicturesTask loadpictures = new LoadPicturesTask((image));
        loadpictures.execute(url);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
