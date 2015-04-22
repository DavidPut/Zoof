package AsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import library.UserFunctions;

public class UpdateProfileTask extends AsyncTask<Void, Void, JSONObject> {


    private String phone_id;
    private String alias;
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public UpdateProfileTask(String id, String new_alias){
        phone_id = id;
        alias = new_alias;

    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.update_profile(phone_id, alias);

        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }

}
