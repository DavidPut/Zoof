package AsyncTasks;
import android.os.AsyncTask;
import org.json.JSONObject;
import library.UserFunctions;

/**
 * Created by David on 15-4-2015.
 */
public class DiscoverTagTask extends AsyncTask<Void, Void, JSONObject> {

    private String picture_id;


    public DiscoverTagTask()
    {

    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.discoverTag();
        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }


}
