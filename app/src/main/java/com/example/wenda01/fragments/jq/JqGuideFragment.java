package com.example.wenda01.fragments.jq;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.activities.jieqi.JqContentActivity;
import com.example.wenda01.activities.weather.WeatherStartActivity;
import com.example.wenda01.adapters.ArrayWheelAdapter;
import com.example.wenda01.beans.jq.JqAllResult;
import com.example.wenda01.fragments.base.JqFabFragment;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.Lunar;
import com.example.wenda01.views.Wheel.WheelView;


import java.util.ArrayList;
import java.util.List;

public class JqGuideFragment extends JqFabFragment implements View.OnClickListener {  //节气引导碎片
    private View view;
    private boolean isTwoPane;

    private Button buttonWeather;
    private ImageView buttonSpring;
    private ImageView buttonSummer;
    private ImageView buttonFall;
    private ImageView buttonWinter;
    private RecyclerView recyclerView;
    private LinearLayout layoutButton;
    private List<JqAllResult.Content.Jq> showList;
    private JqAdapter jqAdapter;

    private String [] arrTime={"2月3-4日","2月18-19日","3月5-6日","3月20-21日","4月4-6日","4月19-20日"
            ,"5月5-6日","5月20-22日","6月5-6日","6月21-22日","7月7-8日","7月22-23日"
            ,"8月6-9日","8月22-24日","9月7-8日","9月22-24日","10月7-9日","10月23-24日"
            ,"11月7-8日","11月22-23日","12月7-8日","12月21-23日","1月5-6日","1月19-21日"};
    private int [] arrId={1,2,3,4,5,24,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};

    private WheelView jqWheel;
    private int nearJq=0; //最近时间节气编号

    private ArrayList<String> createJqArrays(){
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<24;i++){
            int len=arrTime[i].length();
            String s="";
            for(int j=len;j<10;j++){
                s+="  ";
            }
            list.add(arrJq[i]+s+" ("+arrTime[i]+")");
        }
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.jq_guide_frag,container,false );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.jq_content_layout)!=null){
            isTwoPane=true;
        }else {
            isTwoPane=false;
        }
        getNearJq();
        preWork();
        setRecyclerAdapter();
        setWheel();
        setButtonEnable(true);
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_jq_intor));
        buttonSpring.performClick();
        jqWheel.setSelection(nearJq);

    }

    private void getNearJq(){   //根据当前时间获得下一个最近节气
        for(int i=0;i<15;i++){
            Lunar l = new Lunar(System.currentTimeMillis()+86400000*i);
            String jq=l.getTermString().trim();
            if(jq!=null && !jq.equals("")){
                for(int j=0;j<arrJq.length;j++){
                    if(arrJq[j].equals(jq)){
                        nearJq=j;
                        break;
                    }
                }

            }
        }
//        Lunar l = new Lunar(System.currentTimeMillis()+86400000*11);
//        System.out.println("节气:" + l.getTermString());
//        System.out.println("干支历:" + l.getCyclicalDateString());
//        System.out.println("星期" + l.getDayOfWeek());
//        System.out.println("农历" + l.getLunarDateString());

    }

    private void setWheel(){   //设置滚轮控件的显示效果
        jqWheel = (WheelView) view.findViewById(R.id.jq_wheel);
        jqWheel.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        jqWheel.setSkin(WheelView.Skin.Holo);
        jqWheel.setWheelData(createJqArrays());
        jqWheel.setLoop(true);
        jqWheel.setWheelClickable(true);
        jqWheel.setSelection(nearJq);
//        jqWheel.setClickToPosition(true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        if(isTwoPane){
            jqWheel.setWheelSize(13);
            style.selectedTextSize = 28;
            style.textSize = 25;
        }else {
            jqWheel.setWheelSize(7);
            style.selectedTextSize = 22;
            style.textSize = 18;
        }

        jqWheel.setStyle(style);
        jqWheel.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onItemClick(int position, Object o) {
//                WheelUtils.log("click:" + position);
                showJqDetail(arrId[position]);
            }
        });
        jqWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
