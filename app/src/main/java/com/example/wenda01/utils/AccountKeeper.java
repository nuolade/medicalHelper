package com.example.wenda01.utils;

import com.example.wenda01.beans.dj.Account;

public class AccountKeeper {   //用来管理应用登录账号
    private static Account account;  //保存账号
    private static String phone="";  //保存账号


    public static boolean isOnline(){   //判断是否有账号登录
        return account!=null;
    }
    public static void login(Account a){   //账号登录
        account=a;
        phone=a.getPhone();
    }
    public static void exit(){   //账号退出
        account=null;
    }
    public static String getLastPhone(){  //得到最后登录账号的手机号
        return phone;
    }
    public static String getHiddenPhone(){   //得到最后登录账号的隐藏手机号
        if(phone.equals("")){
            return phone;
        }
        return phone.substring(0,3)+"*****"+phone.substring(8,11);
    }
    public static int getAge(){   //得到账号的年龄
        return account.getAge();
    }

    public static Account getAccount() {  //得到账号
        return account;
    }

    public static void setAccount(Account account) {   //设置账号
        AccountKeeper.account = account;
    }
}
