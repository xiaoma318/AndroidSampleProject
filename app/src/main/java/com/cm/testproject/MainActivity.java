package com.cm.testproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv1;
    Handler backgroundHandler;
    List<MyModel> list = new ArrayList<>();
    MyListAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HandlerThread thread = new HandlerThread("background");
//        thread.start();
     //   backgroundHandler = new Handler(thread.getLooper());
        lv1 = (ListView)findViewById(R.id.lvList1);
        adapter = new MyListAdapter(this, list);
        lv1.setAdapter(adapter);

//        backgroundHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                StringBuffer buffer = new StringBuffer();
//                HttpURLConnection connection = null;
//                try {
//                    URL url = new URL("https://jsonplaceholder.typicode.com/users");
//                    connection = (HttpURLConnection) url.openConnection();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                    String string;
//                    while((string = reader.readLine()) != null){
//                        buffer.append(string);
//                        buffer.append("\n");
//                    }
//
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    connection.disconnect();
//                }
//
//                try {
//                    JSONArray jsonArray = new JSONArray(buffer.toString());
//                    for(int i=0;i<jsonArray.length();i++){
//                        JSONObject json = jsonArray.getJSONObject(i);
//                        list.add(new MyModel(json.getString("name"), json.getString("email")));
//
//                    }
//                    adapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        URL url = null;
        try {
            url = new URL("https://jsonplaceholder.typicode.com/users");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new MyFetchTask().execute(url);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class MyFetchTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            StringBuilder sb = new StringBuilder();
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str;
                while((str = reader.readLine()) != null){
                    sb.append(str);
                    sb.append("\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    list.add(new MyModel(json.getString("name"), json.getString("email") + "\n" + json.getString("phone")));
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
