package com.xr.run.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：wzh
 * @date ：Created in 2020/6/13 9:45
 * @Message : 时间工具类
 */
public class DateUtil {
    /**
     * 修改时间格式
     * @param
     * @return
     */
    public static String upDate(Date argDate){
        long time = argDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(time);// new Date()为获取当前系统时间，也可使用当前时间戳
        Date parse = null;

        return date;
    }
    /**
     * 获取当前时间
     * @param formate 时间格式
     * @return
     */
    public static Date getDate(String formate){
        long time = new Date().getTime();
        SimpleDateFormat df = new SimpleDateFormat(formate);//设置日期格式
        String date = df.format(time);// new Date()为获取当前系统时间，也可使用当前时间戳
        Date parse = null;
        try {
            parse = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
    /**
     * 获取当前时间 "yyyy-MM-dd HH:mm:ss"
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static Date getDate(){

        return getDate("yyyy-MM-dd HH:mm:ss");
    }
}
