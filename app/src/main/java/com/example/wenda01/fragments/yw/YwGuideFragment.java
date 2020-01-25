package com.example.wenda01.fragments.yw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.yaowu.YwContentActivity;
import com.example.wenda01.adapters.HistoryAdapter;
import com.example.wenda01.adapters.YwMedicineAdapter;
import com.example.wenda01.beans.yw.MedicineEffect;
import com.example.wenda01.beans.yw.MedicineIndex;
import com.example.wenda01.beans.yw.MedicineInteraction;
import com.example.wenda01.fragments.base.YwFabFragment;
import com.example.wenda01.utils.StringUtils;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YwGuideFragment extends YwFabFragment implements View.OnClickListener, Serializable {   //药物引导碎片

    private EditText editName;
    private ImageView imageVoice;
    private Button buttonSearch;
    private Button buttonFilter;
    private Button buttonCancel;
    private EditText editSearch;
    private TextView textTitle;
    private RecyclerView listView;
    private LinearLayout layoutFilter;
    private TextView textTap;
    private Button buttonHistory;
    private LinearLayout layoutHistory;
    private RecyclerView recyclerHistory;
    private List<String> hList;
    private HistoryAdapter historyAdapter;
    private Button buttonClear;

    private RadioGroup groupNr;
    private RadioGroup groupPp;
    private RadioGroup groupCf;
    private RadioGroup groupMx;
    private RadioButton radioCf;
    private RadioButton radioFcf;
    private RadioButton radioAllCf;
    private RadioButton radioPp;
    private RadioButton radioTy;
    private RadioButton radioAllMx;
    private RadioButton radioJq;
    private RadioButton radioMh;
    private RadioButton radioGjc;
    private RadioButton radioMc;
    private RadioButton radioGx;
    private TextView textEmpty;
    private ImageView imageCancel;


    private List<RadioGroup> radioGroupList; //单选组列表
    private boolean isFiltering; //是否开启高级过滤
    private List<MedicineIndex> medicineIndexList; //药物索引列表
    private YwMedicineAdapter ywMedicineAdapter;

    private String sCf="处方药";
    private String sFcf="非处方药";
    private String sTy="通用名称";
    private String sPp="品牌名称";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.yw_guide_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.yw_content_layout)!=null){
            isTwoPane=true;
        }else {
            isTwoPane=false;
        }
        preWork();
        showIntorOnlyString(getResources().getString(R.string.text_yw_intor));
        getHistory();
    }


    private void preWork(){
        radioGroupList=new ArrayList<>();
        isFiltering=false;

        buttonSearch=view.findViewById(R.id.button_search);
        imageVoice=view.findViewById(R.id.button_voice);
        buttonFilter=view.findViewById(R.id.button_filter);
        buttonCancel=view.findViewById(R.id.button_cancel);
        editSearch=view.findViewById(R.id.edit_search);
        textTitle=view.findViewById(R.id.text_guide_title);
        listView=view.findViewById(R.id.list_guide);
        buttonHistory=view.findViewById(R.id.button_history);
        layoutHistory=view.findViewById(R.id.layout_history);
        recyclerHistory=view.findViewById(R.id.recycler_history);
        buttonClear=view.findViewById(R.id.button_history_clear);
        textEmpty=view.findViewById(R.id.text_res_empty);
        imageCancel=view.findViewById(R.id.image_cancel);

        groupNr=view.findViewById(R.id.group_content);
        groupPp=view.findViewById(R.id.group_pp);
        groupCf=view.findViewById(R.id.group_cf);
        groupMx=view.findViewById(R.id.group_mx);
        radioGroupList.add(groupNr);
        radioGroupList.add(groupPp);
        radioGroupList.add(groupCf);
        radioGroupList.add(groupMx);

        radioCf=view.findViewById(R.id.radio_cf_cfy);
        radioFcf=view.findViewById(R.id.radio_cf_fcfy);
        radioAllCf=view.findViewById(R.id.radio_cf_all);
        radioPp=view.findViewById(R.id.radio_mx_pp);
        radioTy=view.findViewById(R.id.radio_mx_ty);
        radioAllMx=view.findViewById(R.id.radio_mx_all);
        radioJq=view.findViewById(R.id.radio_pp_jq);
        radioMh=view.findViewById(R.id.radio_pp_mh);
        radioGjc=view.findViewById(R.id.radio_pp_gjc);
        radioMc=view.findViewById(R.id.radio_nr_mz);
        radioGx=view.findViewById(R.id.radio_nr_gx);

        initRadioGroup();

        layoutFilter=view.findViewById(R.id.layout_yw_filter);
        layoutFilter.setVisibility(View.GONE);
        textEmpty.setVisibility(View.GONE);

        imageCancel.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        groupNr.setOnClickListener(this);
        radioMc.setOnClickListener(this);
        radioGx.setOnClickListener(this);
        imageVoice.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonFilter.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        layoutHistory.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.INVISIBLE);

        textTitle.setText(getResources().getString(R.string.guide_yw_title));
    }

    protected void getData(){
        medicineIndexList= DataSupport.findAll(MedicineIndex.class);
        LinearLayoutManager layoutManager=new LinearLayoutManager(MyApplication.getContext());
        listView.setLayoutManager(layoutManager);
        ywMedicineAdapter =new YwMedicineAdapter(medicineIndexList,handlerYw);
        listView.setAdapter(ywMedicineAdapter);
    }

    private void initRadioGroup(){   //初始单选按钮
        RadioButton button;
        for(int i=0;i<radioGroupList.size();i++){
            button=(RadioButton) radioGroupList.get(i).getChildAt(0);
            button.setChecked(true);
        }
    }

    private void getHistory(){   //获得查询历史
        hList=new ArrayList<>();
        SharedPreferences sp=getContext().getSharedPreferences("history", Context.MODE_PRIVATE);
        int hSize=sp.getInt("ywHSize",0);
        for(int i=0;i<hSize;i++){
            hList.add(sp.getString("ywHItem"+i,null));
        }
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerHistory.setLayoutManager(manager);
        historyAdapter=new HistoryAdapter(hList,handlerStart);
        recyclerHistory.setAdapter(historyAdapter);
    }

    private void saveHis(List<String> list){   //存储查询记录
        SharedPreferences sp=getContext().getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("ywHSize",list.size());
        for(int i=0;i<list.size();i++){
            editor.remove("ywHItem"+i);
            editor.putString("ywHItem"+i,list.get(i));
        }
        editor.commit();
    }

    private void clearHistory(){   //清空查询历史
        SharedPreferences sp=getContext().getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("ywHSize",0);
        for(int i=0;i<MyApplication.HSIZE;i++){
            editor.remove("ywHItem"+i);
        }
        editor.commit();
        hList.clear();
        historyAdapter.notifyDataSetChanged();
    }

    protected void doHistoryItem(String s){   //选择历史记录内的选项
        if(s.equals("")){
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
                doSearch();
                break;
            case R.id.button_filter:
                doChangeFilter();
                break;
            case R.id.button_cancel:
                doCancelFilter();
                break;
            case R.id.radio_nr_mz:
                doUpdateContentSearch();
                break;
            case R.id.radio_nr_gx:
                doUpdateContentSearch();
                break;
            case R.id.button_history:
                doHistory();
                break;
            case R.id.button_history_clear:
                clearHistory();
                break;
            case R.id.image_cancel:
                editSearch.setText("");
                break;
            case R.id.button_voice:
                doFabSay();
                break;
        }
    }

    protected void doHistory(){   //更改历史记录的可见性
        if(layoutHistory.getVisibility()==View.GONE){
            layoutHistory.setVisibility(View.VISIBLE);
            buttonHistory.setText("隐藏历史");
        }else{
            layoutHistory.setVisibility(View.GONE);
            buttonHistory.setText("搜索历史");
        }
    }

    protected void getSayingWords(String s){   //获得语音的查询内容
        editSearch.setText(s.trim());
    }

    protected void openYwIntor(MedicineIndex medicineIndex){   //打开药物详细内容
        if(isTwoPane){
            YwContentFragment ywContentFragment=new YwContentFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("mIndex",medicineIndex);
            bundle.putSerializable("YwGF",this);
            ywContentFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.yw_content_layout,ywContentFragment);
            transaction.commit();
        }else{
//            YwContentActivity.actionStart(getActivity(),medicineIndex,this);
            YwContentActivity.actionStart(getActivity(),medicineIndex);
        }
    }

    private void doUpdateContentSearch(){   //更新查找内容单选组
        if(radioGx.isChecked()){
            radioGjc.setChecked(true);
            radioJq.setEnabled(false);
        }else if(radioMc.isChecked()){
            radioJq.setChecked(true);
            radioJq.setEnabled(true);
        }
    }

    private void doCancelFilter(){   //取消高级过滤
        isFiltering=false;
        layoutFilter.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.INVISIBLE);
        initRadioGroup();
    }

    private void doChangeFilter(){   //更新高级过滤布局
        if(layoutFilter.getVisibility()==View.GONE){
            layoutFilter.setVisibility(View.VISIBLE);
            buttonFilter.setText("隐藏过滤");
            buttonCancel.setVisibility(View.VISIBLE);
            isFiltering=true;
        }else{
            layoutFilter.setVisibility(View.GONE);
            buttonFilter.setText("高级过滤");
        }
    }

    private void addHis(String s){   //添加新的历史查询记录
        if(!StringUtils.myContainInList(s,hList)){
            hList.add(s);
            if(hList.size()> MyApplication.HSIZE){
                hList.remove(0);
            }
            saveHis(hList);
            historyAdapter.notifyDataSetChanged();
        }

    }

    private List<MedicineIndex> doLastFilter(List<MedicineIndex> list) {  //进行处方和明细的过滤
        if(radioCf.isChecked()){
            for(int i=0;i<list.size();){
                if(!list.get(i).getCf().equals(sCf)){
                    list.remove(i);
                }else{
                    i++;
                }
            }
        }else if(radioFcf.isChecked()){
            for(int i=0;i<list.size();){
                if(!list.get(i).getCf().equals(sFcf)){
                    list.remove(i);
                }else{
                    i++;
                }
            }
        }
        if(radioTy.isChecked()){
            for(int i=0;i<list.size();){
                if(!list.get(i).getMx().equals(sTy)){
                    list.remove(i);
                }else{
                    i++;
                }
            }
        }else if(radioPp.isChecked()){
            for(int i=0;i<list.size();){
                if(!list.get(i).getMx().equals(sPp)){
                    list.remove(i);
                }else{
                    i++;
                }
            }
        }
        return list;
    }

    private List<MedicineIndex> doNameKeyFilter(List<MedicineIndex> list,String key){   //进行名称关键词过滤
        for(int i=0;i<list.size();){
            boolean f=false;
            MedicineIndex medicineIndex=list.get(i);
            if(medicineIndex.getName().contains(key)){
                f=true;
            }else if(medicineIndex.getCategory().contains(key)){
                f=true;
            }else if(medicineIndex.getO1().contains(key)){
                f=true;
            }else if(medicineIndex.getO2().contains(key)){
                f=true;
            }
            if(f){
                i++;
            }else{
                list.remove(i);
            }
        }
        return list;
    }

    private List<MedicineIndex> doNameKeyFilter(List<MedicineIndex> list,String key,Set<String> setInter,Set<String> setEffect){  //进行名称关键词过滤
        for(int i=0;i<list.size();){
            boolean f=false;
            MedicineIndex medicineIndex=list.get(i);
            if(medicineIndex.getName().contains(key)){
                f=true;
            }else if(medicineIndex.getCategory().contains(key)){
                f=true;
            }else if(medicineIndex.getO1().contains(key)){
                f=true;
            }else if(medicineIndex.getO2().contains(key)){
                f=true;
            }else if(setInter.contains(medicineIndex.getO1())){
                f=true;
            }else if(setEffect.contains(medicineIndex.getO2())){
                f=true;
            }
            if(f){
                i++;
            }else{
                list.remove(i);
            }
        }
        return list;
    }

    private List<MedicineIndex> doNameMhFilter(List<MedicineIndex> list,String key){   //进行名称模糊过滤
        for(int i=0;i<list.size();){
            boolean f=false;
            MedicineIndex medicineIndex=list.get(i);

            if( StringUtils.myMhContian(medicineIndex.getName(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineIndex.getCategory(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineIndex.getO1(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineIndex.getO2(),key) ){
                f=true;
            }
            if(f){
                i++;
            }else{
                list.remove(i);
            }
        }
        return list;
    }

    private List<MedicineIndex> doNameMhFilter(List<MedicineIndex> list,String key,Set<String> setInter,Set<String> setEffect){  //进行名称模糊过滤
        for(int i=0;i<list.size();){
            boolean f=false;
            MedicineIndex medicineIndex=list.get(i);

            if( StringUtils.myMhContian(medicineIndex.getName(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineIndex.getCategory(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineIndex.getO1(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineIndex.getO2(),key) ){
                f=true;
            }else if(setInter.contains(medicineIndex.getO1())){
                f=true;
            }else if (setEffect.contains(medicineIndex.getO2())){
                f=true;
            }
            if(f){
                i++;
            }else{
                list.remove(i);
            }
        }
        return list;
    }

    private Set<String> doInterKeyFilter(List<MedicineInteraction> list,String key){   //进行在药物相互作用表中关键词过滤
        Set<String> res=new HashSet<>();
        for(int i=0;i<list.size();i++){
            boolean f=false;
            MedicineInteraction medicineInteraction=list.get(i);
            if(medicineInteraction.getName().contains(key)){
                f=true;
            }else if(medicineInteraction.getInteractionName().contains(key)){
                f=true;
            }else if(medicineInteraction.getResult().contains(key)){
                f=true;
            }else if(medicineInteraction.getAction().contains(key)){
                f=true;
            }
            if(f){
                res.add(medicineInteraction.getName());
                res.add(medicineInteraction.getInteractionName());
            }
        }
        return res;
    }

    private Set<String> doInterMhFilter(List<MedicineInteraction> list,String key){  //进行在药物相互作用表中模糊过滤
        Set<String> res=new HashSet<>();
        for(int i=0;i<list.size();i++){
            boolean f=false;
            MedicineInteraction medicineInteraction=list.get(i);
            if( StringUtils.myMhContian(medicineInteraction.getName(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineInteraction.getInteractionName(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineInteraction.getResult(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineInteraction.getAction(),key)){
                f=true;
            }
            if(f){
                res.add(medicineInteraction.getName());
                res.add(medicineInteraction.getInteractionName());
            }
        }
        return res;
    }

    private Set<String> doEffectMhFilter(List<MedicineEffect> list, String key){  //进行在药物药效表中模糊过滤
        Set<String> res=new HashSet<>();
        for(int i=0;i<list.size();i++){
            boolean f=false;
            MedicineEffect medicineEffect=list.get(i);
            if( StringUtils.myMhContian(medicineEffect.getName(),key) ){
                f=true;
            }else if(StringUtils.myMhContian(medicineEffect.getEffect(),key) ){
                f=true;
            }
            if(f){
                res.add(medicineEffect.getName());
            }
        }
        return res;
    }

    private Set<String> doEffectKeyFilter(List<MedicineEffect> list,String key){  //进行在药物药效表中模糊过滤
        Set<String> res=new HashSet<>();
        for(int i=0;i<list.size();i++){
            boolean f=false;
            MedicineEffect medicineEffect=list.get(i);
            if( medicineEffect.getName().contains(key) ){
                f=true;
            }else if(medicineEffect.getEffect().contains(key) ){
                f=true;
            }
            if(f){
                res.add(medicineEffect.getName());
            }
        }
        return res;
    }

    public void doOuterSearch(String s){   //用于平板模式，通过名称或类别查找
        editSearch.setText(s);
        buttonCancel.performClick();
        buttonFilter.performClick();
        radioMc.setChecked(true);
        radioGjc.setChecked(true);
        radioAllCf.setChecked(true);
        radioAllMx.setChecked(true);
        doSearch(s);
    }
    public void doOuterSearchCF(String s){   //用于平板模式，通过名称或类别查找
        editSearch.setText("");
        buttonCancel.performClick();
        buttonFilter.performClick();
        radioMc.setChecked(true);
        radioJq.setChecked(true);
        if(s.equals("处方药")){
            radioCf.setChecked(true);
        }else if(s.equals("非处方药")){
            radioFcf.setChecked(true);
        }else{
            radioAllCf.setChecked(true);
        }
        radioAllMx.setChecked(true);
        doSearch("");
    }
    public void doOuterSearchMX(String s){  //用于平板模式，通过明细类别查找
        editSearch.setText("");
        buttonCancel.performClick();
        buttonFilter.performClick();
        radioMc.setChecked(true);
        radioJq.setChecked(true);
        radioAllCf.setChecked(true);
        if(s.equals("品牌名称")){
            radioPp.setChecked(true);
        }else if(s.equals("通用名称")){
            radioTy.setChecked(true);
        }else{
            radioAllMx.setChecked(true);
        }
        doSearch("");
    }

    public void doSearch(String s){  //进行药物查询
        List<MedicineIndex> list;
        if(isFiltering){
            if(s.equals("")){
                list=DataSupport.findAll(MedicineIndex.class);
            }else{
                if(radioMc.isChecked()){
                    if(radioJq.isChecked()){
                        list=DataSupport.where("name = ? or category = ? or o1 = ? or o2 = ?",s,s,s,s).find(MedicineIndex.class);
                    }else if(radioMh.isChecked()){
                        list=DataSupport.findAll(MedicineIndex.class);
                        list=doNameMhFilter(list,s);
                    }else{
                        list=DataSupport.findAll(MedicineIndex.class);
                        list=doNameKeyFilter(list,s);
                    }
                }else{
                    list=DataSupport.findAll(MedicineIndex.class);
                    List<MedicineEffect> effectList=DataSupport.findAll(MedicineEffect.class);
                    List<MedicineInteraction> interactionList=DataSupport.findAll(MedicineInteraction.class);
                    Set<String> effectNameSet;
                    Set<String> interNameSet ;
                    if(radioMh.isChecked()){
                        effectNameSet=doEffectMhFilter(effectList,s);
                        interNameSet=doInterMhFilter(interactionList,s);
                        list=doNameMhFilter(list,s,interNameSet,effectNameSet);
                    }else if(radioGjc.isChecked()){
                        effectNameSet=doEffectKeyFilter(effectList,s);
                        interNameSet=doInterKeyFilter(interactionList,s);
                        list=doNameKeyFilter(list,s,interNameSet,effectNameSet);
                    }else{
                        return;
                    }

                }

            }
            list=doLastFilter(list);
        }else{

            if(s.equals("")){
                list=DataSupport.findAll(MedicineIndex.class);

            }else{
                list=DataSupport.where("name = ? or category = ? or o1 = ? or o2 = ?",s,s,s,s).find(MedicineIndex.class);
            }

        }

        if(list.size()<=0){
            textEmpty.setVisibility(View.VISIBLE);
        }
        medicineIndexList.clear();
        ywMedicineAdapter.notifyDataSetChanged();
        for(int i=0;i<list.size();i++){
            medicineIndexList.add(list.get(i));
        }
        ywMedicineAdapter.notifyDataSetChanged();
    }
    private void doSearch(){    //进行药物查询
        String s=editSearch.getText().toString().trim();
        if(!s.equals("")){
            addHis(s);
        }
        textEmpty.setVisibility(View.GONE);
        doSearch(s);
    }

}
