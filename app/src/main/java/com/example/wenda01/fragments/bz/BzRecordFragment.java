package com.example.wenda01.fragments.bz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.adapters.BzRecordAdapter;
import com.example.wenda01.beans.bz.BzRecord;
import com.example.wenda01.fragments.base.BzFabFragment;
import com.example.wenda01.utils.AccountKeeper;
import com.example.wenda01.utils.DialogUtils;
import com.example.wenda01.utils.RegularUtils;
import com.example.wenda01.utils.TimeUtils;
import com.example.wenda01.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BzRecordFragment extends BzFabFragment implements View.OnClickListener {   //病症检测记录碎片

    private EditText editPhone;
    private EditText editSy;
    private EditText editSm;
    private EditText editSd;
    private EditText editEy;
    private EditText editEm;
    private EditText editEd;
    private CheckBox checkDel;
    private TextView textEmpty;

    private Button buttonSearch;
    private Button buttonClear;
    private List<BzRecord> bzRecords;
    private BzRecordAdapter bzRecordAdapter;
    private ListView bzList;
    private LinearLayout layoutCon;
    private ImageView imageShow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.bz_record_frag,container,false);
        showIntor();
        preWork();
        return view;
    }

    private void showIntor(){
        DialogUtils.showIntorDialog(getActivity(),getResources().getString(R.string.text_bz_record_intor));
    }

    private void preWork(){
        editPhone=view.findViewById(R.id.tz_edit_phone);
        editSy=view.findViewById(R.id.edit_s_y);
        editSm=view.findViewById(R.id.edit_s_m);
        editSd=view.findViewById(R.id.edit_s_d);
        editEy=view.findViewById(R.id.edit_e_y);
        editEm=view.findViewById(R.id.edit_e_m);
        editEd=view.findViewById(R.id.edit_e_d);
        checkDel=view.findViewById(R.id.check_del);
        buttonSearch=view.findViewById(R.id.button_search);
        buttonClear=view.findViewById(R.id.button_clear);
        bzList=view.findViewById(R.id.list_bz_record);
        textEmpty=view.findViewById(R.id.text_res_empty);
        imageShow=view.findViewById(R.id.image_show);
        layoutCon=view.findViewById(R.id.layout_condition);

        checkDel.setChecked(MyApplication.isBzRDel());
        checkDel.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        imageShow.setOnClickListener(this);
        layoutCon.setVisibility(View.VISIBLE);
        textEmpty.setVisibility(View.GONE);

        bzRecords=new ArrayList<>();
        bzRecordAdapter =new BzRecordAdapter(getContext(),R.layout.bz_r_item,bzRecords);
        bzList.setAdapter(bzRecordAdapter);

        if(AccountKeeper.isOnline()){
            editPhone.setText(AccountKeeper.getLastPhone());
        }
        setDate();

    }

    private void setDate(){  //设置时间搜索框的初始值
        //使用默认时区和语言环境获得一个日历。
        Calendar rightNow    =    Calendar.getInstance();
		/*用Calendar的get(int field)方法返回给定日历字段的值。
		HOUR 用于 12 小时制时钟 (0 - 11)，HOUR_OF_DAY 用于 24 小时制时钟。*/
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1; //第一个月从0开始，所以得到月份＋1
        Integer day = rightNow.get(rightNow.DAY_OF_MONTH);
        editSy.setText(""+year);
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
            case R.id.check_del:
                doCheckDel();
                break;
            case R.id.button_clear:
                doClear();
                break;
            case R.id.image_show:
                doChangeShow();
                break;
        }
    }

    private void doChangeShow(){   //更改搜索条件的可见性
        if(layoutCon.getVisibility()==View.GONE){
            layoutCon.setVisibility(View.VISIBLE);
            imageShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.up));
        }else{
            layoutCon.setVisibility(View.GONE);
            imageShow.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.down));
        }
    }

    private void doClear(){   //清空过滤条件
        editPhone.setText("");
        editSy.setText("");
        editSm.setText("");
        editSd.setText("");
        editEy.setText("");
        editEm.setText("");
        editEd.setText("");
    }

    private void doCheckDel(){   //更新是否显示删除提示
        boolean isCheck=checkDel.isChecked();
        MyApplication.setBzRDel(isCheck);
    }

    private void doSearch(){   //进行搜索病症条件的预处理
        String phone=editPhone.getText().toString().trim();
        if(phone.equals("")){
            ToastUtils.showSWihtSound("手机不可为空");
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
                ToastUtils.showSWihtSound("开始日期填入有误");
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
                ToastUtils.showSWihtSound("结束日期填入有误");
                return;
            }
        }
        textEmpty.setVisibility(View.GONE);
        getRecord(phone,st,et);
    }

    private void getRecord(String phone,String st,String et){   //进行病症记录的搜索
        List<BzRecord> list= DataSupport.where("phone = ?",phone).order("time desc").find(BzRecord.class);
        bzRecords.clear();
        for(int i=0;i<list.size();i++){
            String time=list.get(i).getTime();
            if(TimeUtils.before(st,time) && TimeUtils.before(time,et)){
                bzRecords.add(list.get(i));
            }
        }
        bzRecordAdapter.notifyDataSetChanged();
        if(bzRecords.size()<=0){
            textEmpty.setVisibility(View.VISIBLE);
        }
    }
}
