package AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.zoof.zoofzoof.MainActivity;

import org.json.JSONObject;

import library.UserFunctions;

/**
 * Created by David on 14-4-2015.
 */
public class PictureGetTask extends AsyncTask<String, Void, JSONObject> {

    private MainActivity activity;
    private static String KEY_SUCCESS = "success";
    private int responseCode = 0;

    public PictureGetTask(MainActivity activity) {
        this.activity = activity;
    }

    public PictureGetTask(Class<MainActivity> mainActivityClass) {
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.getPictures();

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json){

        if(json != null)
        {
          Log.e("Picture_get_task", json.toString());
        }

    }
}

