package com.example.wenda01.fragments.qa;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.qa.QaContentActivity;
import com.example.wenda01.adapters.QaSessionAdapter;
import com.example.wenda01.beans.qa.QaMsg;
import com.example.wenda01.beans.qa.QaSession;
import com.example.wenda01.fragments.base.FabFragment;
import com.example.wenda01.utils.AccountKeeper;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.SaveUtils;
import com.example.wenda01.utils.TimeUtils;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

public class QaGuideFragment extends FabFragment implements View.OnClickListener {   //问答引导碎片
    private Button buttonSearch;
    private Button buttonFlash;
    private Button buttonCreate;
    private Button buttonDel;
    private EditText editPhone;
    private EditText editName;
    private ImageView imageShow;
    private RecyclerView recyclerSession;
    private EditText  editNameCre;
    private EditText  editPhoneCre;
    private ImageView imageNameCreCl;
    private ImageView imagePhoneCreCl;
    private ImageView imageNameCl;
    private ImageView imagePhoneCl;
    private Button    buttonCancel;
    private Button    buttonConfirm;
    private LinearLayout layoutCreate;
    private LinearLayout layoutCon;
    private LinearLayout layoutButton;

    private QaSessionAdapter qaSessionAdapter;
    private List<QaSession> sessionList;
    private int cnt=0;  //累计会话数目
    private String phone="";


