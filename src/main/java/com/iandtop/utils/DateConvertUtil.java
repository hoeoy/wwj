package com.iandtop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/3 0003
 */
public class DateConvertUtil {
    /**
     * @deprecated 将时间转换为时间戳
     * @param s 时间
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp() throws ParseException {

        return String.valueOf(System.currentTimeMillis());
    }

    /**
   * 将时间戳转换为时间
     * @param s
     */
    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
