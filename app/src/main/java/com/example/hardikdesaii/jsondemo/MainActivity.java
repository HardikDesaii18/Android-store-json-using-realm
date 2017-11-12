package com.example.hardikdesaii.jsondemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String param1,param2,param3;
    ProgressDialog pd;
    RecyclerView recyclerView;
    DatasAdapter adapter;
    ArrayList<JSONObject> datalist=new ArrayList<JSONObject>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.jsonrecyclerview);
        Intent intent=getIntent();

        param1=intent.getStringExtra("VALUE1");
        param2=intent.getStringExtra("VALUE2");
        param3=intent.getStringExtra("VALUE3");


        startJSON();

    } // oncreate ends here

    public void startJSON()
    {
        String serverURL = "http://storekirana.easternts.com/index.php?route=webservice/account/order_history&customer_id=1&email=zilmehta07@gmail.com";

        if(!param1.equals("webservice/account/order_history"))
        {
            param1="webservice/account/order_history";
        }
        if(!(param2.equals("1")))
        {
            param2="1";
        }
        if(!(param3.equals("zilmehta07@gmail.com")))
        {
            param2="zilmehta07@gmail.com";
        }

        RequestBody requestBody = new FormBody.Builder()
                .add(Constants.ARGS1, param1)
                .add(Constants.ARGS2, param2)
                .add(Constants.ARGS3, param3)
                .build();

            try
            {
                if(isNetworkAvailable())
                {

                    pd = new ProgressDialog(MainActivity.this);
                    pd.setTitle("Loading");
                    pd.setMessage("Synchronizing Data , Please Wait");
                    pd.show();
                    post(serverURL,requestBody, new Callback()
                    {


                        @Override
                        public void onFailure(okhttp3.Call call, IOException e)
                        {

                            Log.e("onFailure", " " + e.getMessage());

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run()
                                {
                                    Toast.makeText(MainActivity.this," On failure of post method called",Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });

                        } // onFailure ends here

                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException
                        {

                            String responseFromServer = response.body().string();
                            Log.e("responseFromServer", " " + responseFromServer);


                            //parse the json
                            try
                            {
                                JSONObject obj1=new JSONObject(responseFromServer);
                                JSONArray array=obj1.getJSONArray("orders");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Log.e("1"," "+jsonObject.getString(Constants.KEY1));
                                    Log.e("2"," "+jsonObject.getString(Constants.KEY2));
                                    Log.e("3"," "+jsonObject.getString(Constants.KEY3));
                                    Log.e("4"," "+jsonObject.getString(Constants.KEY4));
                                    Log.e("5"," "+jsonObject.getInt(Constants.KEY5));
                                    Log.e("6"," "+jsonObject.getString(Constants.KEY6));
                                    Log.e("7"," "+jsonObject.getString(Constants.KEY7));
                                    datalist.add(jsonObject);
                                }
                            }
                            catch (Exception e)
                            {
                                Log.e("onResponse", "JSONException", e);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                                    adapter = new DatasAdapter(MainActivity.this, datalist);

                                    recyclerView.setAdapter(adapter);


                                    pd.dismiss();
                                }
                            });

                        } // onResonse ends here


                    }); // post method ends here

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Network Connection Unavailable",Toast.LENGTH_SHORT).show();
                }
            } // try ends here
            catch (Exception ex)
            {
                Log.e("In main body" ,""+ex);
                Toast.makeText(MainActivity.this," In main try "+ex,Toast.LENGTH_LONG).show();
            }// catch ends here
    } // start json ends here


    private final OkHttpClient client = new OkHttpClient();

    okhttp3.Call post(String url, RequestBody formBody, Callback callback) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
} // main class ends here
