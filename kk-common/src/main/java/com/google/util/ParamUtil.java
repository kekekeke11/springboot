package com.google.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数工具类
 * @author wk
 */
public class ParamUtil {

    /**
     * 生成接口请求序列号-部分银行保理使用
     *
     * @return
     */
    public static String producterialNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        return sdf.format(new Date());
    }

    /**
     * 当前日期增加或减少N天
     *
     * @param format
     * @param num
     * @param currentDate
     * @return
     */
    public static String addOrCutNDay(String format, int num, String currentDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = null;
            date = sdf.parse(currentDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, num);// 时间+N天
            Date nextDate = calendar.getTime();
            return sdf.format(nextDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addOrCutNDay(String format, int num, Date currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, num);// 时间+N天
        Date nextDate = calendar.getTime();
        return sdf.format(nextDate);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getLoanDate() {
        // 清除时分秒 毫秒
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Integer getDaysNum(Date promiseDate, Date loanDate) {
        Calendar expireCalendar = Calendar.getInstance();
        expireCalendar.setTime(promiseDate);
        expireCalendar.set(Calendar.HOUR_OF_DAY, 0);
        expireCalendar.set(Calendar.MINUTE, 0);
        expireCalendar.set(Calendar.SECOND, 0);
        expireCalendar.set(Calendar.MILLISECOND, 0);

        Calendar loanCalendar = Calendar.getInstance();
        loanCalendar.setTime(loanDate);
        loanCalendar.set(Calendar.HOUR_OF_DAY, 0);
        loanCalendar.set(Calendar.MINUTE, 0);
        loanCalendar.set(Calendar.SECOND, 0);
        loanCalendar.set(Calendar.MILLISECOND, 0);
        Long temp = (expireCalendar.getTime().getTime() - loanCalendar.getTime().getTime()) / (24 * 60 * 60 * 1000);
        return temp.intValue();
    }



    /**
     * 目录名称
     *
     * @return
     */
    public static String productDirectoryName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return new StringBuffer(sdf.format(date)).append(date.getTime()).toString();
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateConvertString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串转日期
     * @param date
     * @param pattern
     * @return
     */
    public static Date stringConvertToDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dataFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String d = sdf.format(date);
        try {
            return sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字符串后几位
     * @param string
     * @param length
     * @return
     */
    public static String subStringAfterNChar(String string, int length){
        return string.substring(string.length()-length);
    }

    /**
     * 生成20位流水号
     * @return
     */
    public static String producterialTwentyNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sdf.format(new Date());
        Random random= new Random(System.currentTimeMillis());
        String rand= (Math.abs(random.nextInt(900))+100)+"";
        return new StringBuffer(strDate).append(rand).toString();
    }

    /**
     * 截取字符串
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String getStrContainData(String str, String start, String end) {
        String s = null;
        Pattern p = Pattern.compile(start + "(.*)" + end);
        Matcher m = p.matcher(str);
        while (m.find()) {
            s = m.group(1);
        }
        return s;
    }

    public static void main(String[] args) {
        String s = producterialTwentyNo();
        System.out.println(s);
        System.out.println(s.length());
        System.out.println(dateConvertString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(ParamUtil.dateConvertString(new Date(),"yyyy-MM-dd'T'HH:mm:ss'+08:00'"));
    }
}
