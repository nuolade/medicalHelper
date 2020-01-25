package com.example.wenda01.fragments.bz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.bingzheng.BzRecordActivity;
import com.example.wenda01.adapters.HistoryAdapter;
import com.example.wenda01.adapters.BzSymptomAdapter;
import com.example.wenda01.beans.bz.BzSymptom;
import com.example.wenda01.fragments.base.BzFabFragment;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.StringUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class BzGuideFragment extends BzFabFragment implements View.OnClickListener {   //病症引导碎片
    private List<BzSymptom> bzSymptomList;
    private ListView listView;
    private BzSymptomAdapter adapter;
    private ImageView imageVoice;
    private ImageView imageCancel;
    private Button buttonSearch;
    private EditText editSearch;
    private Button buttonHistory;
    private LinearLayout layoutHistory;
    private RecyclerView recyclerHistory;
    private List<String> hList;
    private HistoryAdapter historyAdapter;
    private Button buttonClear;
    private Button buttonRecord;
    private TextView textEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.bz_guide_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.bz_content_layout)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
//        ToastUtils.showS(""+isTwoPane);
        showIntor();
        initSymptoms();
        preWork();
        getHistory();
    }

    private void showIntor(){   //显示该模块的功能简介
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_bz_intor));
    }

    private void getHistory(){   //获得查询历史
        hList=new ArrayList<>();
        SharedPreferences sp=getContext().getSharedPreferences("history", Context.MODE_PRIVATE);
        int hSize=sp.getInt("bzHSize",0);
        for(int i=0;i<hSize;i++){
            hList.add(sp.getString("bzHItem"+i,null));
        }
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerHistory.setLayoutManager(manager);
        historyAdapter=new HistoryAdapter(hList,handlerStart);
        recyclerHistory.setAdapter(historyAdapter);
    }

    private void saveHis(List<String> list){   //存储查询记录
        SharedPreferences sp=getContext().getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("bzHSize",list.size());
        for(int i=0;i<list.size();i++){
            editor.remove("bzHItem"+i);
            editor.putString("bzHItem"+i,list.get(i));
        }
        editor.commit();
    }

    private void clearHistory(){  //清空查询历史
        SharedPreferences sp=getContext().getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("bzHSize",0);
        for(int i=0;i<MyApplication.HSIZE;i++){
            editor.remove("bzHItem"+i);
        }
        editor.commit();
        hList.clear();
        historyAdapter.notifyDataSetChanged();
    }

    protected void doHistoryItem(String s){   //选择历史记录内的选项
        if(s.equals("")){
            return;
        }
        doFindSym(s);
    }

    public void preWork(){

        buttonSearch=view.findViewById(R.id.button_search);
        imageVoice=view.findViewById(R.id.button_voice);
        editSearch=view.findViewById(R.id.edit_search);
        buttonHistory=view.findViewById(R.id.button_history);
        layoutHistory=view.findViewById(R.id.layout_history);
        recyclerHistory=view.findViewById(R.id.recycler_history);
        buttonClear=view.findViewById(R.id.button_history_clear);
        buttonRecord=view.findViewById(R.id.button_bz_record);
        imageCancel=view.findViewById(R.id.image_cancel);
        textEmpty=view.findViewById(R.id.text_res_empty);
        imageCancel.setOnClickListener(this);
        buttonRecord.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        imageVoice.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        layoutHistory.setVisibility(View.GONE);

        listView=view.findViewById(R.id.list_guide);
        adapter=new BzSymptomAdapter(getContext(),R.layout.guide_bz_item, bzSymptomList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BzSymptom bzSymptom = bzSymptomList.get(i);
                addHis(bzSymptom.getName());
                doOpenContentFragment(bzSymptom);
            }
        });
    }



    private void doVoiceSearch(){   //进行语音查询
        doFabSay();
    }

    private void doSearch(){    //进行病症查询
        String s=editSearch.getText().toString().trim();
        if(!s.equals("")){
            addHis(s);
        }
        doFindSym(s);
    }

    private void addHis(String s){  //添加新的历史查询记录
        if(!StringUtils.myContainInList(s,hList)){
            hList.add(s);
            if(hList.size()> MyApplication.HSIZE){
                hList.remove(0);
            }
            saveHis(hList);
            historyAdapter.notifyDataSetChanged();
        }
    }

    protected void getSayingWords(String s){   //获得语音的查询内容
        if(s.equals("")){
            return;
        }
        editSearch.setText(s.trim());
        doFindSym(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_voice:
                doVoiceSearch();
                break;
            case R.id.button_search:
                doSearch();
                break;
            case R.id.button_history:
                doHistory();
                break;
            case R.id.button_history_clear:
                clearHistory();
                break;
            case R.id.button_bz_record:
                openBzRecord();
                break;
            case R.id.image_cancel:
                editSearch.setText("");
                break;
        }
    }

    private void openBzRecord(){   //打开相应的病症内容
        if(isTwoPane){
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.bz_content_layout,new BzRecordFragment());
            transaction.commit();
        }else {
            BzRecordActivity.actionStart(getActivity());
        }
    }


    protected void doHistory(){   //更改历史记录的可见性
        if(layoutHistory.getVisibility()==View.GONE){
            layoutHistory.setVisibility(View.VISIBLE);
        }else{
            layoutHistory.setVisibility(View.GONE);
        }
    }


    private void initSymptoms(){   //从本地数据库获取全部病症名称
        bzSymptomList =DataSupport.findAll(BzSymptom.class);
    }

    protected void doFindSym(String s){   //查找病症，并跟新查找结果
        textEmpty.setVisibility(View.GONE);
        List<BzSymptom> list=DataSupport.findAll(BzSymptom.class);
        bzSymptomList.clear();
        if(s.equals("")){
            for(int i=0;i<list.size();i++){
                bzSymptomList.add(list.get(i));
            }
        }else{
            for(int i=0;i<list.size();i++){
                if(StringUtils.myMhContian(list.get(i).getName(),s)){
                    bzSymptomList.add(list.get(i));
                }
                if(list.get(i).getName().equals(s)){
                    doOpenContentFragment(list.get(i));
                }
            }
        }

        adapter.notifyDataSetChanged();
        if(bzSymptomList.size()<=0){
            textEmpty.setVisibility(View.VISIBLE);
        }
    }
}
