package com.example.wenda01.fragments.jq;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenda01.R;
import com.example.wenda01.adapters.CardPagerAdapter;
import com.example.wenda01.adapters.ImageAdapter;
import com.example.wenda01.beans.jq.JqOneResult;
import com.example.wenda01.beans.ShowItem;
import com.example.wenda01.fragments.base.JqFabFragment;
import com.example.wenda01.views.Card.CardItem;
import com.example.wenda01.utils.ShadowTransformer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JqContentFragment extends JqFabFragment implements View.OnClickListener {   //进行病症记录的搜索

    private TextView textTitle;
    private RecyclerView recyclerView;

    private int id; //节气id
    private int showType=0; //展示模式

    private String name; //名称
    private String jieshao; //介绍
    private String qihou; //气候
    private String shenghuo; //生活
    private String jingshen; //精神
    private String fangbing; //防病
    private String yinshi; //饮食
    private String yaoshan; //药膳
    private String [] arr={"介绍","气候","生活","精神","防病","饮食","药膳"};
    private String [] arrTitle={"简介","养生","食疗"};
    private List<ShowItem> list; //列表模式显示内容
    private List<ShowItem> list1; //卡片模式简介显示内容
    private List<ShowItem> list2; //卡片模式养生显示内容
    private List<ShowItem> list3; //卡片模式食疗显示内容
    private CardPagerAdapter mCardAdapter;

    private ViewPager mViewPager;
    private ShowItemAdapter showItemAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private LinearLayout layoutList;
    private LinearLayout layoutCard;
    private Button buttonChange;

    private RecyclerView recyclerImage;
    private List<Integer> images;
    private ImageAdapter imageAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.jq_content_frag,container,false);
        preWork();
        setSDFab(view,this);
        setInitData();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (onClickFab(v) ){
            return;
        }
        switch (v.getId()){
            case R.id.button_change:
                changeShowLayout();
                break;
        }
    }

    private void changeShowLayout(){  //更改节气信息的展示模式（卡片、列表）
        showType=(showType+1)%2;
        if(layoutCard.getVisibility()==View.VISIBLE){
            layoutList.setVisibility(View.VISIBLE);
            layoutCard.setVisibility(View.GONE);
            buttonChange.setText("卡片模式");
        }else{
            layoutList.setVisibility(View.GONE);
            layoutCard.setVisibility(View.VISIBLE);
            buttonChange.setText("列表模式");
        }
    }

    private void preWork(){
        layoutList=view.findViewById(R.id.jq_content_list);
        layoutCard=view.findViewById(R.id.jq_content_card);
        buttonChange=view.findViewById(R.id.button_change);
        textTitle=view.findViewById(R.id.s_title);
        recyclerView=view.findViewById(R.id.s_recycler);
        recyclerImage=view.findViewById(R.id.recycler_image);
        buttonChange.setText("列表模式");

        textTitle.setText("节气");
        list=new ArrayList<>();
        list1=new ArrayList<>();
        list2=new ArrayList<>();
        list3=new ArrayList<>();
        images=new ArrayList<>();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        showItemAdapter=new ShowItemAdapter(list);
        recyclerView.setAdapter(showItemAdapter);

        LinearLayoutManager imageManager=new LinearLayoutManager(getActivity());
        imageManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerImage.setLayoutManager(imageManager);
        imageAdapter=new ImageAdapter(images);
        recyclerImage.setAdapter(imageAdapter);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapter(this);
        layoutList.setVisibility(View.GONE);
        layoutCard.setVisibility(View.VISIBLE);
        buttonChange.setOnClickListener(this);
        if(view.findViewById(R.id.jq_wheel)!=null){
            isTwoPane=true;
        }else {
            isTwoPane=false;
        }
    }

    private void setInitData(){  //获得活动传递的初始参数
        id=getArguments().getInt("id");
        isTwoPane=getArguments().getBoolean("isTwoPane");
        sendReq4JqAll();
    }


    private void sendReq4JqAll(){  //访问服务器获得该节气的信息介绍

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("requestType","2")
                            .add("id",""+id)
                            .add("member_id","88")
                            .add("key_tz","lblc6wcj3ogh0uyhfek53b5z")
                            .build();

                    Request request=new Request.Builder()
                            .url("http://miaolangzhong.com/erzhentang/saas100Business/Food24.do")
                            .post(requestBody)
                            .build();

                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    show(responseData);
//                    Toast.makeText(getContext(),responseData,Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void show(String s){   // 获得服务器返回结果
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseJSONWithGSON(s);
            }
        });

    }

    private void parseJSONWithGSON(String jsonData){   //解析服务器返回的节气信息
        Gson gson=new Gson();
        JqOneResult jqOneResult=gson.fromJson(jsonData, JqOneResult.class);
//        Toast.makeText(getContext(),""+xyResult.getRec().getListF24().size(),Toast.LENGTH_SHORT).show();
        name=jqOneResult.getRec().getName();
        jieshao =jqOneResult.getRec().getJieshao();
        qihou   =jqOneResult.getRec().getQihou();
        shenghuo=jqOneResult.getRec().getShenghuo();
        jingshen=jqOneResult.getRec().getJingshen();
        fangbing=jqOneResult.getRec().getFangbing();
        yinshi  =jqOneResult.getRec().getYinshi();
        yaoshan =jqOneResult.getRec().getYaoshan();
        textTitle.setText(name);
        ShowItem jS     =    new ShowItem(arr[0],jieshao );
        ShowItem qH     =    new ShowItem(arr[1],qihou   );
        ShowItem sH     =   new ShowItem(arr[2],shenghuo);
        ShowItem jShen  = new ShowItem(arr[3],jingshen);
        ShowItem fB     =    new ShowItem(arr[4],fangbing);
        ShowItem yS     =    new ShowItem(arr[5],yinshi  );
        ShowItem yShan  = new ShowItem(arr[6],yaoshan );
        list.add(jS   );
        list.add(qH   );
        list.add(sH   );
        list.add(jShen);
        list.add(fB   );
        list.add(yS   );
        list.add(yShan);

        list1.add(jS   );
        list1.add(qH   );
        list2.add(sH   );
        list2.add(jShen);
        list2.add(fB   );
        list3.add(yS   );
        list3.add(yShan);
        setCard();
        showItemAdapter.notifyDataSetChanged();
        setImageAdapter();
//        Toast.makeText(getContext(),jieshao,Toast.LENGTH_SHORT).show();
    }

    private void setImageAdapter(){   //设置节气图片的适配器
        String prex="";
        if(id<10){
            prex+="0";
        }
        prex+=id;
        images.add(getResources().getIdentifier("jq"+prex+1, "drawable", getContext().getPackageName()));
        images.add(getResources().getIdentifier("jq"+prex+2, "drawable", getContext().getPackageName()));
        images.add(getResources().getIdentifier("jq"+prex+3, "drawable", getContext().getPackageName()));
        imageAdapter.notifyDataSetChanged();
    }

    private void setCard(){   //设置卡片模式下的相关控件
        String prex="";
        if(id<10){
            prex+="0";
        }
        prex+=id;
        mCardAdapter.addCardItem(new CardItem(arrTitle[0],list1,getResources().getIdentifier("jq"+prex+1, "drawable", getContext().getPackageName())));
        mCardAdapter.addCardItem(new CardItem(arrTitle[1],list2,getResources().getIdentifier("jq"+prex+2, "drawable", getContext().getPackageName())));
        mCardAdapter.addCardItem(new CardItem(arrTitle[2],list3,getResources().getIdentifier("jq"+prex+3, "drawable", getContext().getPackageName())));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);


        mViewPager.setAdapter(mCardAdapter);
        if(isTwoPane){
            mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
        }else{
            mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_small));
        }

        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }


    class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ViewHolder> {   //节气详细信息的适配器

        private List<ShowItem> mList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView titleText;
            TextView contentText;
            public ViewHolder(View view){
                super(view);
                titleText=view.findViewById(R.id.item_name);
                contentText=view.findViewById(R.id.item_content);
                titleText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(contentText.getVisibility()==View.VISIBLE){
                            contentText.setVisibility(View.GONE);

                        }else{
                            contentText.setVisibility(View.VISIBLE);
                        }
                    }


                });
                contentText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reportString=contentText.getText().toString().trim();
                        doFabReport(reportString,false,true);
                    }
                });
            }
        }

        public ShowItemAdapter(List<ShowItem> list){
            mList=list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.jq_show_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ShowItem showItem=mList.get(position);
            holder.titleText.setText(showItem.getName());
            holder.contentText.setText(showItem.getContent());

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
