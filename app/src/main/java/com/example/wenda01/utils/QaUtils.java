package com.example.wenda01.utils;

import com.example.wenda01.beans.qa.QaMsg;

public class QaUtils {   //知识问答模块，回答工具

    public static QaMsg getAnswer(QaMsg msg){   //产生问题对应的测试答案
        QaMsg qaMsg=new QaMsg(TimeUtils.getTime(),msg.getText(),msg.getPhone(),msg.getSession(),Ks.QA_RECEIVED,msg.getNo());
        qaMsg.save();
        return qaMsg;
    }
}
