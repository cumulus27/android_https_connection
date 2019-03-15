package top.contrail.py.androidurl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.concurrent.ExecutorService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class GetInfo {

    private static final String TAG = "GetInfo";

    private String aid;
    private String base_url;
    private String result_str;
    private String cookies;

    String result_str0;
    String image_url = "";

    private Handler handler;

    GetInfo(String aid_) {
        aid_ = aid_.replace("av", "");
        this.aid = aid_;
        this.result_str = null;
    }

    void set_parameter(String url, String cookie, Handler handler_in) {

        this.base_url = url;
        this.cookies = cookie;
        this.handler = handler_in;

    }

    void send_request(ExecutorService pool) {

        final String put_aid = this.aid;
        final GetInfo this_task = this;

        Uri.Builder builder = Uri.parse(this.base_url).buildUpon();
        builder.appendQueryParameter("aid", put_aid);
        String paramUrl=builder.build().toString();
        Log.d(TAG, paramUrl);

        final String url = paramUrl;

        Thread get_thread = new Thread(new Runnable() {
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
                        String result_str = responses.body().string();
                        Log.d(TAG, result_str);

                        this_task.result_str0 = result_str;

                        Message message=new Message();
                        message.what=1;
                        message.obj=this_task;
                        handler.sendMessage(message);

                        try{
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            Log.e(TAG, e.toString());
                        }

                    }catch (NullPointerException e){
                        Log.e(TAG, e.toString());
                        e.printStackTrace();
                    }

                }catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });

//        get_thread.start();
        pool.submit(get_thread);
        Log.d(TAG, "Submit thread (send_request)");
    }

    void analyse_result(String result_s) {

        JSONObject result = null;

        try{
            result = new JSONObject(result_s);
        }catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        String lines = "";
        String[] keys = {"title", "tname", "dynamic", "videos", "desc"};

        try{

            JSONObject data = result.getJSONObject("data");

            for (String key: keys) {
                String item = data.getString(key);
                String line = String.format("%s: %s\n", key, item);
                lines = lines.concat(line);
            }

        }catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        this.result_str = lines;

    }

    String get_response(){
        return this.result_str;
    }

    String get_image_url(String result_s){

        JSONObject result = null;

        try{
            result = new JSONObject(result_s);
        }catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        String key = "pic";
        String image_url = "";

        try{

            JSONObject data = result.getJSONObject("data");
            image_url = data.getString(key);
            Log.d(TAG, image_url);

        }catch (JSONException e){
            Log.e(TAG, e.toString());
        }catch (NullPointerException e){
            Log.e(TAG, e.toString());
        }

        return image_url;

    }

    void get_image_bit(Handler image_handler){

        Log.d(TAG, "Thread start. (get_image_bit)");
        final String url = this.image_url;
        Log.d("Confirm url", url);

        try {
            Bitmap image_bit = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            Log.d(TAG, "Success get the image");

            Message message=new Message();
            message.what=1;
            message.obj=image_bit;
            image_handler.sendMessage(message);
//            image_handler.sendMessageDelayed(message, 3000);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
