package AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.zoof.zoofzoof.CameraActivity;
import com.example.zoof.zoofzoof.MainActivity;

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

    private String ba;
    private CameraActivity activity;
    private ProgressDialog pd;
    private String tag_value;



    public PictureUploadTask(String ba1, CameraActivity activity, String value)
    {
       ba = ba1;
       this.activity = activity;
       pd = new ProgressDialog(activity);
       tag_value = value;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Wait image uploading!");
        pd.show();

    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.uploadPictures(ba, tag_value);

        return json;


    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);
        pd.hide();
        pd.dismiss();

        Toast.makeText(activity, "Picture uploaded", Toast.LENGTH_SHORT).show();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }


}
