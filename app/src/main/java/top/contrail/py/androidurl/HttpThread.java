package top.contrail.py.androidurl;

import android.os.AsyncTask;

class MyAsyncTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        String url = strings[0];
        String aid = strings[1];
        String cookies = strings[2];

//        GetInfo task = new GetInfo(aid);
//        task.set_parameter(url, cookies);
//        task.send_request();
//        task.analyse_result();
//        String info = task.get_response();
//        content.setText(info);

        return null;
    }
}
