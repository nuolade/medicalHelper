package com.example.wenda01.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.beans.tz.TzOneRecord;
import com.lixs.charts.BarChart.LBarChartView;

import java.util.ArrayList;
import java.util.List;

public class TzOneAdapter extends RecyclerView.Adapter<TzOneAdapter.ViewHolder> {  //体质单选问题适配器
    private String[] tizhi={"阳虚","阴虚","气虚","痰湿","湿热","血瘀","气郁","血虚"};
    private List< List<TzOneRecord> > rList;
    private List<Integer> iList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        LBarChartView lBarChartView;
        public ViewHolder(View view){
            super(view);
            textTitle=view.findViewById(R.id.text_title);
            lBarChartView=view.findViewById(R.id.bar_chart);
        }
    }

    public TzOneAdapter(List<List<TzOneRecord>> rList, List<Integer> iList) {
        this.rList = rList;
        this.iList = iList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tz_record_one_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<TzOneRecord> list=rList.get(position);
        int index=iList.get(position);
        holder.textTitle.setText(tizhi[index]+"质");
        List<Double> doubles=new ArrayList<>();
        List<String> strings=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            TzOneRecord record=list.get(i);
            doubles.add((double)record.getScore());
            String time=record.getTime();
            String [] arr=time.split("-");
            strings.add(arr[0]+"/"+arr[1]+"/"+arr[2]);
//            strings.add(arr[0]);
        }
        if(doubles.size()==0){
            doubles.add(0d);
            strings.add("无记录");
        }
        holder.lBarChartView.setDatas(doubles,strings,true);
    }

    @Override
    public int getItemCount() {
        return rList.size();
    }
}