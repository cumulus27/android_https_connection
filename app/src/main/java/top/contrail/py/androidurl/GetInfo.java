package top.contrail.py.androidurl;

import android.os.AsyncTask;
import android.os.Handler;
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

    private Handler handler;

    GetInfo(String aid_) {
        this.aid = aid_;
        this.result_json = null;
        this.jsonData = null;

    }

    void set_parameter(String url, String cookie, Handler handler_in) {

        this.base_url = url;
        this.cookies = cookie;
        this.handler = handler_in;

    }

    void send_request() {

        final String put_aid = this.aid;
        final String url = this.base_url;
        final String result_str;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response responses = null;

                    try {
                        responses = client.newCall(request).execute();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                        e.printStackTrace();
                    }

                    try {
//                        this.jsonData = responses.body().string();
//                    result_str = responses.body().string();
                        Log.d(TAG, responses.body().string());

                    }catch (NullPointerException e){
                        Log.e(TAG, e.toString());
                        e.printStackTrace();
                    }


//                    JSONObject Jobject = new JSONObject(this.jsonData);
//            JSONArray Jarray = Jobject.getJSONArray("employees");
//
//            for (int i = 0; i < Jarray.length(); i++) {
//                JSONObject object     = Jarray.getJSONObject(i);
//            }

//                    this.result_json = Jobject.toString();

//                }catch (JSONException e){
//                    Log.e(TAG, e.toString());
                }catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
            }
        }).start();


    }

    void analyse_result() {
//        this.result_json = result.toString();

    }

    String get_response(){
        return this.result_json;
    }
}
