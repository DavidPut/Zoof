package AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.zoof.zoofzoof.CameraActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

import library.UserFunctions;

/**
 * Created by David on 15-4-2015.
 */
public class PictureUploadTask extends AsyncTask<Void, Void, JSONObject> {

    public static String URL = "http://zoofzoof.nl/api";
    private String ba;
    private CameraActivity activity;
    private ProgressDialog pd;
    private String TAG_UPLOAD = "upload_picture";
//


    public PictureUploadTask(String ba1, CameraActivity activity)
    {
        Log.e("UPLOADTASK", "YEP1");
       ba = ba1;
       this.activity = activity;
      pd = new ProgressDialog(activity);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Wait image uploading!");
        pd.show();

    }

    @Override
    protected JSONObject doInBackground(Void... params) {


        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.uploadPictures(ba);

        return json;
//        try {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(URL);
//            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//            HttpResponse response = httpclient.execute(httppost);
//            String st = EntityUtils.toString(response.getEntity());
//            Log.v("log_tag", "In the try Loop" + st);
//
//        } catch (Exception e) {
//            Log.v("log_tag", "Error in http connection " + e.toString());
//        }


    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);
        pd.hide();
        pd.dismiss();
    }
}
