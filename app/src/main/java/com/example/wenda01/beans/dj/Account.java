package com.example.wenda01.beans.dj;

import org.litepal.crud.DataSupport;

public class Account extends DataSupport {  //为本地存储的账号类
    private String name;        //姓名
    private String phone;       //电话
    private String password;        //密码
    private int age;        //年龄
    private boolean isMale;     //是否男性
    private int year;       //年
    private int month;      //年
    private int day;        //日
    private int height;     //日
    private int weight;     //体重

    public Account(String phone, String password, boolean isMale, int year, int month, int day) {
        this.phone = phone;
        this.password = password;
        this.isMale = isMale;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Account() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
