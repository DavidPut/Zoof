package AsyncTasks;
import android.os.AsyncTask;
import org.json.JSONObject;
import library.UserFunctions;

/**
 * Created by David on 15-4-2015.
 */
public class CommentGetTask extends AsyncTask<Void, Void, JSONObject> {

    private String phone_id;
    private String picture_id;

    public CommentGetTask(String pid)
    {
         picture_id = pid;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.getComments(picture_id);

        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }


}
