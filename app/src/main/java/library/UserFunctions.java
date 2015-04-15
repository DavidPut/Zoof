package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {

    private JSONParser jsonParser;

    //URL
    private static String URL = "http://student.cmi.hro.nl/0848947/Jaar3/Zoof_api/";
    //pictures get tag
    private static String tag_pictures_get = "getPictures";
    //pictures post tag
    private static String tag_pictures_post = "postPicture";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }


    /**
     * function Picture Request
     * */
    public JSONObject getPictures(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", tag_pictures_get));
        JSONObject json = jsonParser.getJSONFromUrl(URL, params);

        // return json
        return json;
    }

//    public JSONObject addPicture(String tag, Integer likes){
//        // Building Parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", tag_pictures_post));
//        params.add(new BasicNameValuePair("tag", tag));
//        params.add(new BasicNameValuePair("likes", likes));
//
//        // getting JSON Object
//        JSONObject json = jsonParser.getJSONFromUrl(URL, params);
//        // return json
//        return json;
//    }



}