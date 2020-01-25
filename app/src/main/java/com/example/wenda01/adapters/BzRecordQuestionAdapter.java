package com.example.wenda01.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.beans.bz.BzRecordQuestion;

import java.util.List;

public class BzRecordQuestionAdapter extends ArrayAdapter<BzRecordQuestion> {  //病症记录中问题适配器
    private int resourceId;

    public BzRecordQuestionAdapter(@NonNull Context context, int resource, @NonNull List<BzRecordQuestion> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BzRecordQuestion question=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView textNo=view.findViewById(R.id.text_bz_r_no);
        TextView textQ=view.findViewById(R.id.text_bz_r_question);
        TextView textA=view.findViewById(R.id.text_bz_r_answer);
        textNo.setText(""+(position+1));
        textA.setText(question.getA());
        textQ.setText(question.getQ());
        return view;
    }
}
