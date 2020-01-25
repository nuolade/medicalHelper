package com.example.wenda01.adapters;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.utils.Ks;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{ //搜索历史记录适配器
    private List<String> mList;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;

        public ViewHolder(View view){
            super(view);
            textName=view.findViewById(R.id.history_text);
        }
    }

    public HistoryAdapter(List<String> mList, Handler handler) {
        this.mList = mList;
        this.handler=handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=holder.getAdapterPosition();
                String s=mList.get(mList.size()-1-p);
                Message message=new Message();
                message.what= Ks.HISTORYKEY;
                message.obj=s;
                handler.sendMessage(message);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s=mList.get(mList.size()-1-position);
        holder.textName.setText(s);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}