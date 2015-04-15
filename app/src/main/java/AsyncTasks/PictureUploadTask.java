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

import java.util.ArrayList;

/**
 * Created by David on 15-4-2015.
 */
public class PictureUploadTask extends AsyncTask<Void, Void, String> {

    public static String URL = "http://zoofzoof.nl/pictures/";
    private String ba;
    private CameraActivity activity;

    private ProgressDialog pd = new ProgressDialog(activity);

    public PictureUploadTask(String ba1)
    {
       ba = ba1;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Wait image uploading!");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("base64", ba));
        nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String st = EntityUtils.toString(response.getEntity());
            Log.v("log_tag", "In the try Loop" + st);

        } catch (Exception e) {
            Log.v("log_tag", "Error in http connection " + e.toString());
        }
        return "Success";

    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pd.hide();
        pd.dismiss();
    }
}
