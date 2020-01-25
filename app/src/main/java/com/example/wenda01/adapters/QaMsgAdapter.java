package com.example.wenda01.adapters;

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
import com.example.wenda01.beans.qa.QaMsg;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.TTSUtility;
import com.example.wenda01.utils.TimeUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

public class QaMsgAdapter extends RecyclerView.Adapter<QaMsgAdapter.ViewHolder> {  //知识问答消息适配器
    private List<QaMsg> msgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        TextView textTime;
        ImageView imageCancel;
        public ViewHolder(View view){
            super(view);
            leftLayout=view.findViewById(R.id.layout_left);
            rightLayout=view.findViewById(R.id.layout_right);
            leftMsg=view.findViewById(R.id.left_msg);
            rightMsg=view.findViewById(R.id.right_msg);
            textTime=view.findViewById(R.id.text_time);
            imageCancel=view.findViewById(R.id.image_cancel);
        }
    }

    public QaMsgAdapter(List<QaMsg> msgList) {
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qa_msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QaMsg qaMsg=msgList.get(position);
        if(MyApplication.getQaMsgDel()==Ks.QA_MSG_DEL){
            holder.imageCancel.setVisibility(View.VISIBLE);
        }else{
            holder.imageCancel.setVisibility(View.GONE);
        }
        int cnt=qaMsg.getNo();
        if(qaMsg.getType()== Ks.QA_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            if(cnt>0){
                holder.leftMsg.setText("回答"+cnt+": "+qaMsg.getText());
            }else {
                holder.leftMsg.setText(qaMsg.getText());
            }

        }else if(qaMsg.getType()==Ks.QA_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            if(cnt>0){
                holder.rightMsg.setText("问题"+cnt+": "+qaMsg.getText());
            }else {
                holder.rightMsg.setText(qaMsg.getText());
            }
        }
        holder.leftMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTSUtility.doReport(qaMsg.getText());
            }
        });
        holder.rightMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTSUtility.doReport(qaMsg.getText());
            }
        });
        holder.imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(QaMsg.class,"time = ? and session = ? and type = ? and no = ?",qaMsg.getTime(),""+qaMsg.getSession(),""+qaMsg.getType(),""+qaMsg.getNo());
                msgList.remove(position);
                notifyDataSetChanged();
            }
        });

        String msgTime=qaMsg.getTime();
        String [] msgArr=msgTime.split("-");
        String curTime= TimeUtils.getTime();
        String [] curArr=curTime.split("-");
        String time=""+msgArr[3]+":"+msgArr[4];
        if(msgArr[0].equals(curArr[0])){
            if(msgArr[1].equals(curArr[1]) && msgArr[2].equals(curArr[2])){

            }else{
                time=""+msgArr[1]+"/"+msgArr[2]+" "+time;
            }

        }else{
            time=""+msgArr[0]+"/"+msgArr[1]+"/"+msgArr[2]+" "+time;
        }
        holder.textTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
