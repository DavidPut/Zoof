package AsyncTasks;
import android.os.AsyncTask;
import org.json.JSONObject;
import library.UserFunctions;

/**
 * Created by David on 15-4-2015.
 */
public class CommentUploadTask extends AsyncTask<Void, Void, JSONObject> {

    private String phone_id;
    private String picture_id;
    private String comment;

    public CommentUploadTask(String id , String pid, String cmnt)
    {
        phone_id = id;
        picture_id = pid;
        comment = cmnt;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.uploadComment(phone_id, picture_id, comment);

        return json;

    }

    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

    }


}
