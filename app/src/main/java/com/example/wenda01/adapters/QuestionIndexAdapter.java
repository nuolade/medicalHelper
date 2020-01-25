package com.example.wenda01.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzIndexQuestion;
import com.example.wenda01.utils.TTSUtility;

import java.util.List;

public class QuestionIndexAdapter extends RecyclerView.Adapter<QuestionIndexAdapter.ViewHolder> { //编号问题适配器
    private List<TzIndexQuestion> mList;
    private Handler handler;



    static class ViewHolder extends RecyclerView.ViewHolder{
        Button button;
        public ViewHolder(View view){
            super(view);
            button=view.findViewById(R.id.question_no);

        }
    }

    public QuestionIndexAdapter(List<TzIndexQuestion> mList, Handler handler) {
        this.mList = mList;
        this.handler=handler;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.number_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTSUtility.getInstance(MyApplication.getContext()).stopSpeaking();
                int position=holder.getAdapterPosition();
                TzIndexQuestion tzIndexQuestion =mList.get(position);
//                textView.setText(tzIndexQuestion.getText());
                Message message=new Message();
                message.what=position;
                handler.sendMessage(message);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TzIndexQuestion tzIndexQuestion =mList.get(position);
        holder.button.setText(""+(tzIndexQuestion.getId()+1));
        Context context= MyApplication.getContext();
        if(tzIndexQuestion.getState()==0){
            if(tzIndexQuestion.isOn()){
                holder.button.setBackgroundColor(context.getResources().getColor(R.color.colorChooseNothingOn));
            }else{
                holder.button.setBackgroundColor(context.getResources().getColor(R.color.colorChooseNothingOff));
            }
        }else if(tzIndexQuestion.getState()==1){
            if(tzIndexQuestion.isOn()){
                holder.button.setBackgroundColor(context.getResources().getColor(R.color.colorChooseYesOn));
            }else{
                holder.button.setBackgroundColor(context.getResources().getColor(R.color.colorChooseYesOff));
            }
        }else if(tzIndexQuestion.getState()==2){
            if(tzIndexQuestion.isOn()){
                holder.button.setBackgroundColor(context.getResources().getColor(R.color.colorChooseNoOn));
            }else{
                holder.button.setBackgroundColor(context.getResources().getColor(R.color.colorChooseNoOff));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
