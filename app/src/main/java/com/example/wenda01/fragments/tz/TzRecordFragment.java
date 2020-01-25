package com.example.wenda01.fragments.tz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenda01.R;
import com.example.wenda01.activities.tizhi.TzResultActivity;
import com.example.wenda01.adapters.TzOneAdapter;
import com.example.wenda01.beans.tz.TzOneRecord;
import com.example.wenda01.beans.tz.TzRecordFirstResult;
import com.example.wenda01.beans.tz.TzRecordChildResult;
import com.example.wenda01.beans.tz.TzRecordAdultResult;
import com.example.wenda01.beans.tz.TzRecordWxResult;
import com.example.wenda01.fragments.base.TzFabFragment;
import com.example.wenda01.utils.AccountKeeper;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.Ks;
import com.example.wenda01.utils.RegularUtils;
import com.example.wenda01.utils.TimeUtils;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TzRecordFragment extends TzFabFragment implements View.OnClickListener {   //体质记录查询碎片
    private EditText editName;
    private EditText editPhone;
    private EditText editSy;
    private EditText editSm;
    private EditText editSd;
    private EditText editEy;
    private EditText editEm;
    private EditText editEd;
    private RecyclerView recyclerView;
    private RecyclerView recyclerImage;
    private Button buttonSearch;
    private Button buttonClear;
    private RadioButton radioAdult;
    private RadioButton radioChild;
    private RadioButton radioWx;
    private RadioButton radioOne;
    private TextView textEmpty;
    private LinearLayout layoutCon;
    private ImageView imageShow;
    private LinearLayout layoutImage;
    private LinearLayout layoutList;
    private LinearLayout layoutCheckAll;
    private LinearLayout layoutCheck;

    private int type; //模块编号

    private int [] yWLen={7,8,8,8,7,6,6,7,8}; //年轻女性问题数量
    private int [] yMLen={7,8,8,8,7,6,7,7,8};  //年轻男性问题数量
    private int [] oWLen={4,4,4,4,4,4,4,4,4};   //老年女性问题数量
    private int [] oMLen={4,4,4,4,4,4,4,4,4};   //老年男性问题数量
    private int [] adLen={4,4,4,4,4,4,4,4,5}; //成人常规问题数量
    private int [] chLen={8,8,8,8,8,7,8};   //儿童问题数量
    private int [] wxLen={13,14,14,11,15}; //五型问题数量
    private int [] kLen={7,8,8,8,7,6,7,7,8}; //成人关键问题数量

    private String [] types={"5","11.1","7"}; //访问服务器模块编号
    private RecyclerView.Adapter myAdapter;
    private List myList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tz_record_frag,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.tz_guide_fragment)!=null){
            isTwoPane=true;
        }else{
            isTwoPane=false;
        }
        showIntor();
        preWork();
    }

    private void showIntor(){
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_tz_record_intor));
    }

    private void preWork(){
        editName=view.findViewById(R.id.tz_edit_name);
        editPhone=view.findViewById(R.id.tz_edit_phone);
        editSy=view.findViewById(R.id.edit_s_y);
        editSm=view.findViewById(R.id.edit_s_m);
        editSd=view.findViewById(R.id.edit_s_d);
        editEy=view.findViewById(R.id.edit_e_y);
        editEm=view.findViewById(R.id.edit_e_m);
        editEd=view.findViewById(R.id.edit_e_d);
        recyclerView=view.findViewById(R.id.tz_record_recycler);
        recyclerImage=view.findViewById(R.id.recycler_one);
        buttonSearch=view.findViewById(R.id.button_search);
        buttonClear=view.findViewById(R.id.button_clear);
        textEmpty=view.findViewById(R.id.text_res_empty);
        imageShow=view.findViewById(R.id.image_show);
        layoutCon=view.findViewById(R.id.layout_condition);
        layoutCheckAll=view.findViewById(R.id.layout_check_ones);
        layoutCheck=view.findViewById(R.id.layout_check);
        layoutImage=view.findViewById(R.id.layout_image);
        layoutList=view.findViewById(R.id.layout_list);
        radioOne  =view.findViewById(R.id.radio_sign_one);
        radioAdult=view.findViewById(R.id.radio_sign_adult);
        radioChild=view.findViewById(R.id.radio_sign_child);
        radioWx   =view.findViewById(R.id.radio_sign_wx);
        buttonSearch.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        imageShow.setOnClickListener(this);
        radioOne  .setOnClickListener(this);
        radioAdult.setOnClickListener(this);
        radioChild.setOnClickListener(this);
        radioWx   .setOnClickListener(this);
        layoutImage.setVisibility(View.GONE);
        layoutCheckAll.setVisibility(View.GONE);
        layoutCon.setVisibility(View.VISIBLE);
        textEmpty.setVisibility(View.GONE);
        if(AccountKeeper.isOnline()){
            editPhone.setText(AccountKeeper.getLastPhone());
            editName.setText(AccountKeeper.getAccount().getName());
        }
        editPhone.setText("13589796323");
        myAdapter=null;
        setDate();
    }

    private void setDate(){   //初始化查询时间
        //使用默认时区和语言环境获得一个日历。
        Calendar rightNow    =    Calendar.getInstance();
		/*用Calendar的get(int field)方法返回给定日历字段的值。
		HOUR 用于 12 小时制时钟 (0 - 11)，HOUR_OF_DAY 用于 24 小时制时钟。*/
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1; //第一个月从0开始，所以得到月份＋1
        Integer day = rightNow.get(rightNow.DAY_OF_MONTH);
        editSy.setText(""+(year-1));
        editSm.setText("1");
        editSd.setText("1");
        editEy.setText(""+year);
        editEm.setText(""+month);
        editEd.setText(""+day);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
                doSearch();
                break;
            case R.id.button_clear:
                doClear();
                break;
            case R.id.image_show:
                doChangeShow();
                break;
            case R.id.radio_sign_one:
                setOnesVis(true);
                break;
            case R.id.radio_sign_adult:
                setOnesVis(false);
                break;
            case R.id.radio_sign_child:
                setOnesVis(false);
                break;
            case R.id.radio_sign_wx:
                setOnesVis(false);
                break;

        }
    }

    private void setOnesVis(boolean b){   //设置单项检测查找控件可见性
        if(b){
            layoutCheckAll.setVisibility(View.VISIBLE);
            layoutImage.setVisibility(View.VISIBLE);
            layoutList.setVisibility(View.GONE);
        }else{
            layoutCheckAll.setVisibility(View.GONE);
            layoutImage.setVisibility(View.GONE);
            layoutList.setVisibility(View.VISIBLE);
        }
    }

    private void doChangeShow(){  //更改查找条件布局可见性
        if(layoutCon.getVisibility()==View.GONE){
            layoutCon.setVisibility(View.VISIBLE);
            imageShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.up));
        }else{
            layoutCon.setVisibility(View.GONE);
            imageShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.down));
        }
    }

    private void doClear(){   //清空查找条件
        editName.setText("");
        editPhone.setText("");
        editSy.setText("");
        editSm.setText("");
        editSd.setText("");
        editEy.setText("");
        editEm.setText("");
        editEd.setText("");
    }

    private void doSearch(){  //查找记录
//        Toast.makeText(getContext(),"姓名和手机不可都为空",Toast.LENGTH_SHORT).show();
        String name=editName.getText().toString().trim();
        String phone=editPhone.getText().toString().trim();
        if(name.equals("") && phone.equals("")){
            Toast.makeText(getContext(),"姓名和手机不可都为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(radioOne.isChecked() && phone.equals("")){
            Toast.makeText(getContext(),"手机不可都为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String sy=editSy.getText().toString().trim();
        String sm=editSm.getText().toString().trim();
        String sd=editSd.getText().toString().trim();
        String ey=editEy.getText().toString().trim();
        String em=editEm.getText().toString().trim();
        String ed=editEd.getText().toString().trim();
        String st;
        String et;
        if(sy.equals("") && sm.equals("") && sd.equals("")){
            st="";
        }else{
            if(RegularUtils.isNum(sy)
                    && RegularUtils.isNum(sm)
                    && RegularUtils.isNum(sd)){
                st=sy+"-"+sm+"-"+sd;
            }else{
                Toast.makeText(getContext(),"开始日期填入有误",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(ey.equals("") && em.equals("") && ed.equals("")){
            et="";
        }else{
            if(RegularUtils.isNum(ey)
                    && RegularUtils.isNum(em)
                    && RegularUtils.isNum(ed)){
                et=ey+"-"+em+"-"+ed;
            }else{
                Toast.makeText(getContext(),"结束日期填入有误",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        textEmpty.setVisibility(View.GONE);
        if(radioAdult.isChecked()){
            type=0;
            getRecord(name,phone,st,et,types[0]);
        }else if(radioChild.isChecked()){
            type=1;
            getRecord(name,phone,st,et,types[1]);
        }else if(radioWx.isChecked()){
            type=2;
            getRecord(name,phone,st,et,types[2]);
        }else if(radioOne.isChecked()){
            getOneRecord(phone,st,et);
        }
    }

    private void getOneRecord(String p,String st,String et){   //获得单项检测记录，查找本地数据
        st+="-00-00-00";
        et+="-23-59-59";
        int count=layoutCheck.getChildCount();
        List<Integer> indexs=new ArrayList<>();
        int cnt=0;

        for(int i=0;i<count;i++){
            CheckBox cb=(CheckBox) layoutCheck.getChildAt(i);
            if(cb.isChecked()){
                indexs.add(i);
                cnt++;
            }
        }
        if(!isTwoPane){
            LinearLayout layoutCheck2=view.findViewById(R.id.layout_check_2);
            count=layoutCheck2.getChildCount();
            for(int i=0;i<count;i++){
                CheckBox cb=(CheckBox) layoutCheck2.getChildAt(i);
                if(cb.isChecked()){
                    indexs.add(4+i);
                    cnt++;
                }
            }
        }
        List< List<TzOneRecord> >  imageDataList=new ArrayList<>();
        for(int i=0;i<indexs.size();i++){
            int no=indexs.get(i);
            List<TzOneRecord> oRList= DataSupport.where("group = ? and phone = ?",""+no,p).order("time asc").find(TzOneRecord.class);
            List<TzOneRecord> oRList2=new ArrayList<>();
            for(int j=0;j<oRList.size();j++){
                String time=oRList.get(j).getTime();
                if(TimeUtils.before(st,time) && TimeUtils.before(time,et)){
                    oRList2.add(oRList.get(j));
                }
            }
            imageDataList.add(oRList2);
        }
        TzOneAdapter tzOneAdapter =new TzOneAdapter(imageDataList,indexs);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerImage.setLayoutManager(layoutManager);
        recyclerImage.setAdapter(tzOneAdapter);

    }

    private void getRecord(String name,String phone,String st,String et,String type){  //获得除单项外的记录，需访问山之锋服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType",type)
                            .add("name",name)
                            .add("phone",phone)
                            .add("beginTime",st)
                            .add("endTime",et)
                            .add("member_id","88")
                            .add("key_dm","lblc6wcj3ogh0uyhfek53b5z")
                            .add("funcmods_code","tz")
                            .build();
                    Request request=new Request.Builder()
                            .url("http://miaolangzhong.com/erzhentang/saas100Business/bodyIdentify.do")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){   //获得服务器返回内容
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseJSONWithGSON(response);
            }
        });
    }

    private void parseJSONWithGSON(String jsonData){   //解析服务器返回内容
        Gson gson=new Gson();
        TzRecordFirstResult tzRecordFirstResult =gson.fromJson(jsonData, TzRecordFirstResult.class);
        String res= tzRecordFirstResult.getRes();

        if(res.equals("1001")){
            if(type==0){
                TzRecordAdultResult tzResults=gson.fromJson(jsonData, TzRecordAdultResult.class);
                List<TzRecordAdultResult.C.Body> list= tzResults.getRec().getListBodyConclusion();
                setAdapterAdult(list);
                if(list.size()<=0){
                    textEmpty.setVisibility(View.VISIBLE);
                }
            }else if(type==1){
                TzRecordChildResult tzResults=gson.fromJson(jsonData,TzRecordChildResult.class);
                List<TzRecordChildResult.C.Body> list= tzResults.getRec().getListConstConclusionChild();
                setAdapterChild(list);
                if(list.size()<=0){
                    textEmpty.setVisibility(View.VISIBLE);
                }
            }else if(type==2){
                TzRecordWxResult tzResults=gson.fromJson(jsonData,TzRecordWxResult.class);
                List<TzRecordWxResult.C.Body> list= tzResults.getRec().getListBodyConclusionWuxing();
                setAdapterWx(list);
                if(list.size()<=0){
                    textEmpty.setVisibility(View.VISIBLE);
                }
            }
        }else{
            Toast.makeText(getContext(), tzRecordFirstResult.getMsg(),Toast.LENGTH_SHORT).show();
            textEmpty.setVisibility(View.VISIBLE);
            if(myAdapter!=null){
                myList.clear();
                myAdapter.notifyDataSetChanged();
            }
            return;
        }
    }

    private void setAdapterAdult(List<TzRecordAdultResult.C.Body> list){   //设置成人体质记录适配器
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        TzRecordAdapter tzRecordAdapter=new TzRecordAdapter(list);
        recyclerView.setAdapter(tzRecordAdapter);
        myAdapter=tzRecordAdapter;
        myList=list;
    }

    private void setAdapterChild(List<TzRecordChildResult.C.Body> list){  //设置儿童体质记录适配器
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        TzRecordChildAdapter tzRecordAdapter=new TzRecordChildAdapter(list);
        recyclerView.setAdapter(tzRecordAdapter);
        myAdapter=tzRecordAdapter;
        myList=list;
    }

    private void setAdapterWx(List<TzRecordWxResult.C.Body> list){   //设置五型体质记录适配器
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        TzRecordWxAdapter tzRecordAdapter=new TzRecordWxAdapter(list);
        recyclerView.setAdapter(tzRecordAdapter);
        myAdapter=tzRecordAdapter;
        myList=list;
    }

    class TzRecordAdapter extends RecyclerView.Adapter<TzRecordAdapter.ViewHolder> {  //体质成人记录适配器

        private List<TzRecordAdultResult.C.Body> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textNo;
            TextView textName;
            TextView textSex;
            TextView textPhone;
            TextView textDate;
            TextView textTime;

            public ViewHolder(View view){
                super(view);
                textNo=view.findViewById(R.id.record_no);
                textName=view.findViewById(R.id.record_name);
                textSex=view.findViewById(R.id.record_gander);
                textPhone=view.findViewById(R.id.record_phone);
                textDate=view.findViewById(R.id.record_date);
                textTime=view.findViewById(R.id.record_time);
            }
        }

        public TzRecordAdapter(List<TzRecordAdultResult.C.Body> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tz_record_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TzRecordAdultResult.C.Body body =mList.get(holder.getAdapterPosition());
                    int newKey;
                    int [] newNum;
                    if(body.getGender()==0){
                        if(body.getAge()>60){
                            newKey=Ks.TZ_OM_R;
                            newNum=oMLen;
                        }else{
                            newKey=Ks.TZ_YM_R;
                            newNum=yMLen;
                        }
                    }else if(body.getGender()==1){
                        if(body.getAge()>60){
                            newKey=Ks.TZ_OW_R;
                            newNum=oWLen;
                        }else{
                            newKey=Ks.TZ_YW_R;
                            newNum=yWLen;
                        }
                    }else{
                        if(body.getAge()>60){
                            newKey=Ks.TZ_OK_R;
                            newNum=kLen;
                        }else if(body.getAge()>0){
                            newKey=Ks.TZ_YK_R;
                            newNum=kLen;
                        }else{
                            newKey=Ks.TZ_AD_R;
                            newNum=adLen;
                        }
                    }
                    int [] newSum={body.getYangxu(),body.getYinxu(),body.getQixu(),
                            body.getTanshi(),body.getXueyu(),body.getShire(),
                            body.getQiyu(),body.getTebing(),body.getPinghe()};
                    Intent intent=new Intent(getContext(), TzResultActivity.class);
                    Bundle b=new Bundle();
                    b.putIntArray("sum",newSum);
                    b.putIntArray("num",newNum);
                    b.putInt("key",newKey);
                    b.putInt("age",body.getAge());
                    b.putInt("gender",body.getGender());
                    b.putInt("id",body.getId());
                    b.putString("phone",body.getPhone());
                    b.putString("name",body.getName());
                    b.putBoolean("isTwoPane",isTwoPane);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TzRecordAdultResult.C.Body body=mList.get(position);
            holder.textName.setText(body.getName());
            if(body.getGender()==0){
                holder.textSex.setText("男");
            }else if(body.getGender()==1){
                holder.textSex.setText("女");
            }else{
                holder.textSex.setText("通用");
            }
            holder.textPhone.setText(body.getPhone());
            String [] arr=body.getRecord_time().split(" ");
            holder.textDate.setText(arr[0]);
            holder.textTime.setText(arr[1]);
            holder.textNo.setText(""+(position+1));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class TzRecordChildAdapter extends RecyclerView.Adapter<TzRecordChildAdapter.ViewHolder> {   //体质儿童记录适配器

        private List<TzRecordChildResult.C.Body> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textNo;
            TextView textName;
            TextView textSex;
            TextView textPhone;
            TextView textDate;
            TextView textTime;

            public ViewHolder(View view){
                super(view);
                textNo=view.findViewById(R.id.record_no);
                textName=view.findViewById(R.id.record_name);
                textSex=view.findViewById(R.id.record_gander);
                textPhone=view.findViewById(R.id.record_phone);
                textDate=view.findViewById(R.id.record_date);
                textTime=view.findViewById(R.id.record_time);
            }
        }

        public TzRecordChildAdapter(List<TzRecordChildResult.C.Body> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tz_record_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TzRecordChildResult.C.Body body =mList.get(holder.getAdapterPosition());
                    int [] newSum={body.getPixu(),body.getJizhi(),body.getShizhi(),
                            body.getXinhuo(),body.getRezhi(),body.getYibing(),
                            body.getShengji()};
                    Intent intent=new Intent(getContext(), TzResultActivity.class);
                    Bundle b=new Bundle();
                    b.putIntArray("sum",newSum);
                    b.putIntArray("num",chLen);
                    b.putInt("key",Ks.TZ_CH_R);
                    b.putInt("age",body.getAge());
                    b.putInt("gender",body.getGender());
                    b.putString("phone",body.getPhone());
                    b.putString("name",body.getName());
                    b.putInt("id",body.getId());
                    b.putBoolean("isTwoPane",isTwoPane);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TzRecordChildResult.C.Body body=mList.get(position);
            holder.textName.setText(body.getName());
            if(body.getGender()==0){
                holder.textSex.setText("男");
            }else if(body.getGender()==1){
                holder.textSex.setText("女");
            }else{
                holder.textSex.setText("通用");
            }

            holder.textPhone.setText(body.getPhone());
            String [] arr=body.getRecord_time().split(" ");
            holder.textDate.setText(arr[0]);
            holder.textTime.setText(arr[1]);
            holder.textNo.setText(""+(position+1));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class TzRecordWxAdapter extends RecyclerView.Adapter<TzRecordWxAdapter.ViewHolder> {   //体质五型记录适配器

        private List<TzRecordWxResult.C.Body> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textNo;
            TextView textName;
            TextView textSex;
            TextView textPhone;
            TextView textDate;
            TextView textTime;

            public ViewHolder(View view){
                super(view);
                textNo=view.findViewById(R.id.record_no);
                textName=view.findViewById(R.id.record_name);
                textSex=view.findViewById(R.id.record_gander);
                textPhone=view.findViewById(R.id.record_phone);
                textDate=view.findViewById(R.id.record_date);
                textTime=view.findViewById(R.id.record_time);
            }
        }

        public TzRecordWxAdapter(List<TzRecordWxResult.C.Body> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tz_record_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TzRecordWxResult.C.Body body =mList.get(holder.getAdapterPosition());
                    int [] newSum={body.getJin(),body.getMu(),body.getShui(),
                            body.getHuo(),body.getTu()};
                    Intent intent=new Intent(getContext(), TzResultActivity.class);
                    Bundle b=new Bundle();
                    b.putIntArray("sum",newSum);
                    b.putIntArray("num",wxLen);
                    b.putInt("key", Ks.TZ_WX_R);
                    b.putInt("age",body.getAge());
                    b.putInt("gender",body.getGender());
                    b.putString("phone",body.getPhone());
                    b.putString("name",body.getName());
                    b.putInt("id",body.getId());
                    b.putBoolean("isTwoPane",isTwoPane);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TzRecordWxResult.C.Body body=mList.get(position);
            holder.textName.setText(body.getName());
            if(body.getGender()==0){
                holder.textSex.setText("男");
            }else if(body.getGender()==1){
                holder.textSex.setText("女");
            }else{
                holder.textSex.setText("通用");
            }

            holder.textPhone.setText(body.getPhone());
            String [] arr=body.getRecord_time().split(" ");
            holder.textDate.setText(arr[0]);
            holder.textTime.setText(arr[1]);
            holder.textNo.setText(""+(position+1));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

}
