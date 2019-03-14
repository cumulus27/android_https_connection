package top.contrail.py.androidurl;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText aid=null;
    Button get=null;
    TextView content=null;

    private static final String TAG = "MainActivity";

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Werther werther= (Werther) msg.obj;
//            switch(msg.what){
//                case 1:
//                    tv_werther_conten.setText("时间：" + String.valueOf(werther.getHeWeather6().get(0).getUpdate().getLoc()) +
//                            "、地点：" + werther.getHeWeather6().get(0).getBasic().getLocation() +
//                            "、天气：" + werther.getHeWeather6().get(0).getNow().getCond_txt() +
//                            "、风向：" + werther.getHeWeather6().get(0).getNow().getWind_dir() +
//                            "、气温：" + werther.getHeWeather6().get(0).getNow().getTmp());
//                    break;
//            }
//            Log.d(TAG, "onResponse: " + String.valueOf(werther.getHeWeather6().get(0).getUpdate().getLoc()));
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aid = (EditText) findViewById(R.id.aidText);
        get = (Button) findViewById(R.id.getInfo);
        content=(TextView) findViewById(R.id.aidInfo);

        content.setMovementMethod(ScrollingMovementMethod.getInstance());

        get.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {

                String base_url = getResources().getString(R.string.URL_UP_ALBUM);
                String aid_number = aid.getText().toString();
                String cookies = getResources().getString(R.string.COOKIES);

                GetInfo task = new GetInfo(aid_number);
//                task.set_parameter(base_url, cookies, handler);
                task.send_request();
                task.analyse_result();
                String info = task.get_response();
                content.setText(info);

//                MyAsyncTask task = new MyAsyncTask();
//                task.execute(base_url, aid_number, cookies);

            }

        });

    }

}
