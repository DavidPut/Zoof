package com.example.zoof.zoofzoof;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.imageView = (ImageView)this.findViewById(R.id.Picture);

        //Edit tag
        tag   = (EditText)findViewById(R.id.tag);

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
        Log.v("EditText", tag.getText().toString());
        tag_value = tag.getText().toString();

        // Upload image to server
        new PictureUploadTask(ba1, this, tag_value).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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
