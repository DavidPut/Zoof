package AsyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zoof.zoofzoof.MainActivity;
import com.example.zoof.zoofzoof.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import library.UserFunctions;

/**
 * Created by David on 14-4-2015.
 */
public class LoadPicturesTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public LoadPicturesTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Log.e("test",urldisplay);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            if (mIcon11.getWidth() >= mIcon11.getHeight()){

                mIcon11 = Bitmap.createBitmap(
                        mIcon11,
                        mIcon11.getWidth()/2 - mIcon11.getHeight()/2,
                        0,
                        mIcon11.getHeight(),
                        mIcon11.getHeight()
                );

            }else{

                mIcon11 = Bitmap.createBitmap(
                        mIcon11,
                        0,
                        mIcon11.getHeight()/2 - mIcon11.getWidth()/2,
                        mIcon11.getWidth(),
                        mIcon11.getWidth()
                );
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
}


