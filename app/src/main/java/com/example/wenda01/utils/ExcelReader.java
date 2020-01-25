package com.example.wenda01.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.wenda01.beans.xj.XjCkKnowledge;
import com.example.wenda01.beans.yw.MedicineEffect;
import com.example.wenda01.beans.yw.MedicineIndex;
import com.example.wenda01.beans.yw.MedicineInteraction;
import com.example.wenda01.beans.bz.BzQuestion;
import com.example.wenda01.beans.bz.BzSymptom;
import com.example.wenda01.beans.tz.TzAdultKeyQuestion;
import com.example.wenda01.beans.tz.TzChildQuestion;
import com.example.wenda01.beans.tz.TzAdultQuestion;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelReader {  //excel读取工具

    public static void readExcelXjCk(Context context,String filename) {   //读取宣教孕产数据
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String title = (sheet.getCell(1, i)).getContents();
                String content = (sheet.getCell(2, i)).getContents();
                String label = (sheet.getCell(3, i)).getContents();
                String type = (sheet.getCell(4, i)).getContents();
                XjCkKnowledge xjCkKnowledge =new XjCkKnowledge(title,content,label,type);
                xjCkKnowledge.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelTzChild(Context context,String filename) {   //读取儿童体质问题
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 2; i < Rows; ++i) {
                String idd = (sheet.getCell(0, i)).getContents();
                String q = (sheet.getCell(1, i)).getContents();
                String order=(sheet.getCell(2, i)).getContents();
                String type = (sheet.getCell(3, i)).getContents();
                String index1=(sheet.getCell(4,i)).getContents();
                String index2=(sheet.getCell(5,i)).getContents();
                String index3=(sheet.getCell(6,i)).getContents();
                String index4=(sheet.getCell(7,i)).getContents();
                String index5=(sheet.getCell(8,i)).getContents();
                String index6=(sheet.getCell(8,i)).getContents();
                String index7=(sheet.getCell(10,i)).getContents();
                int i1=Integer.valueOf(index1);
                int i2=Integer.valueOf(index2);
                int i3=Integer.valueOf(index3);
                int i4=Integer.valueOf(index4);
                int i5=Integer.valueOf(index5);
                int i6=Integer.valueOf(index6);
                int i7=Integer.valueOf(index7);
                int id=Integer.valueOf(idd);
                int orderId=Integer.valueOf(order);
                TzChildQuestion tzChildQuestion=new TzChildQuestion(id,q,orderId,i1,i2,i3,i4,i5,i6,i7,type);
                tzChildQuestion.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelTzAdult(Context context,String filename) {   //读取成人常规体质问题
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String idd = (sheet.getCell(0, i)).getContents();
                String q = (sheet.getCell(1, i)).getContents();
                String index1=(sheet.getCell(2,i)).getContents();
                String index2=(sheet.getCell(3,i)).getContents();
                String index3=(sheet.getCell(4,i)).getContents();
                String index4=(sheet.getCell(5,i)).getContents();
                String index5=(sheet.getCell(6,i)).getContents();
                String index6=(sheet.getCell(7,i)).getContents();
                String index7=(sheet.getCell(8,i)).getContents();
                String index8=(sheet.getCell(9,i)).getContents();
                String index9=(sheet.getCell(10,i)).getContents();
                String buttonContent=(sheet.getCell(11,i)).getContents();
                int i1=Integer.valueOf(index1);
                int i2=Integer.valueOf(index2);
                int i3=Integer.valueOf(index3);
                int i4=Integer.valueOf(index4);
                int i5=Integer.valueOf(index5);
                int i6=Integer.valueOf(index6);
                int i7=Integer.valueOf(index7);
                int i8=Integer.valueOf(index8);
                int i9=Integer.valueOf(index9);
                int id=Integer.valueOf(idd);
                TzAdultQuestion tzAdultQuestion =new TzAdultQuestion(q,id,i1,i2,i3,i4,i5,i6,i7,i8,i9,buttonContent);
                tzAdultQuestion.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelTzKey(Context context,String filename) {   //读取成人快捷体质问题
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 2; i < Rows; ++i) {
                String idd = (sheet.getCell(0, i)).getContents();
                String q = (sheet.getCell(1, i)).getContents();
                String index1=(sheet.getCell(2,i)).getContents();
                String index2=(sheet.getCell(3,i)).getContents();
                String index3=(sheet.getCell(4,i)).getContents();
                String index4=(sheet.getCell(5,i)).getContents();
                String index5=(sheet.getCell(6,i)).getContents();
                String index6=(sheet.getCell(7,i)).getContents();
                String index7=(sheet.getCell(8,i)).getContents();
                String index8=(sheet.getCell(9,i)).getContents();
                String index9=(sheet.getCell(10,i)).getContents();
                int i1=Integer.valueOf(index1);
                int i2=Integer.valueOf(index2);
                int i3=Integer.valueOf(index3);
                int i4=Integer.valueOf(index4);
                int i5=Integer.valueOf(index5);
                int i6=Integer.valueOf(index6);
                int i7=Integer.valueOf(index7);
                int i8=Integer.valueOf(index8);
                int i9=Integer.valueOf(index9);
                int id=Integer.valueOf(idd);
                TzAdultKeyQuestion tzAdultKeyQuestion=new TzAdultKeyQuestion(q,id,i1,i2,i3,i4,i5,i6,i7,i8,i9);
                tzAdultKeyQuestion.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelBzSym(Context context,String filename) {  //读取病症症状
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String name=(sheet.getCell(1, i)).getContents();
                String idd = (sheet.getCell(0, i)).getContents();
                String maxL= (sheet.getCell(2, i)).getContents();
                String intor= (sheet.getCell(3, i)).getContents();
                int id1=Integer.valueOf(idd);
                int id2=Integer.valueOf(maxL);
                BzSymptom bzSymptom =new BzSymptom(name,id1,id2,intor);
                bzSymptom.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelMIndex(Context context,String filename) {   //读取药物介绍信息（索引）
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String name = (sheet.getCell(0, i)).getContents();
                String c= (sheet.getCell(1, i)).getContents();
                String o1= (sheet.getCell(2, i)).getContents();
                String o2= (sheet.getCell(3, i)).getContents();
                String cf= (sheet.getCell(4, i)).getContents();
                String mx=(sheet.getCell(5, i)).getContents();
                MedicineIndex medicineIndex=new MedicineIndex(name,c,o1,o2,cf,mx,i);
                medicineIndex.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelBzQues(Context context,String filename) {   //读取病症问题
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String idd = (sheet.getCell(3, i)).getContents();
                String idYes= (sheet.getCell(4, i)).getContents();
                String idNo= (sheet.getCell(5, i)).getContents();
                String isEnd= (sheet.getCell(6, i)).getContents();
                String text= (sheet.getCell(7, i)).getContents();
                int id1=Integer.valueOf(idd);
                int id2=Integer.valueOf(idYes);
                int id3=Integer.valueOf(idNo);
                int id4=Integer.valueOf(isEnd);
                BzQuestion bzQuestion =new BzQuestion(text,id1,id2,id3,id4);
                bzQuestion.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelMEffect(Context context,String filename) {   //读取药物功效
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String name = (sheet.getCell(0, i)).getContents();
                String effect= (sheet.getCell(1, i)).getContents();
                MedicineEffect medicineEffect=new MedicineEffect(name,effect);
                medicineEffect.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

    public static void readExcelMInter(Context context,String filename) {   //读取药物相互作用
        AssetManager assetManager = context.getAssets();
        try {
            Workbook book = Workbook.getWorkbook(assetManager.open(filename));
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            for (int i = 1; i < Rows; ++i) {
                String name = (sheet.getCell(0, i)).getContents();
                String n2= (sheet.getCell(1, i)).getContents();
                String res= (sheet.getCell(2, i)).getContents();
                String act= (sheet.getCell(3, i)).getContents();
                MedicineInteraction medicineInteraction=new MedicineInteraction(name,n2,res,act);
                medicineInteraction.save();
            }
            book.close();
        } catch (Exception e) {
            Log.e("yy",  "e"+e);
        }
    }

}
