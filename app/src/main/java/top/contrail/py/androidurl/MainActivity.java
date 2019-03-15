package top.contrail.py.androidurl;

import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText aid=null;
    Button get=null;
    TextView content=null;
    ImageView cover=null;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aid = (EditText) findViewById(R.id.aidText);
        get = (Button) findViewById(R.id.getInfo);
        content=(TextView) findViewById(R.id.aidInfo);
        cover = (ImageView) findViewById(R.id.cover);

        content.setMovementMethod(ScrollingMovementMethod.getInstance());

        final ExecutorService singlePool = Executors.newSingleThreadExecutor();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                GetInfo task = (GetInfo) msg.obj;

                String result = task.result_str0;
                task.analyse_result(result);

                String info = task.get_response();
                content.setText(info);

                task.image_url = task.get_image_url(result);
                Log.d(TAG, task.image_url);

            }
        };

        final Handler image_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap image_bit = (Bitmap) msg.obj;

                cover.setImageBitmap(image_bit);

            }
        };


        get.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {

                String base_url = getResources().getString(R.string.URL_UP_ALBUM);
                String aid_number = aid.getText().toString();
                String cookies = getResources().getString(R.string.COOKIES);

                final GetInfo task = new GetInfo(aid_number);
                task.set_parameter(base_url, cookies, handler);
                task.send_request(singlePool);

                Thread image_task = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        task.get_image_bit(image_handler);
                    }
                });

                singlePool.submit(image_task);
                Log.d(TAG, "Submit thread (image_task)");

            }

        });

    }

}
