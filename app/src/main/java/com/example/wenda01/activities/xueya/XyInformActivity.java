package com.example.wenda01.activities.xueya;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenda01.R;
import com.example.wenda01.activities.speak.BaseSpeakActivity;
import com.example.wenda01.beans.xy.XyResult;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import okhttp3.FormBody;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XyInformActivity extends BaseSpeakActivity implements View.OnClickListener {
    private TextView text_xy;
    private Button buttonSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xy_inform);
        preWork();
    }

    private void preWork(){
        text_xy=findViewById(R.id.text_xy);
        buttonSub=findViewById(R.id.button_xsubmit);
        buttonSub.setOnClickListener(this);
    }

    public void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                    URL url=new URL("http://miaolangzhong.com/erzhentang/saas100Business/bpXunzheng.do?requestType=1&zhenghou=肾阳虚");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try{
                            reader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType","1")
                            .add("zhenghou","肾阳虚")
                            .build();

                    Request request=new Request.Builder()
                            .url("http://miaolangzhong.com/erzhentang/saas100Business/bpXunzheng.do")
                            .post(requestBody)
                            .build();

//                    Request request=new Request.Builder()
//                            .url("http://miaolangzhong.com/erzhentang/saas100Business/bpXunzheng.do?requestType=1&zhenghou=肾阳虚")
//                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);
//                    parseJSONWithGSON("["+responseData+"]");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){

//        Toast.makeText(XyInformActivity.this,"abc",Toast.LENGTH_SHORT).show();
        Gson gson=new Gson();
//        List<XyResult> list=gson.fromJson(jsonData,new TypeToken<List<XyResult>>(){}.getType());
        XyResult xyResult=gson.fromJson(jsonData,XyResult.class);
//        XyResult xyResult=gson.fromJson(jsonData,new TypeToken<XyResult>(){}.getType());

//        for(XyResult xyResult:list){
//            Log.d("XyInforma","ggg");
//            Log.d("XyInforma",xyResult.getMsg());
//            Toast.makeText(XyInformActivity.this,xyResult.getMsg(),Toast.LENGTH_SHORT).show();

//        }
        Toast.makeText(XyInformActivity.this,xyResult.getMsg(),Toast.LENGTH_SHORT).show();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text_xy.setText(response);
                parseJSONWithGSON(response);
//                parseJSONWithGSON("["+response+"]");

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_xsubmit:
//                sendRequestWithHttpURLConnection();
                sendRequestWithOkHttp();
                break;

        }
    }
}
