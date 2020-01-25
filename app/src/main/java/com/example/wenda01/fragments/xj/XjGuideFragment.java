package com.example.wenda01.fragments.xj;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.adapters.HistoryAdapter;
import com.example.wenda01.beans.xj.XjCkKnowledge;
import com.example.wenda01.fragments.base.XjFabFragment;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.StringUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class XjGuideFragment extends XjFabFragment implements View.OnClickListener {  //宣教引导碎片
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
    private RecyclerView recyclerView;
    private RecyclerView recyclerType;
    private List<XjCkKnowledge> list;
    private KnowledgeAdapter knowledgeAdapter;
    private TextView textEmpty;


    private String [] types={"全部","入院宣教","住院宣教","出院宣教","健康宣教","其他宣教"};
    private int [] indexs={5,0,1,2,3,4};
    private String [] index={"入院宣教","住院宣教","出院宣教","健康宣教",""};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xj_guide_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.xj_content_layout)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
        preWork();
        showIntor();
        getHistory();
    }

    private void preWork(){
        buttonSearch=view.findViewById(R.id.button_search);
        imageVoice=view.findViewById(R.id.button_voice);
        editSearch=view.findViewById(R.id.edit_search);
        buttonHistory=view.findViewById(R.id.button_history);
        layoutHistory=view.findViewById(R.id.layout_history);
        recyclerHistory=view.findViewById(R.id.recycler_history);
        buttonClear=view.findViewById(R.id.button_history_clear);
        recyclerType=view.findViewById(R.id.recycler_type);
        recyclerView=view.findViewById(R.id.list_xj);
        imageCancel=view.findViewById(R.id.image_cancel);
        textEmpty=view.findViewById(R.id.text_res_empty);

        buttonClear.setOnClickListener(this);
        imageVoice.setOnClickListener(this);
        imageCancel.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
        layoutHistory.setVisibility(View.GONE);

        list= DataSupport.findAll(XjCkKnowledge.class);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        knowledgeAdapter=new KnowledgeAdapter(list);
        recyclerView.setAdapter(knowledgeAdapter);

        LinearLayoutManager typeLayoutManager=new LinearLayoutManager(getActivity());
        typeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerType.setLayoutManager(typeLayoutManager);
        TypeAdapter typeAdapter=new TypeAdapter(types,indexs);
        recyclerType.setAdapter(typeAdapter);


    }


    private void showIntor(){  //显示该模块的功能简介
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_xj_intor));
    }

    private void getHistory(){   //获得查询历史
        hList=new ArrayList<>();
        SharedPreferences sp=getContext().getSharedPreferences("history", Context.MODE_PRIVATE);
        int hSize=sp.getInt("xjHSize",0);
        for(int i=0;i<hSize;i++){
            hList.add(sp.getString("xjHItem"+i,null));
        }
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerHistory.setLayoutManager(manager);
        historyAdapter=new HistoryAdapter(hList,handlerStart);
        recyclerHistory.setAdapter(historyAdapter);
    }

    private void saveHis(List<String> list){   //存储查询记录
        SharedPreferences sp=getContext().getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("xjHSize",list.size());
        for(int i=0;i<list.size();i++){
            editor.remove("xjHItem"+i);
            editor.putString("xjHItem"+i,list.get(i));
        }
        editor.commit();
    }

    private void clearHistory(){   //存储查询记录
        SharedPreferences sp=getContext().getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("xjHSize",0);
        for(int i = 0; i< MyApplication.HSIZE; i++){
            editor.remove("xjHItem"+i);
        }
        editor.commit();
        hList.clear();
        historyAdapter.notifyDataSetChanged();
    }

    protected void doHistoryItem(String s){   //选择历史记录内的选项
        if(s.equals("")){
            return;
        }
        doFindKnow(s);
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
            case R.id.image_cancel:
                editSearch.setText("");
                break;
        }
    }

    private void doFindKnow(String s){  //查找相关宣教知识
        textEmpty.setVisibility(View.GONE);
        List<XjCkKnowledge> list2;
        list2=DataSupport.findAll(XjCkKnowledge.class);
        list.clear();
        if(s.equals("")){
            for(int i=0;i<list2.size();i++){
                list.add(list2.get(i));
            }
        }else{
            for(int i=0;i<list2.size();i++){
                if(StringUtils.myMhContian(list2.get(i).getContent(),s)){
                    list.add(list2.get(i));
                }
            }
        }

        knowledgeAdapter.notifyDataSetChanged();
        if(list.size()<=0){
            textEmpty.setVisibility(View.VISIBLE);
        }
    }

    protected void doHistory(){   //更改历史记录的可见性
        if(layoutHistory.getVisibility()==View.GONE){
            layoutHistory.setVisibility(View.VISIBLE);
        }else{
            layoutHistory.setVisibility(View.GONE);
        }
    }

    private void doVoiceSearch(){   //进行语音查询
        doFabSay();
    }

    protected void getSayingWords(String s){   //获得语音的查询内容
        editSearch.setText(s.trim());
        doSearch();
    }

    private void doSearch(){    //进行病症查询
        String s=editSearch.getText().toString().trim();
        if(!s.equals("")){
            addHis(s);
        }

        doFindKnow(s);
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

    private void doTypeCHoose(int i){   //查找某一类别的宣教知识
        List<XjCkKnowledge> list2;
        if(i<4){
            String key=index[i];
            list2= DataSupport.where("label = ?",key).find(XjCkKnowledge.class);
        }else{
            list2=DataSupport.findAll(XjCkKnowledge.class);
        }

        list.clear();
        for(int j=0;j<list2.size();j++){
            list.add(list2.get(j));
        }
        knowledgeAdapter.notifyDataSetChanged();

    }

    class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.ViewHolder> {  //宣教知识适配器

        private List<XjCkKnowledge> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView klTitleText;
            public ViewHolder(View view){
                super(view);
                klTitleText=view.findViewById(R.id.guide_item_name);
            }
        }

        public KnowledgeAdapter(List<XjCkKnowledge> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_xj_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XjCkKnowledge xjCkKnowledge =mList.get(holder.getAdapterPosition());
                    openKnow(xjCkKnowledge);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            XjCkKnowledge xjCkKnowledge =mList.get(position);
            String [] arr= xjCkKnowledge.getTitle().split("/");
            holder.klTitleText.setText(""+(position+1)+". "+arr[0]);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder>{  //宣教知识类别适配器
        private String [] types;
        private int [] indexs;


        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textName;

            public ViewHolder(View view){
                super(view);
                textName=view.findViewById(R.id.history_text);
            }
        }

        public TypeAdapter(String [] mList, int [] indexs) {
            this.types = mList;
            this.indexs=indexs;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            holder.textName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p=holder.getAdapterPosition();
                    int ind=indexs[p];
                    doTypeCHoose(ind);
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String s=types[position];
            holder.textName.setText(s);
        }

        @Override
        public int getItemCount() {
            return types.length;
        }
    }
}
