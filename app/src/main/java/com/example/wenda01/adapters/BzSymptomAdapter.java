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
import com.example.wenda01.beans.bz.BzSymptom;

import java.util.List;

public class BzSymptomAdapter extends ArrayAdapter<BzSymptom> {  //病症症状适配器
    private int resourceId;

    public BzSymptomAdapter(Context context, int resourceId, List objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BzSymptom bzSymptom =getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else{
            view=convertView;
        }
        TextView symptomName=(TextView)view.findViewById(R.id.guide_item_name);
        symptomName.setText(bzSymptom.getName());
        return view;

    }
}
