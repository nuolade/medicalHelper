package com.example.wenda01.views.Card;

import com.example.wenda01.beans.ShowItem;

import java.util.List;

public class CardItem {

    private String mTextResource;
    private String mTitleResource;
    private List<ShowItem> list;
    private int imageId;

    public CardItem(String title, List<ShowItem> items,int imageId) {
        mTitleResource = title;
        list=items;
        this.imageId=imageId;
//        mTextResource = text;
    }
//    public CardItem(int title, int text) {
//        mTitleResource = title;
//        mTextResource = text;
//    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public List<ShowItem> getList() {
        return list;
    }

    public int getImageId() {
        return imageId;
    }
}