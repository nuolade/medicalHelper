package com.example.wenda01;

import android.content.Context;

import com.example.wenda01.beans.tz.TzWxOlQuesResult;
import com.example.wenda01.utils.ExcelWriter;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Algo {
    /**
     * @ 获取当前手机屏幕的尺寸(单位:像素)
     */
    public  float getPingMuSize(Context mContext) {
        int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        float density = mContext.getResources().getDisplayMetrics().density;
        float xdpi = mContext.getResources().getDisplayMetrics().xdpi;
        float ydpi = mContext.getResources().getDisplayMetrics().ydpi;
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;

        // 这样可以计算屏幕的物理尺寸
        float width2 = (width / xdpi)*(width / xdpi);
        float height2 = (height / ydpi)*(width / xdpi);

        return (float) Math.sqrt(width2+height2);
    }


//    private void get1() {
//        Resources resources = this.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        Toast.makeText(TzResultActivity.this,""+width + " , " + height+" "+getPingMuSize(TzResultActivity.this),Toast.LENGTH_SHORT).show();
//        Log.d("方法1", width + " , " + height);
//    }

//    private void doChildAnalyze(){
//        int maxS=0;
//        int index=-1;
//        for(int i=0;i<len;i++){
//            if(i!=len-1){
//                if(maxS<score[i]){
//                    maxS=Math.max(maxS,score[i]);
//                    index=i;
//                }
//            }
//        }
//        if(score[len-1]>=60){
//            if(maxS<30){
//                res[len-1]=1;
//            }else if(maxS<40){
//                res[len-1]=2;
//            }else{
//                res[len-1]=0;
//            }
//        }else {
//            if(maxS<30){
//                res[len-1]=2;
//            }else{
//                res[len-1]=0;
//            }
//        }
//
//        for(int i=0;i<len-1;i++){
//            if(score[i]>=40){
//                res[i]=1;
//            }else if(score[i]>=30){
//                res[i]=2;
//            }else{
//                res[i]=0;
//            }
//        }
//        String r="";
//        if(res[len-1]!=0){
//            if(res[len-1]==1){
//                r+="您是平和质。";
//            }else{
//                r+="您基本是平和质。";
//            }
//            if(res[index]==1){
//                r+="您还兼有"+tzChild[index]+"。";
//            }
//        }else{
//            if(res[index]==2){
//                r+="您倾向是"+tzChild[index]+"。";
//            }else{
//                r+="您是"+tzChild[index]+"。";
//                int maxS2=0;
//                int index2=-1;
//                for(int j=0;j<len-1;j++){
//                    if(j!=index){
//                        if(maxS2<score[j]){
//                            maxS2=score[j];
//                            index2=j;
//                        }
//                    }
//                }
//                if(res[index2]==1){
//                    r+="您还兼有"+tzChild[index2]+"。";
//                }
//            }
//        }
////        Toast.makeText(TzResultActivity.this,r,Toast.LENGTH_SHORT).show();
//        textRes.setText(r);
//    }
//
//    private void doYoungAnalyze(){
//        Toast.makeText(TzResultActivity.this,getS(),Toast.LENGTH_SHORT).show();
//
//        int maxS=0;
//        int index=-1;
//        for(int i=0;i<len;i++){
//            if(i!=len-1){
//                if(maxS<score[i]){
//                    maxS=Math.max(maxS,score[i]);
//                    index=i;
//                }
//            }
//        }
//        if(score[len-1]>=60){
//            if(maxS<30){
//                res[len-1]=1;
//            }else if(maxS<40){
//                res[len-1]=2;
//            }else{
//                res[len-1]=0;
//            }
//        }else {
//            if(maxS<30){
//                res[len-1]=2;
//            }else{
//                res[len-1]=0;
//            }
//        }
//
//        for(int i=0;i<len-1;i++){
//            if(score[i]>=40){
//                res[i]=1;
//            }else if(score[i]>=30){
//                res[i]=2;
//            }else{
//                res[i]=0;
//            }
//        }
//        String r="";
//        if(res[len-1]!=0){
//            if(res[len-1]==1){
//                Toast.makeText(TzResultActivity.this,"aaa"+index,Toast.LENGTH_SHORT).show();
//                r+="您是平和质。";
//            }else{
//                r+="您基本是平和质。";
//            }
//            if(res[index]==1){
//                r+="您还兼有"+tz[index]+"。";
//            }
//        }
//        else{
//            if(res[index]==2){
//                r+="您倾向是"+tz[index]+"。";
//
//            }else{
//                r+="您是"+tz[index]+"。";
//                Toast.makeText(TzResultActivity.this,"bbb"+index,Toast.LENGTH_SHORT).show();
//                int maxS2=0;
//                int index2=-1;
//                for(int j=0;j<len-1;j++){
//                    if(j!=index){
//                        if(maxS2<score[j]){
//                            maxS2=score[j];
//                            index2=j;
//                        }
//                    }
//                }
//                if(res[index2]==1){
//                    r+="您还兼有"+tz[index2]+"。";
//                }
//            }
//        }
////        Toast.makeText(TzResultActivity.this,r,Toast.LENGTH_SHORT).show();
//        textRes.setText(r);
//    }
//
//    private void doOldAnalyze(){
//        int maxS=0;
//        int index=-1;
//        for(int i=0;i<len;i++){
//            if(i!=len-1){
//                if(maxS<num[i]){
//                    maxS=Math.max(maxS,num[i]);
//                    index=i;
//                }
//            }
//        }
//        if(num[len-1]>=17){
//            if(maxS<8){
//                res[len-1]=1;
//            }else if(maxS<10){
//                res[len-1]=2;
//            }else{
//                res[len-1]=0;
//            }
//        }else {
//            if(maxS<9){
//                res[len-1]=2;
//            }else{
//                res[len-1]=0;
//            }
//        }
//
//        for(int i=0;i<len-1;i++){
//            if(num[i]>=11){
//                res[i]=1;
//            }else if(num[i]>=9){
//                res[i]=2;
//            }else{
//                res[i]=0;
//            }
//        }
//        String r="";
//        if(res[len-1]!=0){
//            if(res[len-1]==1){
//                r+="您是平和质。";
//            }else{
//                r+="您基本是平和质。";
//            }
//            if(res[index]==1){
//                r+="您还兼有"+tz[index]+"。";
//            }
//        }else{
//            if(res[index]==2){
//                r+="您倾向是"+tz[index]+"。";
//            }else{
//                r+="您是"+tz[index]+"。";
//                int maxS2=0;
//                int index2=-1;
//                for(int j=0;j<len-1;j++){
//                    if(j!=index){
//                        if(maxS2<num[j]){
//                            maxS2=num[j];
//                            index2=j;
//                        }
//                    }
//                }
//                if(res[index2]==1){
//                    r+="您还兼有"+tz[index2]+"。";
//                }
//            }
//        }
////        Toast.makeText(TzResultActivity.this,r,Toast.LENGTH_SHORT).show();
//        textRes.setText(r);
//    }

    public static void main(String [] arg){
        sendRequestWithOkHttp();
    }

    private static void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
//                    RequestBody requestBody=new FormBody.Builder()
//                            .add("requestType",arr2[key])
//                            .add("member_id","88")
//                            .add("key_tz","lblc6wcj3ogh0uyhfek53b5z")
//                            .build();
                    Request request=new Request.Builder()
                            .url("http://miaolangzhong.com/erzhentang/saas100Business/bodyIdentify.do?requestType=1&member_id=88&key_tz=lblc6wcj3ogh0uyhfek53b5z")
//                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void showResponse(final String response){
        new Thread(new Runnable() {
            @Override
            public void run() {
                parseJSONWithGSON(response);
            }
        }).start();
    }

    private static void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
//        List<TzAdultOlQuesResult> tzResults=gson.fromJson(jsonData,new TypeToken<List<TzAdultOlQuesResult>>(){}.getType());
        TzWxOlQuesResult tzWxOlQuesResult =gson.fromJson(jsonData, TzWxOlQuesResult.class);
        List<TzWxOlQuesResult.C.Wxtz> list= tzWxOlQuesResult.getRec().getListWxtz();
        ExcelWriter.createExcelTzWx(new File("d://wx.xls"),list,0);
    }

    //删除文件夹
    private static void deleteExcel(File file){
        if(file.exists()){
            file.delete();
        }
    }


}
