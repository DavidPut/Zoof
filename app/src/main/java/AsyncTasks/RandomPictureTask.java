package AsyncTasks;
import android.os.AsyncTask;
import org.json.JSONObject;
import library.UserFunctions;

/**
 * Created by David on 15-4-2015.
 */
public class RandomPictureTask extends AsyncTask<Void, Void, JSONObject> {

    private String phone_id;
    private String search_tag;


    public RandomPictureTask(String id, String tag)
    {
        phone_id = id;
        search_tag = tag;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.randomPicture(phone_id, search_tag);
        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }


}
