package com.cn.util;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.cn.util.StringUtils.isEmpty;
import static com.cn.util.StringUtils.isNotEmpty;

/***            : 日期工具类
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Group Ltd.
 * @Project     ：vigorbox
 * @Title       ：DateUtil
 * @Description ：日期工具类
 * @Version     ：Ver1.0
 * @Author      ：ChenWen
 * @Date        ：2024-02-07 11:28
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Group Ltd.
 * @Copyright Copyright (C), All rights Reserved,2016-2024, 圆和医疗集团有限公司
 ***/
@Slf4j
public class DateUtil {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYMMDDHHMMSS = "yyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_T_HH_MM_SS_SSS = "yyyy-MM-ddTHH:mm:ss";
    public static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取当前日期，默认格式为 yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String getTimeHHMM() {
        return dateTimeNow(YYYY_MM_DD_HH_MM);
    }



    public static final String getTimeHH(){return dateTimeNow(YYYY_MM_DD_HH);}

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String dateTimeHHMMSS(final Date date) {
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
    }


    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    public static final String dateSame(String sameTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Date sameDate = dateFormat.parse(sameTime);

        return DateFormatUtils.format(sameDate, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如 20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }



    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static String getDateStr(Date dateParam){
        //Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化日期为字符串
        String formattedDate = sdf.format(dateParam);
        return formattedDate;
    }


    public static String getNowDate(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化日期为字符串
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }

    public static String getShortNowDate(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(YYMMDDHHMMSS);
        // 格式化日期为字符串
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }



    public static String getNowDateStr(){
        LocalDateTime currentDate = LocalDateTime.now();
        //Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化日期为字符串
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }

    public static String getNowDateToSecond(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化日期为字符串
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }

    public static LocalDate stringToLocalDate(String dateString){

        if(!StringUtils.isEmpty(dateString)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);

            return LocalDate.parse(dateString, formatter);
        }else{
            return null;
        }
    }

    public static LocalDateTime stringToLocalDateTime(String dateString){

        if(!StringUtils.isEmpty(dateString)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

            return LocalDateTime.parse(dateString, formatter);
        }else{
            return null;
        }
    }
    public static LocalDateTime stringToLocalDateTimeH(String dateString){

        if(!StringUtils.isEmpty(dateString)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

            return LocalDateTime.parse(dateString, formatter);
        }else{
            return null;
        }
    }