    private Handler qaHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Ks.QA_OPENSESSION:
                    QaSession qaSession=(QaSession) msg.obj;
                    openSession(qaSession);
                    break;
            }
        }
    };

    private void openSession(QaSession qaSession){  //打开会话内容
//        ToastUtils.showS(qaSession.getName());
        if(isTwoPane){
            QaContentFragment qaContentFragment=new QaContentFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("session",qaSession);
            qaContentFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.qa_content_layout,qaContentFragment);
            transaction.commit();
        }else{
            QaContentActivity.actionStart(getActivity(),qaSession);
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.qa_guide_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.qa_content_layout)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
        preWork();
        getData();
        setAdapter();
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_qa_intor));
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonFlash.performClick();
    }

    protected void preWork(){
        buttonSearch=view.findViewById(R.id.button_search);
        buttonFlash =view.findViewById(R.id.button_flash);
        buttonCreate=view.findViewById(R.id.button_create);
        editPhone=view.findViewById(R.id.edit_phone);
        editName=view.findViewById(R.id.edit_name);
        recyclerSession=view.findViewById(R.id.recycler_session);
        editNameCre=view.findViewById(R.id.edit_create_name);
        editPhoneCre=view.findViewById(R.id.edit_create_phone);
        imageNameCreCl=view.findViewById(  R.id.image_name_cre_cancel);
        imagePhoneCreCl=view.findViewById( R.id.image_phone_cre_cancel);
        buttonCancel=view.findViewById( R.id.button_cancel);
        buttonConfirm=view.findViewById(R.id.button_confirm);
        buttonDel=view.findViewById(R.id.button_del);
        layoutCreate=view.findViewById(R.id.layout_create);
        layoutCon=view.findViewById(R.id.layout_condition);
        layoutButton=view.findViewById(R.id.layout_button);
        imageNameCl=view.findViewById(R.id.image_name_cancel);
        imagePhoneCl=view.findViewById(R.id.image_phone_cancel);
        imageShow=view.findViewById(R.id.image_show);

        imageShow.setOnClickListener(this);
        imageNameCreCl.setOnClickListener(this);
        imagePhoneCreCl.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonConfirm.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonFlash .setOnClickListener(this);
        buttonCreate.setOnClickListener(this);
        imageNameCl.setOnClickListener(this);
        imagePhoneCl.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
    }

    protected void getData(){   //获得会话有关的初始化数据
//        sessionList=new ArrayList<>();
        if(AccountKeeper.isOnline()){
            phone=AccountKeeper.getLastPhone();
            editPhone.setText(phone);
        }
//        phone="13589796323";
        sessionList= DataSupport.order("timeLast desc").find(QaSession.class);
        MyApplication.setQaSesDel(Ks.QA_MSG_UNDEL);
    }

    private void setAdapter(){   //设置会话适配器
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        qaSessionAdapter=new QaSessionAdapter(sessionList,qaHandler);
        recyclerSession.setLayoutManager(layoutManager);
        recyclerSession.setAdapter(qaSessionAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
                doSearch();
                break;
            case R.id.button_flash:
                doFlash();
                break;
            case R.id.button_create:
                showCreateLayout();
//                doCreate();
                break;
            case R.id.image_name_cre_cancel:
                editNameCre.setText("");
                break;
            case R.id.image_phone_cre_cancel:
                editPhoneCre.setText("");
                break;
            case R.id.image_name_cancel:
                editName.setText("");
                break;
            case R.id.image_phone_cancel:
                editPhone.setText("");
                break;
            case R.id.button_cancel:
                hideCreateLayout();
                break;
            case R.id.button_confirm:
                doCreate();
                break;
            case R.id.image_show:
                changeVis();
                break;
            case R.id.button_del:
                doDel();
                break;
        }
    }

    private void doDel(){   //更新删除按钮样式
        if(MyApplication.getQaSesDel()==Ks.QA_MSG_DEL){
            MyApplication.setQaSesDel(Ks.QA_MSG_UNDEL);
            qaSessionAdapter.notifyDataSetChanged();
            buttonDel.setText("删除对话");
        }else{
            MyApplication.setQaSesDel(Ks.QA_MSG_DEL);
            qaSessionAdapter.notifyDataSetChanged();
            buttonDel.setText("取消删除");
        }
    }

    private void changeVis(){   //更改子布局可见性
        if(layoutCon.getVisibility()==View.GONE){
            layoutCon.setVisibility(View.VISIBLE);
            imageShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.up));
        }else{
            layoutCon.setVisibility(View.GONE);
            imageShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.down));
        }
    }

    private void doSearch(){  //查找会话
        String p=editPhone.getText().toString().trim();
        String n=editName.getText().toString().trim();
        List<QaSession> list;
        sessionList.clear();
        if(n.equals("")){
            if(p.equals("")){
                list=DataSupport.findAll(QaSession.class);
            }else{
                list=DataSupport.where("phone = ?",p).order("timeLast desc").find(QaSession.class);
            }
            for(int i=0;i<list.size();i++){
                sessionList.add(list.get(i));
            }
        }else{
            if(p.equals("")){
                list=DataSupport.findAll(QaSession.class);
            }else{
                list=DataSupport.where("phone = ?",p).order("timeLast desc").find(QaSession.class);
            }
            for(int i=0;i<list.size();i++){
                String n2=list.get(i).getName();
                if(n2.contains(n)){
                    sessionList.add(list.get(i));
                }
            }
        }

        qaSessionAdapter.notifyDataSetChanged();
    }
    private void doFlash(){   //刷新会话
        List<QaSession> list=DataSupport.order("timeLast desc").find(QaSession.class);
        sessionList.clear();
        for(int i=0;i<list.size();i++){
            sessionList.add(list.get(i));
        }
        qaSessionAdapter.notifyDataSetChanged();
    }
    private void doCreate(){   //新建会话
        String p=editPhoneCre.getText().toString().trim();
        if(p.equals("")){
            ToastUtils.showS("未填入手机号，将不会和账号关联");
        }
        cnt= SaveUtils.getNewSessionNo();
        String name=editNameCre.getText().toString().trim();
        if(name.equals("") || name.equals("对话")){
            name="对话"+cnt;
        }
        QaSession qaSession=new QaSession(TimeUtils.getTime(),TimeUtils.getTime(),name,"",cnt,p,0);
        qaSession.save();
        QaMsg qaMsg=new QaMsg(TimeUtils.getTime(),getResources().getString(R.string.text_qa_init),p,cnt,Ks.QA_RECEIVED,0);
        qaMsg.save();
        sessionList.add(qaSession);
        qaSessionAdapter.notifyDataSetChanged();

        hideCreateLayout();
        recyclerSession.scrollToPosition(sessionList.size()-1);
    }
    private void showCreateLayout(){   //显示新建会话子布局
        layoutButton.setVisibility(View.GONE);
        layoutCreate.setVisibility(View.VISIBLE);
        editPhoneCre.setText(phone);
        editNameCre.setText("对话");
    }
    private void hideCreateLayout(){   //隐藏新建会话子布局
        layoutCreate.setVisibility(View.GONE);
        layoutButton.setVisibility(View.VISIBLE);
    }
}

