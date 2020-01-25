package com.example.wenda01.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算公式：[Y*D+C]-L
 *
 * @author
 *
 */
public class SolarTerm {   //page状态改变
    /**
     * 节气D值
     */
    private static final double D = 0.2422;
    /**
     * 20世纪的节气C值
     */
    private static final double[] C_20 = { 6.11, 20.84, 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86,
            6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6 };
    /**
     * 21世纪的节气C值
     */
    private static final double[] C_21 = { 5.4055, 20.12, 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678,
            21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94 };

    /**
     * 24节气
     */
    private static final String[] TERM = { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑",
            "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };

    /**
     * 节气缓存表
     */
    private static Map<Integer, Map<String, String>> termMap = new HashMap<Integer, Map<String, String>>();

    /**
     * 获取指定日期节气名称
     *
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static String getTermName(int year, int month, int date) {
        Map<String, String> map = getYearTermMap(year);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return map.get(getTermKey(month, date));
    }

    /**
     * 获取指定年份萨拉表
     *
     * @param year
     * @return
     */
    public static Map<String, String> getYearTermMap(int year) {
        // 处理世纪C值
        double[] c = null;
        if (year > 1900 && year <= 2000) {
            c = C_20;
        } else if (year > 2000 && year <= 2100) {
            c = C_21;
        } else {
            throw new RuntimeException("不支持的年份:" + year + ",目前只支持1901年到2100年的时间范围");
        }
        // 从节气表中取
        Map<String, String> map = termMap.get(year);
        if (map != null) {
            return map;
        }
        // 节气表中无对应的节气数据，则计算生成
        synchronized (TERM) {
            map = termMap.get(year);
            if (map == null) {
                int y = year % 100;
                map = new HashMap<String, String>();
                for (int k = 0; k < 24; k++) {
                    // 计算节气日期，计算公式：[Y*D+C]-L
                    int date = 0;
                    if (k < 2 || k > 22) {
                        date = (int) (y * D + c[k]) - (int) ((y - 1) / 4);
                    } else {
                        date = (int) (y * D + c[k]) - (int) (y / 4);
                    }
                    // 记录计算结果
                    map.put(getTermKey(k / 2 + 1, date), TERM[k]);
                }
                // 计算结果添加到节气表
                termMap.put(year, map);
            }
        }
        return map;
    }

    /**
     * 组装节气存储key
     *
     * @param month
     * @param date
     * @return
     */
    private static String getTermKey(int month, int date) {
        String key = String.valueOf(month);
        if (month < 10) {
            key = "0" + key;
        }
        if (date < 10) {
            key += "0";
        }
        key += date;
        return key;
    }

    public static void main(String[] args) {
        System.out.println(getTermName(2020, 12, 11));
    }
}

