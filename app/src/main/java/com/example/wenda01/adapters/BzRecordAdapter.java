package com.example.wenda01.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.beans.bz.BzRecordQuestion;
import com.example.wenda01.beans.bz.BzRecord;
import com.example.wenda01.beans.bz.BzQuestion;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class BzRecordAdapter extends ArrayAdapter<BzRecord> {  //病症记录适配器
    private int resourceId;

    private List<BzRecord> list;

    public BzRecordAdapter(@NonNull Context context, int resource, @NonNull List<BzRecord> objects) {
        super(context, resource, objects);
        resourceId=resource;
        list=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BzRecord bzRecord=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        LinearLayout layoutBzR=view.findViewById(R.id.layout_bz_r);
        LinearLayout layoutBzRContent=view.findViewById(R.id.layout_bz_r_content);
        TextView textNo=view.findViewById(R.id.text_bz_r_no);
        TextView textTime=view.findViewById(R.id.text_bz_r_time);
        TextView textName=view.findViewById(R.id.text_bz_r_name);
        TextView textSug=view.findViewById(R.id.text_bz_r_suggestion);
        ListView listQues=view.findViewById(R.id.list_bz_r_question);
        ImageView imageCancel=view.findViewById(R.id.image_cancel);

        layoutBzR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutBzRContent.getVisibility()==View.GONE){
                    layoutBzRContent.setVisibility(View.VISIBLE);
                }else{
                    layoutBzRContent.setVisibility(View.GONE);
                }
            }
        });
        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isBzRDel()){
                    showDelDialog(bzRecord.getPhone(),bzRecord.getTime(),position);
                }else{
                    doBzRDel(bzRecord.getPhone(),bzRecord.getTime(),position);
                }


            }
        });

        String sId=bzRecord.getsId();
        String sAnd=bzRecord.getsAns();
        List<BzRecordQuestion> list=getQuestion(sId,sAnd);
        BzRecordQuestionAdapter bzRecordQuestionAdapter =new BzRecordQuestionAdapter(getContext(),R.layout.bz_r_question_item,list);
        listQues.setAdapter(bzRecordQuestionAdapter);
        setListViewHeightBasedOnChildren(listQues);
        listQues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BzRecordQuestion bzRecordQuestion =list.get(position);
                ToastUtils.showS(bzRecordQuestion.getQ());
            }
        });

        textNo.setText(""+(position+1));
        String [] times=bzRecord.getTime().split("-");
        String time=""+times[0]+"年"+times[1]+"月"+times[2]+"日   "+times[3]+":"+times[4]+":"+times[5];
        textTime.setText(time);
        textName.setText(bzRecord.getSymptom());
        textSug.setText(bzRecord.getSuggestion());
        return view;
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    private List<BzRecordQuestion> getQuestion(String s1, String s2){
        List<BzRecordQuestion> list=new ArrayList<>();
        String [] ids=s1.split(" ");
        String [] ans=s2.split(" ");
        for(int i=0;i<ids.length;i++){
            String text=getQ(ids[i]);
            String an=getA(ans[i]);
            BzRecordQuestion bzRecordQuestion =new BzRecordQuestion(text,an);
            list.add(bzRecordQuestion);
        }
        return list;
    }

    private String getQ(String id){
        List<BzQuestion> bzQuestions = DataSupport.where("idd = ?",id).find(BzQuestion.class);
        return bzQuestions.get(0).getText();
    }

    private String getA(String an){
        if(an.equals("1")){
            return "是";
        }else{
            return "否";
        }
    }

    private void showDelDialog(String p,String t,int position){
        AlertDialog.Builder dialog;
        dialog=new AlertDialog.Builder(getContext());
        dialog.setCancelable(true);
        dialog.setTitle("删除提示");
        dialog.setMessage(MyApplication.getContext().getResources().getString(R.string.text_del_intor));
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doBzRDel(p,t,position);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    private void doBzRDel(String p,String t,int position){
        DataSupport.deleteAll(BzRecord.class,"phone = ? and time = ?",p,t);
        list.remove(position);
        notifyDataSetChanged();
    }
}
