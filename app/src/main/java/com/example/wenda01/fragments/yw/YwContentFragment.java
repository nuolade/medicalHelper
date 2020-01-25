package com.example.wenda01.fragments.yw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.beans.yw.MedInter4S;
import com.example.wenda01.beans.yw.MedicineEffect;
import com.example.wenda01.beans.yw.MedicineIndex;
import com.example.wenda01.beans.yw.MedicineInteraction;
import com.example.wenda01.fragments.base.YwFabFragment;
import com.example.wenda01.utils.StringUtils;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class YwContentFragment extends YwFabFragment implements View.OnClickListener {   //药物详细内容碎片
    private View  view;
    private TextView textName;
    private TextView textCategory;
    private TextView textEffect;
    private TextView textChufang;
    private TextView textMingxi;
    private RecyclerView listView;
    private Button buttonIntor;
    protected MedicineIndex medicineIndex;
    private InterAdapter interAdapter;
    private YwGuideFragment ywGuideFragment;
    private List<MedInter4S> inter4SList;
    private String [] listIntor={"介绍","简介","说明"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.yw_content_frag,container,false);
        preWork();
        setSDFab(view,this);

        getData();
        return view;
    }

    private void preWork(){
        textName=view.findViewById(R.id.text_yw_name);
        textCategory=view.findViewById(R.id.text_yw_category);
        textEffect=view.findViewById(R.id.text_yw_effect);
        textChufang=view.findViewById(R.id.text_yw_chufang);
        textMingxi=view.findViewById(R.id.text_yw_mingxi);
        listView=view.findViewById(R.id.list_yw_inter);
        buttonIntor=view.findViewById(R.id.button_intor);
        buttonIntor.setOnClickListener(this);
        textCategory.setOnClickListener(this);
        textName.setOnClickListener(this);
        textChufang.setOnClickListener(this);
        textMingxi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onClickFab(v) ){
            return;
        }

        switch (v.getId()){
            case R.id.button_intor:
                doAllIntor();
                break;
            case R.id.text_yw_category:
                doSearch(v);
                break;
            case R.id.text_yw_name:
                doSearch(v);
                break;
            case R.id.text_yw_chufang:
                doSearch(v);
                break;
            case R.id.text_yw_mingxi:
                doSearch(v);
                break;

        }
    }

    private void doSearch(View v){   //平板下当点击名称和类别是，更新药物引导碎片药物查找列表
        String s;
        if(ywGuideFragment!=null){
            if(v.getId()==R.id.text_yw_category){
                s=textCategory.getText().toString().trim();
                ywGuideFragment.doOuterSearch(s);
            }
            else if(v.getId()==R.id.text_yw_name){
                s=textName.getText().toString().trim();
                ywGuideFragment.doOuterSearch(s);
            }
            else if(v.getId()==R.id.text_yw_chufang){
                s=textChufang.getText().toString().trim();
                ywGuideFragment.doOuterSearchCF(s);
            }
            else if(v.getId()==R.id.text_yw_mingxi){
                s=textMingxi.getText().toString().trim();
                ywGuideFragment.doOuterSearchMX(s);
            }
        }


    }

    private void doAllIntor(){   //进行药物全部信息播报
        if(!MyApplication.isIsSoundOpen()){
            ToastUtils.showS(getResources().getString(R.string.text_sound_intor));
        }else{
            String s="";
            s+="本药为"+medicineIndex.getName();
            s+="。所属类别为"+medicineIndex.getCategory();
            s+="。为"+medicineIndex.getCf();
            s+="。有"+textEffect.getText().toString().trim()+"的功效";
            if(inter4SList.size()>0){
                s+="。并会和以下"+inter4SList.size()+"种药物有相互作用。";
                for(int  i=0;i<inter4SList.size();i++){
                    MedInter4S medInter4S=inter4SList.get(i);
                    String s2="第 "+(i+1)+" 会和 "+medInter4S.getName()+" 相互作用。";
                    s2+="从而 "+medInter4S.getResult()+" 。";
                    s2+="这时 "+medInter4S.getAction();
                    s+=s2;
                }
            }
            reportString=s;
            doFabReport(reportString,true,true);
        }
    }

    protected void getData(){   //获得初始数据
        medicineIndex=(MedicineIndex) getArguments().getSerializable("mIndex");
        ywGuideFragment=(YwGuideFragment) getArguments().getSerializable("YwGF");
        textName.setText(medicineIndex.getName());
        textCategory.setText(medicineIndex.getCategory());
        textMingxi.setText(medicineIndex.getMx());
        textChufang.setText(medicineIndex.getCf());
        List<MedicineEffect> effectList= DataSupport.where("name = ?",medicineIndex.getO2()).find(MedicineEffect.class);
        if(effectList.size()>0){
            textEffect.setText(effectList.get(0).getEffect());
        }
        inter4SList=new ArrayList<>();
        List<MedicineInteraction> list1=DataSupport.where("name = ?",medicineIndex.getO1()).find(MedicineInteraction.class);
        List<MedicineInteraction> list2=DataSupport.where("interactionName = ?",medicineIndex.getO1()).find(MedicineInteraction.class);
        for(int i=0;i<list1.size();i++){
            MedicineInteraction mi=list1.get(i);
            MedInter4S medInter4S=new MedInter4S(mi.getInteractionName(),mi.getResult(),mi.getAction());
            inter4SList.add(medInter4S);
        }
        for(int i=0;i<list2.size();i++){
            MedicineInteraction mi=list2.get(i);
            MedInter4S medInter4S=new MedInter4S(mi.getName(),mi.getResult(),mi.getAction());
            inter4SList.add(medInter4S);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        interAdapter=new InterAdapter(inter4SList);
        listView.setAdapter(interAdapter);
    }

    protected void getSayingWords(String s){
        if(getFabSayingWords(s)){
            return;
        }
        if(StringUtils.myContain(s,listIntor)){
            buttonIntor.performClick();
        }
    }

    class InterAdapter extends RecyclerView.Adapter<InterAdapter.ViewHolder> {   //药物相互作用适配器

        private List<MedInter4S> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textName;
            TextView textRes;
            TextView textAct;
            Button buttonSound;
            public ViewHolder(View view){
                super(view);
                textName=view.findViewById(R.id.inter_name);
                textRes=view.findViewById(R.id.inter_res);
                textAct=view.findViewById(R.id.inter_method);
                buttonSound=view.findViewById(R.id.button_sound);
            }
        }

        public InterAdapter(List<MedInter4S> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.yw_inter_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MedInter4S medicineInteraction =mList.get(position);
            holder.textAct.setText(medicineInteraction.getAction());
            holder.textRes.setText(medicineInteraction.getResult());
            holder.textName.setText(""+(position+1)+". "+medicineInteraction.getName());
            holder.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String s=holder.textName.getText().toString().trim();
                    String s=mList.get(position).getName();
                    if(ywGuideFragment!=null){
                        ywGuideFragment.doOuterSearch(s);
                    }
                }
            });
            holder.buttonSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!MyApplication.isIsSoundOpen()){
                        ToastUtils.showS(getResources().getString(R.string.text_sound_intor));
                    }else{
                        MedInter4S medInter4S=mList.get(holder.getAdapterPosition());
                        String s=medicineIndex.getName()+" 会和 "+medInter4S.getName()+" 相互作用。";
                        s+="从而 "+medInter4S.getResult()+" 。";
                        s+="这时 "+medInter4S.getAction();
                        reportString=s;
                        doFabReport(reportString,false,true);
                    }


                }
            });
//            holder.klTitleText.setText(ckKnowledge.getTitle());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
