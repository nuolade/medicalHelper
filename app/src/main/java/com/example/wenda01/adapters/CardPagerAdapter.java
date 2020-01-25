package com.example.wenda01.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenda01.MyApplication;
import com.example.wenda01.R;
import com.example.wenda01.beans.ShowItem;
import com.example.wenda01.fragments.base.FabFragment;
import com.example.wenda01.views.Card.CardItem;
import com.example.wenda01.utils.TTSUtility;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private FabFragment mFabFragment;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public  CardPagerAdapter(FabFragment fabFragment) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        mFabFragment=fabFragment;
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.jq_card_adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        RecyclerView recyclerView=view.findViewById(R.id.s_recycler);
        TextView textView=view.findViewById(R.id.s_text);
        ImageView imageView=view.findViewById(R.id.card_image);
        imageView.setImageResource(item.getImageId());
        textView.setText(item.getTitle());
        List<ShowItem> list=item.getList();
        LinearLayoutManager layoutManager=new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ShowItemAdapter showItemAdapter=new ShowItemAdapter(list);
        recyclerView.setAdapter(showItemAdapter);
//        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
//        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
//        titleTextView.setText(item.getTitle());
//        contentTextView.setText(item.getText());
    }

    class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ViewHolder> {

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
                        String s=contentText.getText().toString().trim();
                        if(MyApplication.isIsSoundOpen()){
                            TTSUtility.getInstance(MyApplication.getContext()).speaking(s,false);
                            if(mFabFragment!=null){
                                mFabFragment.setReportString(s);
                            }
                        }
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