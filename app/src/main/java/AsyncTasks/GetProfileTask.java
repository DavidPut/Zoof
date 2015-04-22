package AsyncTasks;

import android.os.AsyncTask;
import org.json.JSONObject;
import library.UserFunctions;

public class GetProfileTask extends AsyncTask<Void, Void, JSONObject> {

    private String phone_id;
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public GetProfileTask(String id){
        phone_id = id;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.get_profile(phone_id);

        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }

}
