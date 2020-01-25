package com.example.wenda01.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonParser {   //json解析器
    public static String parseIatResult(String json){  //解析json
        StringBuffer ret=new StringBuffer();
        try{
            JSONTokener tokener=new JSONTokener(json);
            JSONObject jsonObject=new JSONObject(tokener);
            JSONArray words=jsonObject.getJSONArray("ws");
            for(int i=0;i<words.length();i++){
                JSONArray items=words.getJSONObject(i).getJSONArray("cw");
                JSONObject object=items.getJSONObject(0);
                ret.append(object.getString("w"));
                //多候选
//                for(int j=0;j<items.length();j++){
//                    JSONObject object1=items.getJSONObject(j);
//                    ret.append(object1.getString("w"));
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret.toString();
    }
}