//                WheelUtils.log("selected:" + position);
                if(isTwoPane){
                    showJqDetail(arrId[position]);
                }
            }
        });
        MyApplication.setWheelView(jqWheel);
    }

    private void showJqDetail(int id){   //打开节气的相应信息
        if(isTwoPane){
            JqContentFragment jqContentFragment=new JqContentFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("id",id);
            bundle.putBoolean("isTwoPane",isTwoPane);
            jqContentFragment.setArguments(bundle);

            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.jq_content_layout,jqContentFragment);
            transaction.commit();
        }else{
            JqContentActivity.actionStart(getActivity(),id,isTwoPane);
        }
    }

    private void setButtonEnable(boolean b){  //设置按钮是否可用
        buttonSpring.setEnabled(b);
        buttonSummer.setEnabled(b);
        buttonFall.setEnabled(b);
        buttonWinter.setEnabled(b);
        buttonSpring.setClickable(b);
        buttonSummer.setClickable(b);
        buttonFall.setClickable(b);
        buttonWinter.setClickable(b);
    }

    private void preWork(){
        buttonSpring=view.findViewById( R.id.button_jq_spring);
        buttonSummer=view.findViewById( R.id.button_jq_summer);
        buttonFall=view.findViewById(   R.id.button_jq_fall);
        buttonWinter=view.findViewById( R.id.button_jq_winter);

        buttonWeather=view.findViewById(R.id.button_weather);
//        buttonBack=view.findViewById(   R.id.button_jq_back);
        layoutButton=view.findViewById( R.id.layout_jq_button);
//        layoutList=view.findViewById(   R.id.layout_jq_list);
        recyclerView=view.findViewById(R.id.list_jq);

        buttonSpring.setOnClickListener(this);
        buttonSummer.setOnClickListener(this);
        buttonFall.setOnClickListener(this);
        buttonWinter.setOnClickListener(this);
        buttonWeather.setOnClickListener(this);
//        buttonBack.setOnClickListener(this);
        buttonSpring.setClickable(false);
        buttonSummer.setClickable(false);
        buttonFall.setClickable(false);
        buttonWinter.setClickable(false);

//        layoutList.setVisibility(View.GONE);
        layoutButton.setVisibility(View.VISIBLE);
//        setButtonEnable(false);

        showList=new ArrayList<>();

    }

    private void setRecyclerAdapter(){   //设置各个季节节气的适配器
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager layoutManager1=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        jqAdapter=new JqAdapter(showList);
        recyclerView.setAdapter(jqAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_jq_spring:
                showJqList(0);
//                sendRequestWithOkHttp();
//                sendRequestWithHttpURLConnection();
                break;
            case R.id.button_jq_summer:
                showJqList(1);
                break;
            case R.id.button_jq_fall:
                showJqList(2);
                break;
            case R.id.button_jq_winter:
                showJqList(3);
                break;
            case R.id.button_weather:
                openWeather();
                break;
        }
    }

    private void openWeather(){
        Intent intent=new Intent(getActivity(), WeatherStartActivity.class);
        startActivity(intent);
    }

    private void showJqList(int index){   //更改不同节气对应的节气
        jqWheel.setSelection(6*index);
//        layoutButton.setVisibility(View.GONE);
//        layoutList.setVisibility(View.VISIBLE);
        showList.clear();
//        Toast.makeText(getContext(),""+index,Toast.LENGTH_SHORT).show();
        for(int i=0;i<6;i++){
            JqAllResult.Content.Jq jq=new JqAllResult.Content.Jq();
            jq.setId(arrId[index*6+i]);
            jq.setName(arrJq[index*6+i]);
            showList.add(jq);
        }

        jqAdapter.notifyDataSetChanged();

    }


    class JqAdapter extends RecyclerView.Adapter<JqAdapter.ViewHolder> {   //四季对应节气的适配器

        private List<JqAllResult.Content.Jq> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView klTitleText;
            public ViewHolder(View view){
                super(view);
                klTitleText=view.findViewById(R.id.guide_item_name);
            }
        }

        public JqAdapter(List<JqAllResult.Content.Jq> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_jq_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JqAllResult.Content.Jq jieQi =mList.get(holder.getAdapterPosition());
                    showJqDetail(jieQi.getId());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            JqAllResult.Content.Jq jieQi=mList.get(position);
            holder.klTitleText.setText(jieQi.getName());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
