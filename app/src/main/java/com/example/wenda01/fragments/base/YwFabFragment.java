package com.example.wenda01.fragments.base;

import android.os.Handler;
import android.os.Message;

import com.example.wenda01.beans.yw.MedicineIndex;
import com.example.wenda01.utils.Ks;

import java.io.Serializable;

public class YwFabFragment extends FabFragment implements Serializable { //具有辅助功能的药物父类碎片
    protected Handler handlerYw=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Ks.STARTKEY:
                    openYwIntor((MedicineIndex)msg.obj);
                    break;
            }
        }
    };

    protected void openYwIntor(MedicineIndex medicineIndex){  //打开药物详细信息
    }

}
