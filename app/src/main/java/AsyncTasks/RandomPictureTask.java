package AsyncTasks;
import android.os.AsyncTask;
import org.json.JSONObject;
import library.UserFunctions;

/**
 * Created by David on 15-4-2015.
 */
public class RandomPictureTask extends AsyncTask<Void, Void, JSONObject> {

    private String phone_id;


    public RandomPictureTask(String id)
    {
        phone_id = id;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.savePhone(phone_id);

        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }


}
