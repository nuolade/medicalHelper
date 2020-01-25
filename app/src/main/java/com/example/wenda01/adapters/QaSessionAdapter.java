package com.example.wenda01.adapters;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.beans.qa.QaSession;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.TimeUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

public class QaSessionAdapter extends RecyclerView.Adapter<QaSessionAdapter.ViewHolder> {  //知识问答会话适配器
    private List<QaSession> sList;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textTimeCreated;
        TextView textTimeLast;
        TextView textPhone;
        TextView textContent;
        ImageView imageCancel;
        LinearLayout layoutContent;

        public ViewHolder(View view){
            super(view);
            textName=view.findViewById(R.id.text_name);
            textTimeCreated=view.findViewById(R.id.text_time_created);
            textTimeLast=view.findViewById(R.id.text_time_last);
            textPhone=view.findViewById(R.id.text_phone);
            textContent=view.findViewById(R.id.text_content);
            imageCancel=view.findViewById(R.id.image_cancel);
            layoutContent=view.findViewById(R.id.layout_content);
        }
    }

    public QaSessionAdapter(List<QaSession> sList,Handler handler) {
        this.sList = sList;
        this.handler=handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qa_session_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QaSession qaS=sList.get(position);
        if(MyApplication.getQaSesDel()==Ks.QA_MSG_DEL){
            holder.imageCancel.setVisibility(View.VISIBLE);
        }else{
            holder.imageCancel.setVisibility(View.GONE);
        }
        String curTime=TimeUtils.getTime();
        String [] curArr=curTime.split("-");
        String [] creArr=qaS.getTimeCreated().split("-");
        String [] lastArr=qaS.getTimeLast().split("-");
        String creS=""+creArr[0]+"/"+creArr[1]+"/"+creArr[2];
        String lastS="";
        if(curArr[0].equals(lastArr[0]) && curArr[1].equals(lastArr[1]) && curArr[2].equals(lastArr[2])  ){
            lastS+=lastArr[3]+":"+lastArr[4];
        }else{
            lastS+=lastArr[0]+"/"+lastArr[1]+"/"+lastArr[2];
        }
        holder.textName.setText(qaS.getName());
        holder.textPhone.setText(StringUtils.getPhoneHide(qaS.getPhone()));
        holder.textTimeLast.setText(lastS);
        holder.textTimeCreated.setText(creS);
        holder.textContent.setText(qaS.getTextLast());
        holder.imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(QaSession.class,"no = ?",""+qaS.getNo());
                sList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=Ks.QA_OPENSESSION;
                message.obj=qaS;
                handler.sendMessage(message);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }
}