    public static LocalDate stringToLocalDateymdhms(String dateString){

        if(!StringUtils.isEmpty(dateString)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

            return LocalDate.parse(dateString, formatter);
        }else{
            return null;
        }
    }
    public static Date addOneYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }
    public static Date stringToDateTime(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        if(!StringUtils.isEmpty(dateString)){

            // 格式化日期为字符串
            Date date = null;
            try {
                return  dateFormat.parse(dateString);
            } catch (ParseException e) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
                LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
                ZoneId zoneId = ZoneId.systemDefault();
                Date date1 = Date.from(localDateTime.atZone(zoneId).toInstant());
                return date1 ;
            }
        }else{
            return null;
        }
    }

    public static Date tStringToDateTime(String dateString) {
        if(isNotEmpty(dateString)) {
            LocalDateTime localDateTime = LocalDateTime.parse(dateString);
            ZoneId zoneId = ZoneId.systemDefault();
            Date date = Date.from(localDateTime.atZone(zoneId).toInstant());

            SimpleDateFormat targetFormatter = new SimpleDateFormat(YYYY_MM_DD);
            targetFormatter.format(date) ;
            return date;
        }

        return new Date() ;
    }

    public static Date localStringToDate(String dateString) {
        if (!StringUtil.isEmpty(dateString)) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(YYYY_MM_DD);
            Date date = new Date() ;
            try {
                date = formatter.parse(dateString);

                //System.out.println(date);

                //return date;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        return new Date();
    }

    public static Date longStringToDateTime(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
        if(!StringUtils.isEmpty(dateString)){

            // 格式化日期为字符串
            Date date = null;
            try {
                return  dateFormat.parse(dateString);
            } catch (ParseException e) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
                LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
                ZoneId zoneId = ZoneId.systemDefault();
                Date date1 = Date.from(localDateTime.atZone(zoneId).toInstant());
                return date1 ;
            }
        }else{
            return null;
        }
    }

    public static Date stringToDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the string into a Date object
            Date date = dateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            String date = dateFormat.format(new Date());
            return dateFormat.parse(date);
        }
    }

    public static Date stringToDateHHss(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");

        try {
            // Parse the string into a Date object
            Date date = dateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            String date = dateFormat.format(new Date());
            return dateFormat.parse(date);
        }
    }
    public static String getNowDateToSecond2(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // 格式化日期为字符串
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }

    public static String getNowDateToSecond3(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 实例，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        // 格式化日期为字符串
        String formattedDate = sdf.format(currentDate);
        return formattedDate;
    }

    public static String getNextYearToday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }


    public static long getTimeDifference(LocalDateTime createTime) {
        Duration duration = Duration.between(createTime, LocalDateTime.now());

        return duration.toHours();

    }

    public static long getDateTimeDifference(Date date){
        Date now = new Date();
        long differenceInMillis = Math.abs(date.getTime() - now.getTime());
        long differenceInHours = differenceInMillis / (1000 * 60 * 60); // Convert milliseconds to hours

        return differenceInHours;
    }

    public static int getNowYear(){
        return LocalDate.now().getYear();
    }

    /***            : first 时间是否在 second 时间之前,
         *@funcName     : compareDateBefore
         *@description  : description
         *@Param1       : java.lang.String  first
         *@Param2       : java.lang.String  second
         *@return       : boolean
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-04-29 13:32
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-04-29 13:32
         ***/
    public static boolean compareDateBefore(String first,String second){
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);

        try {
            Date date1 = dateFormat.parse(first);
            Date date2 = dateFormat.parse(second);

            return date1.before(date2);
        } catch (Exception e) {
            log.debug("比较时间出错:{}",e.getMessage());
            return false;
        }
    }

    /***            : 判断字符串时间 增加一年是否在Date之前
         *@funcName     : compareStringDateBefore
         *@description  : description
         *@Param1       : java.lang.String  time
         *@Param2       : java.lang.String  date
         *@return       : ResponseVO
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-07-25 13:17
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-07-25 13:17
         ***/
    public static boolean compareStringDateBefore(String time,Date date){

        return compareDateBefore(getDateStr(addOneYear(stringToDateTime(time))),getDateStr(date));
    }

    public static String turnZtime(String dateTimeString){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);

        try {
            Date date = inputFormat.parse(dateTimeString);
            outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String formattedDateTime = outputFormat.format(date);
            log.debug("Formatted date and time:{}",formattedDateTime);
            return formattedDateTime;
        } catch (Exception e) {
            log.debug("Error occurred:{}" , e.getMessage());
        }
        return null;
    }

    public static String turnTime(String dateTimeString){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);

        try {
            Date date = inputFormat.parse(dateTimeString);
            outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String formattedDateTime = outputFormat.format(date);
            log.debug("Formatted date and time:{}",formattedDateTime);
            return formattedDateTime;
        } catch (Exception e) {
            log.debug("Error occurred:{}" , e.getMessage());
        }
        return null;
    }

    public static String turnTimeHh(String dateTimeString){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat(YYYY_MM_DD_HH);

        try {
            Date date = inputFormat.parse(dateTimeString);
            outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            String formattedDateTime = outputFormat.format(date);
            log.debug("Formatted date and time:{}",formattedDateTime);
            return formattedDateTime;
        } catch (Exception e) {
            log.debug("Error occurred:{}" , e.getMessage());
        }
        return null;
    }

    public static String turnTimeHhMm(String dateTimeString){

        try {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        // 解析日期时间字符串
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, inputFormatter);
            // 将 ZonedDateTime 转换为中国标准时间的 ZonedDateTim
            ZonedDateTime targetZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
            LocalDateTime localDateTime = targetZonedDateTime.toLocalDateTime();
            // 将 ZonedDateTime 转换为 LocalDateTime
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 格式化日期时间
        String formattedDateTime = localDateTime.format(outputFormatter);
        log.debug("Formatted date and time:{}",formattedDateTime);
        return formattedDateTime;
        } catch (Exception e) {
            log.debug("Error occurred:{}" , e.getMessage());
        }
        return null;
    }

    /***            : 传入的 dateString 时间 距离当前时间天数
         *@funcName     : sameTimeToNowCountDayNum
         *@description  : description
         *@Param1       : java.lang.String  dateString
         *@return       : ResponseVO
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-07-23 9:07
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-07-23 9:07
         ***/
    public static long sameTimeToNowCountDayNum(String dateString){

        if(isEmpty(dateString)){
            return 0L;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

        LocalDate specifiedDate = LocalDate.parse(dateString, formatter);

        LocalDate currentDate = LocalDate.now();

        return ChronoUnit.DAYS.between(specifiedDate, currentDate);
    }

    /***            : 传入的 dateString 时间 距离当前时间年数
     *@funcName     : sameTimeToNowCountDayNum
     *@description  : description
     *@Param1       : java.lang.String  dateString
     *@return       : ResponseVO
     *@version      :
     *@author       : LiZhen
     *@date         : 2024-07-23 9:07
     *@revision     : Ver1.0
     *@reviser      : LiZhen
     *@reviseDate   : 2024-07-23 9:07
     ***/
    public static long sameTimeToNowCountYearNum(String dateString){

        if(isEmpty(dateString)){
            return 0L;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

        LocalDate specifiedDate = LocalDate.parse(dateString, formatter);

        LocalDate currentDate = LocalDate.now();

        return ChronoUnit.YEARS.between(specifiedDate, currentDate);
    }

    public static long sameDateToNowCountDayNum(Date dateString){

        if(ObjectUtils.isEmpty(dateString)){
            return 0L;
        }
        Instant instant = dateString.toInstant();

        LocalDate specifiedDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();;

        LocalDate currentDate = LocalDate.now();

        return ChronoUnit.DAYS.between(specifiedDate, currentDate);
    }

    public static String turnTime2(String dateTimeString) throws ParseException {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");

        Date date = inputFormat.parse(dateTimeString);
        outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String formattedDateTime = outputFormat.format(date);
        log.debug("Formatted date and time:{}",formattedDateTime);
        return formattedDateTime;

    }

    public static LocalDate dateTurnLocalDate(Date date){

        if(date == null){
            return null;
        }else{
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

    }

    public static Date localDateTurnDate(LocalDate localDate){

        if(localDate == null){
            return null;
        }else{
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

    }

    public static String dateFromSeconds(Long seconds){
        Date date = new Date();
        date.setTime(seconds);
        return setDateFormat(date);
    }

    public static String setDateFormat(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(date);
    }

    /***            : 把时间yyyy年MM月dd日 HH时mm分 格式化成yyyy-MM-dd HH:mm:ss
         *@funcName     : getReportDetail
         *@description  : description
         *@Param1       : java.lang.String  target
         *@Param2       : java.lang.String  registerEntity
         *@return       : ResponseVO
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 15:07
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 15:07
         ***/
    public static String chineseFormat(String inputDate){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);

        return dateTime.format(outputFormatter);
    }
}
