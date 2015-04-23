package com.example.zoof.zoofzoof;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;


import AsyncTasks.PictureUploadTask;
import library.Base64;


public class CameraActivity extends ActionBarActivity {

    Button btn_upload;

    private ImageView imageView;
    Bitmap photo;
    String ba1;
    EditText tag;
    String tag_value;
    String phone_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.imageView = (ImageView)this.findViewById(R.id.main_image);

        //Edit tag
        tag   = (EditText)findViewById(R.id.tag);
        tag.setText("#");

        //Start camera on startup
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 100);


        btn_upload = (Button) findViewById(R.id.Button_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    //Show picture in imageview
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 100 && resultCode == RESULT_OK ) {
                photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
            }
            else {
                //Go back to mainactivity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
    }

    private void upload() {

        //Resize picture
        if (photo.getWidth() >= photo.getHeight()){

            photo = Bitmap.createBitmap(
                    photo,
                    photo.getWidth()/2 - photo.getHeight()/2,
                    0,
                    photo.getHeight(),
                    photo.getHeight()
            );

        }else{

            photo = Bitmap.createBitmap(
                    photo,
                    0,
                    photo.getHeight()/2 - photo.getWidth()/2,
                    photo.getWidth(),
                    photo.getWidth()
            );
        }

        //Compress to base64
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeBytes(ba);

        //Get input value
       tag_value = tag.getText().toString();

        //Unique hardware id
        phone_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (tag_value.matches("")) {
            Toast.makeText(this, "You did not enter a tag", Toast.LENGTH_SHORT).show();
        }
        else{
            // Upload image to server
            new PictureUploadTask(ba1, this, tag_value, phone_id).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);

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
