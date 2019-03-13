package top.contrail.py.androidurl;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class GetInfo {

    private static final String TAG = "GetInfo";

    private String aid;
    private String result_json;
    private String base_url;
    private String cookies;
    private String jsonData;

    GetInfo(String aid_) {
        this.aid = aid_;
        this.result_json = null;
        this.jsonData = null;

    }

    void set_parameter(String url, String cookie) {

        this.base_url = url;
        this.cookies = cookie;


    }

    void send_request() {

        String url = this.base_url;
        final String put_aid = this.aid;

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(this.base_url)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.jsonData = responses.body().string();
            JSONObject Jobject = new JSONObject(this.jsonData);
//            JSONArray Jarray = Jobject.getJSONArray("employees");
//
//            for (int i = 0; i < Jarray.length(); i++) {
//                JSONObject object     = Jarray.getJSONObject(i);
//            }

            this.result_json = Jobject.toString();

        }catch (JSONException e){
            Log.e(TAG, e.toString());
        }catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    void analyse_result() {
//        this.result_json = result.toString();

    }

    String get_response(){
        return this.result_json;
    }
}