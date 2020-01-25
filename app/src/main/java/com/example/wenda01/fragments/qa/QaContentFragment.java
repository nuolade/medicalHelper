package com.example.wenda01.fragments.qa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.adapters.QaMsgAdapter;
import com.example.wenda01.beans.qa.QaMsg;
import com.example.wenda01.beans.qa.QaSession;
import com.example.wenda01.fragments.base.FabFragment;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.QaUtils;
import com.example.wenda01.utils.TimeUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class QaContentFragment extends FabFragment implements View.OnClickListener {  //问答会话聊天记录碎片
    private TextView textTitle;
    private Button buttonSend;
    private Button buttonSay;
    private Button buttonSound;
    private Button buttonDel;
    private EditText editInput;
    private ImageView imageCancel;
    private RecyclerView recyclerMsg;
    private QaMsgAdapter qaMsgAdapter;
    private List<QaMsg> msgList;
    private String phone;
    private int sNo; //会话编号
    private QaSession qaSession; //会话
    private int cnt; //累计消息数目

    private String [] strings={
            "我浑身酸痛是什么病",
            "症状浑身酸痛可能染上的疾病有:急性细菌性前列腺炎;病毒性上呼吸道感染;老年人重症肌无力;风热感冒;小儿嗜血性流行性感冒杆菌脑膜炎",
            "风热感冒有什么症状",
            "风热感冒的症状包括:咽痛;咳出黄色痰液;情绪性感冒;浑身酸痛;鼻塞;小儿发烧后身上起红点",
            "风寒感冒有什么症状",
            "风寒感冒的症状包括:恶寒;情绪性感冒;背心冷:鼻塞;喉部有痰;流鼻涕;小儿发烧后身上起红点;风寒咳嗽;外寒内热;扁桃体发炎",
            "风热感冒需要吃什么药",
            "风热感冒通常的使用的药品包括:维C银翘片;阿司匹林片;复方感冒灵颗粒;阿司匹林肠溶胶囊;阿司匹林肠溶片;维C银翘胶囊;牛磺酸颗粒",
            "风热感冒多久才好",
            "风热感冒治疗可能持续的周期为：7-14天"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.qa_content_fragment,container,false);
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preWork();
        updateSound();
        getData();
        initData();
        setAdapter();
//        ToastUtils.showS("aaa");
    }

    private void updateSound(){  //问答会话聊天记录碎片
        if(MyApplication.isIsSoundOpen()){
            buttonSound.setText("声音：开");
        }else{
            buttonSound.setText("声音：关");
        }
    }

    private void setAdapter(){  //问答会话聊天记录碎片
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerMsg.setLayoutManager(layoutManager);
        qaMsgAdapter=new QaMsgAdapter(msgList);
        recyclerMsg.setAdapter(qaMsgAdapter);
        qaMsgAdapter.notifyDataSetChanged();
//        ToastUtils.showS(""+msgList.size());
    }

    private void preWork(){
        buttonSay=view.findViewById(R.id.button_say);
        buttonSend=view.findViewById(R.id.button_send);
        buttonDel=view.findViewById(R.id.button_del);
        buttonSound=view.findViewById(R.id.button_sound);
        editInput=view.findViewById(R.id.edit_msg_input);
        recyclerMsg=view.findViewById(R.id.recycler_msg);
        textTitle=view.findViewById(R.id.text_title);
        imageCancel=view.findViewById(R.id.image_cancel);
        imageCancel.setOnClickListener(this);
        buttonSay.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
        buttonSound.setOnClickListener(this);
        buttonDel.setOnClickListener(this);

        msgList=new ArrayList<>();

    }

    protected void getData(){  //从活动中获得会话的初始化参数
        qaSession=(QaSession) getArguments().getSerializable("session");
        textTitle.setText(qaSession.getName());
        sNo=qaSession.getNo();
        phone=qaSession.getPhone();
        cnt=qaSession.getCnt();
        msgList= DataSupport.where("session = ?",""+sNo).order("time asc").find(QaMsg.class);
        MyApplication.setQaMsgDel(Ks.QA_MSG_UNDEL);
    }

    private void updateSession(QaMsg msg,int cnt){  //更新会话

        QaSession s2=new QaSession();
        s2.setTextLast(msg.getText());
        s2.setTimeLast(msg.getTime());
        if(cnt>0){
            s2.setCnt(cnt);
        }
//        QaSession session=new QaSession(qaSession.getTimeCreated(),msg.getTime(),qaSession.getName(),msg.getText(),sNo,phone);
        s2.updateAll("no = ? and phone = ?",""+sNo,phone);
    }

    private void initData(){
        for(int i=0;i<strings.length;i++){
            int t=(i+1)%2;
            if(i%2==0){
                cnt++;
            }
            QaMsg msg=new QaMsg(TimeUtils.getTime(),strings[i],phone,sNo,t,cnt);
            msgList.add(msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_say:
                doSay();
                break;
            case R.id.button_send:
                doSend();
                break;
            case R.id.image_cancel:
                editInput.setText("");
                break;
            case R.id.button_del:
                doDel();
                break;
            case R.id.button_sound:
                MyApplication.changeSoundOpen();
                updateSound();
                break;
        }
    }

    private void doDel(){  //更新删除按钮外观
        if(MyApplication.getQaMsgDel()==Ks.QA_MSG_DEL){
            MyApplication.setQaMsgDel(Ks.QA_MSG_UNDEL);
            qaMsgAdapter.notifyDataSetChanged();
            buttonDel.setText("删除");
        }else{
            MyApplication.setQaMsgDel(Ks.QA_MSG_DEL);
            qaMsgAdapter.notifyDataSetChanged();
            buttonDel.setText("取消");
        }
    }

    private void doSay(){  //进行语音输入
        doFabSay();
    }

    protected void getSayingWords(String s){   //获得并语音输入的文字内容
        editInput.setText(s);
    }

    private void doSend(){   //发送问题，并更新会话
        String word=editInput.getText().toString().trim();
        if(word.equals("")){
            return;
        }
        cnt++;
        QaMsg msg=new QaMsg(TimeUtils.getTime(),word,phone,sNo,Ks.QA_SENT,cnt);
        msg.save();
        updateSession(msg,cnt);


        msgList.add(msg);
        qaMsgAdapter.notifyItemInserted(msgList.size()-1);
        recyclerMsg.scrollToPosition(msgList.size()-1);
        editInput.setText("");

        getAnswer(msg);
    }

    private void getAnswer(QaMsg msg){   //发送问题，并更新会话
        QaMsg qaMsg= QaUtils.getAnswer(msg);
        msgList.add(qaMsg);
        qaMsgAdapter.notifyItemInserted(msgList.size()-1);
        recyclerMsg.scrollToPosition(msgList.size()-1);
        updateSession(qaMsg,-1);
    }
}
