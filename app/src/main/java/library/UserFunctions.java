package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {

    private JSONParser jsonParser;

    //URL
    private static String URL = "http://zoofzoof.nl/api/";
    //pictures get tag
    private static String tag_pictures_get = "getPictures";
    private static String tag_pictures_post = "postPicture";

    //pictures upload
    private String TAG_UPLOAD = "upload_picture";

    //save phone
    private String TAG_PHONE = "save_phone";


    //random
    private String TAG_RANDOM = "picture_random";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }


    /**
     * function make Picture Request
     * */
    public JSONObject getPictures(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", tag_pictures_get));
        JSONObject json = jsonParser.getJSONFromUrl(URL, params);

        // return json
        return json;
    }

    public JSONObject uploadPictures(String ba, String value){
        // Building Parameters
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", TAG_UPLOAD));
        params.add(new BasicNameValuePair("base64", ba));
        params.add(new BasicNameValuePair("tag_value", value));
        JSONObject json = jsonParser.getJSONFromUrl(URL, params);

        return json;
    }

    public JSONObject savePhone(String id){
        // Building Parameters
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", TAG_PHONE));
        params.add(new BasicNameValuePair("id", id));
        JSONObject json = jsonParser.getJSONFromUrl(URL, params);

        return json;
    }

    public JSONObject randomPicture(String id, String search_tag){
        // Building Parameters
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", TAG_RANDOM));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("search_tag", search_tag));
        JSONObject json = jsonParser.getJSONFromUrl(URL, params);

        return json;
    }



}