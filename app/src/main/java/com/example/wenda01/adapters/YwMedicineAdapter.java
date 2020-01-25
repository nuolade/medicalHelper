package com.example.wenda01.adapters;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.beans.yw.MedicineIndex;
import com.example.wenda01.utils.Ks;

import java.util.List;

public class YwMedicineAdapter extends RecyclerView.Adapter<YwMedicineAdapter.ViewHolder>{ //药物适配器
    private List<MedicineIndex> mList;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textClass;
        LinearLayout layout;
        TextView textYm;

        public ViewHolder(View view){
            super(view);
            textName=view.findViewById(R.id.yw_item_title);
            textClass=view.findViewById(R.id.yw_item_class);
            layout=view.findViewById(R.id.layout_yw_item);
            textYm=view.findViewById(R.id.text_ym);
        }
    }

    public YwMedicineAdapter(List<MedicineIndex> mList, Handler handler) {
        this.mList = mList;
        this.handler=handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.yw_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=holder.getAdapterPosition();
                MedicineIndex medicineIndex=mList.get(p);
                Message message=new Message();
                message.what= Ks.STARTKEY;
                message.obj=medicineIndex;
                handler.sendMessage(message);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicineIndex medicineIndex=mList.get(position);
//        holder.button.setText(""+(tzQuestion.getId()+1));
        String name=medicineIndex.getName();
        holder.textYm.setText(""+(position+1)+". 药名：");
        if(medicineIndex.getCf().indexOf("非")==-1){
            name+="*";
        }
        holder.textName.setText(name);
        if(medicineIndex.getMx().indexOf("通用")!=-1){
//            holder.textName.setTypeface(Typeface.DEFAULT_BOLD);
//            holder.textName.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);
            holder.textName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            holder.textName.getPaint().setAntiAlias(true);//抗锯齿
        }
        holder.textClass.setText(medicineIndex.getCategory());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
