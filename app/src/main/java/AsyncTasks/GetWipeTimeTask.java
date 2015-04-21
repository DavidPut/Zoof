package AsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import library.UserFunctions;

public class GetWipeTimeTask extends AsyncTask<Void, Void, JSONObject> {

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.wipeTime();

        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }

}
