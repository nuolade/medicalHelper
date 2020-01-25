package com.example.wenda01.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.wenda01.beans.tz.TzWxOlQuesResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelWriter {
    private static WritableFont arial14font = null;
    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */

    private static String [] arr={"d://oM.xls","d://oW.xls","d://yM.xls","d://yW.xls","d://child.xls"};
    private static String [] arr2={"8.1","8.2","8.3","8.4"};
    private static int i;


    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);
            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param fileName 导出excel存放的地址（目录）
     * @param colName excel中包含的列名（可以有多个）
     */

    public static void initExcel(String fileName,String sheetName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
//                    ProjectBean projectBean = (ProjectBean) objList.get(j);
//                    List<String> list = new ArrayList<>();
//                    list.add(projectBean.getName());
//                    list.add(projectBean.getProject());
//                    list.add(projectBean.getYear() );
//                    for (int i = 0; i < list.size(); i++) {
//                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
//                        if (list.get(i).length() <= 4) {
//                            //设置列宽
//                            sheet.setColumnView(i, list.get(i).length() + 8);
//                        } else {
//                            //设置列宽
//                            sheet.setColumnView(i, list.get(i).length() + 5);
//                        }
//                    }
//
//                    //设置行高
//                    sheet.setRowView(j + 1, 350);
                }
                writebook.write();
//                Toast.makeText(c, "导出Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**

     * 读取EXCEL内容

     * @param filePath

     * @return

     * @throws Exception

     */

    public static List getXlsData(String filePath) throws Exception {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> mapResult = new HashMap<>();//根据具体的生成对应的对象文件
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        // 创建输入流
        InputStream stream = new FileInputStream(filePath);
        // 获取Excel文件对象
        Workbook rwb = Workbook.getWorkbook(stream);
        // 获取文件的指定工作表 默认的第一个
        rwb.getSheets();
        Sheet sheet = rwb.getSheet(0);
        // 行数(表头的目录不需要，从1开始)
        for (int i = 1; i < sheet.getRows(); i++) {
            // 列数
            for (int j = 0; j < sheet.getColumns(); j++) {
                // 获取第i行，第j列的值
                Cell keyCell = sheet.getCell(j, 0);
                // 获取第i行，第j列的值
                Cell valueCell = sheet.getCell(j, i);
                mapResult.put(keyCell.getContents(),valueCell.getContents());
            }
            // 把刚获取的列存入list，也可以存入到数据库
            list.add(mapResult);
        }
        return list;
    }


    private static WritableWorkbook wwb;



    /**向Execl写入数据
     * @Param ls List<map>数据
     * @Param emeailPath
     * @Param file
     */
    public void writeToExcel(List<Map<String,Object>> ls, String emeailPath, File file) {

        try {
            Workbook oldWwb = Workbook.getWorkbook(file);
            wwb = Workbook.createWorkbook(file, oldWwb);
            WritableSheet ws = wwb.getSheet(0);
            // 当前行数
            for (int i = 0; i < ls.size(); i++) {
                int row = ws.getRows();
                Label lab1 = new Label(0, row, ls.get(i).get("数据1") + "");
                Label lab2 = new Label(1, row, ls.get(i).get("数据2") + "");
                Label lab3 = new Label(2, row, ls.get(i).get("数据3") + "");
                Label lab4 = new Label(3, row, ls.get(i).get("数据4") + "");
                ws.addCell(lab1);
                ws.addCell(lab2);
                ws.addCell(lab3);
                ws.addCell(lab4);
            }
            // 从内存中写入文件中,只能刷一次
            wwb.write();
            wwb.close();
            if (emeailPath != null) {
//                postEmail(emeailPath);
            }else{
//                final ProgressDialog precentDialog=new ProgressDialog(mContext);
//                precentDialog.setMessage("导出U盘中...");
//                precentDialog.setMax(100);
//                precentDialog.setCanceledOnTouchOutside(false);
//                precentDialog.show();
//                new Thread(){
//                    public void run() {
//                        //等待进度条
//                        for (int i = 0; i < 100; i++) {
//                            try {
//                                long l= (long) (Math.random()*200);
//                                Thread.sleep(l);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            precentDialog.setProgress(i);
//                        }
//                        precentDialog.dismiss();
//                        handler.sendEmptyMessage(1);
//                    };
//                }.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
//            Toast.makeText(mContext,"导入U盘完成！", Toast.LENGTH_SHORT).show();
        }
    };

    //删除文件夹
    private static void deleteExcel(File file){
        if(file.exists()){
            file.delete();
        }
    }

    // 创建excel表
    public static void createExcelTzWx(File file, List<TzWxOlQuesResult.C.Wxtz> list, int sheetId) {
        deleteExcel(file);
        WritableSheet ws = null;
        try {
            if (!file.exists()) {
                wwb = Workbook.createWorkbook(file);//创建表
                ws = wwb.createSheet("sheet1", sheetId);//表名 页数

                for(int i=0;i<list.size();i++){
                    TzWxOlQuesResult.C.Wxtz tzResult=list.get(i);
                    String age_group  =""+tzResult.getAge_group();
                    String backward   =""+tzResult.getBackward();
                    String body_id    =""+tzResult.getBody_id();
                    String class_id       =""+tzResult.getClass_id();
                    String gender       =""+tzResult.getGender();
                    String id        =""+tzResult.getId();
                    String question  =tzResult.getQuestion();
                    String repeat    =""+tzResult.getRepeat();
                    String sort      =""+tzResult.getSort();

                    String opt1      =tzResult.getAnswer().getOpt1();
                    String opt2      =tzResult.getAnswer().getOpt2();


                    // 在指定单元格插入数据
                    Label lbl1 = new Label(0, i, age_group);
                    Label lbl2 = new Label(1, i, backward );
                    Label lbl3 = new Label(2, i, body_id  );
                    Label lbl4 = new Label(3, i, class_id );
                    Label lbl5 = new Label(4, i, gender   );
                    Label lbl6 = new Label(5, i, id       );
                    Label lbl7 = new Label(6, i, question );
                    Label lbl8 = new Label(7, i, repeat   );
                    Label lbl9 = new Label(8, i, sort     );
                    Label lbl10=new Label(9, i , opt1     );
                    Label lbl11=new Label(10, i, opt2     );

                    ws.addCell(lbl1 );
                    ws.addCell(lbl2 );
                    ws.addCell(lbl3 );
                    ws.addCell(lbl4 );
                    ws.addCell(lbl5 );
                    ws.addCell(lbl6 );
                    ws.addCell(lbl7 );
                    ws.addCell(lbl8 );
                    ws.addCell(lbl9 );
                    ws.addCell(lbl10);
                    ws.addCell(lbl11);

                }

                // 从内存中写入文件中
                wwb.write();
                wwb.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
