package top.contrail.py.androidurl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText aid=null;
    Button get=null;
    TextView content=null;

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
                task.set_parameter(base_url, cookies);
                task.send_request();
                task.analyse_result();

                String info = task.get_response();

                content.setText(info);

            }

        });

    }
}
