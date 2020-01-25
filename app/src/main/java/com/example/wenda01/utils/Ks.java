package com.example.wenda01.utils;

public class Ks {   //为关键key值，（现存储了体质模块、知识问答的key）
    //为体质模块向TzResultActivity传递的key，用于说明是什么模块的体质记录，w表示将写入服务器的记录，r表示从服务器读取的记录
    public static final int TZ_OM_W=1;  //老年男性
    public static final int TZ_OW_W=2;  //老年女性
    public static final int TZ_YM_W=3;  //年轻男性
    public static final int TZ_YW_W=4;  //年轻女性
    public static final int TZ_CH_W=5;  //儿童
    public static final int TZ_WX_W=6;  //五型
    public static final int TZ_OK_W=7;  //老年快捷
    public static final int TZ_YK_W=8;  //年轻快捷
    public static final int TZ_AD_W=9;  //成人常规
    public static final int TZ_OM_R=-1; //老年男性
    public static final int TZ_OW_R=-2; //老年女性
    public static final int TZ_YM_R=-3; //年轻男性
    public static final int TZ_YW_R=-4; //年轻女性
    public static final int TZ_CH_R=-5; //儿童
    public static final int TZ_WX_R=-6; //五型
    public static final int TZ_OK_R=-7; //老年快捷
    public static final int TZ_YK_R=-8; //年轻快捷
    public static final int TZ_AD_R=-9; //成人常规
    public static final int STARTKEY=1; //开始
    public static final int WORDKEY=2;   //语音文字处理
    public static final int SAYKEY=3;  //语音输入
    public static final int HISTORYKEY=4;   //语音输入
    public static final int PHONEKEY=5; //手机号保存

    //为知识问答模块的key
    public static final int QA_RECEIVED=0;  //标记消息为接收
    public static final int QA_SENT=1;  //标记消息为发送
    public static final int QA_OPENSESSION=1;   //操作会话
    public static final int QA_MSG_DEL=1;   //消息删除
    public static final int QA_MSG_UNDEL=0; //消息取消删除
}
