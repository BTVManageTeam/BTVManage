package com.cdvcloud.rochecloud.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;


/**
 * 时间工具类
 * 
 * @author TYW
 * 
 */
public class DateUtil {

	public static final String ISO_DATE_FORMAT = "yyyyMMdd";
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static boolean LENIENT_DATE = false;
	private static final Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * 获得当前的年份
	 * 
	 * @return
	 */
	public static int getYear() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.YEAR);
	}

	/**
	 * 获得当前的月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前的日
	 * 
	 * @return
	 */
	public static int getDay() {
		GregorianCalendar now = new GregorianCalendar();
		Date date = new Date();
		now.setTime(date);
		return now.get(Calendar.DAY_OF_MONTH);
	}


	/**
	 * 根据给定的时间（yyyy-MM-dd hh:mm:ss）增加若干秒后的时间（yyyy-MM-dd hh:mm:ss）
	 * 
	 * @param datetime
	 *            计算的开始时间（yyyy-MM-dd hh:mm:ss）
	 * @param seconds
	 *            增加的秒数
	 * @return 时间字符串（yyyy-MM-dd hh:mm:ss）
	 */
	public static final String dateIncreaseBySeconds(String datetime, int seconds) {
		SimpleDateFormat format = new SimpleDateFormat(DATETIME_PATTERN, Locale.CHINA);
		String datetimeseconds = null;
		try {
			java.util.Date date = format.parse(datetime);
			long lonTime = (date.getTime() / 1000) + seconds;
			date.setTime(lonTime * 1000);
			datetimeseconds = format.format(date);
		} catch (Exception e) {
			logger.error("给指定时间增加n秒异常：" + e.getMessage());
		}
		return datetimeseconds;
	}

	/**
	 * 日期增加
	 * 
	 * @param isoString
	 *            日期字符串
	 * @param fmt
	 *            格式
	 * @param field
	 *            年/月/日 Calendar.YEAR/Calendar.MONTH/Calendar.DATE
	 * @param amount
	 *            增加数量
	 * @return
	 * @throws ParseException
	 */
	private static final String dateIncrease(String isoString, String fmt, int field, int amount) {
		try {
			Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
			cal.setTime(stringToDate(isoString, fmt, true));
			cal.add(field, amount);
			return dateToString(cal.getTime(), fmt);
		} catch (Exception e) {
			logger.error("日期增加异常：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @param lenient
	 *            日期越界标志
	 * @return
	 */
	public static Date stringToDate(String dateText, String format, boolean lenient) {
		if (dateText == null) {
			return null;
		}
		DateFormat df = null;
		try {
			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format, Locale.CHINA);
			}
			df.setLenient(false);
			return df.parse(dateText);
		} catch (ParseException e) {
			logger.error("字符串转换为日期异常：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {
		return stringToDate(dateString, format, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
	}

	/**
	 * 根据时间变量返回时间字符串
	 * 
	 * @return 返回时间字符串
	 * @param pattern
	 *            时间字符串样式
	 * @param date
	 *            时间变量
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern, Locale.CHINA);
			sfDate.setLenient(false);
			return sfDate.format(date);
		} catch (Exception e) {
			logger.error("根据时间变量返回时间字符串异常：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 返回当前时间
	 * 
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();
		return dtNow;
	}

	/**
	 * 返回当前时间-毫秒数
	 **/
	public static long getCurrentDateTimeMsecond() {
		return new Date().getTime();
	}

	/**
	 * 返回当前时间-秒数
	 **/
	public static long getCurrentDateTimeSecond() {
		return getCurrentDateTime().getTime() / 1000;
	}

	/**
	 * 返回当前日期字符串
	 * 
	 * @param pattern
	 *            日期字符串样式
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		return dateToString(getCurrentDateTime(), pattern);
	}

	/**
	 * 返回当前日期字符串 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		return dateToString(getCurrentDateTime(), ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 返回当前日期+时间字符串 yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringWithTime(Date date) {
		return dateToString(date, DATETIME_PATTERN);
	}

	/**
	 * 日期增加-按日增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 日期增加-按月增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByMonth(Date date, int mnt) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.MONTH, mnt);
		return cal.getTime();
	}

	/**
	 * 日期增加-按年增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByYear(Date date, int mnt) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.YEAR, mnt);
		return cal.getTime();
	}

	/**
	 * 日期增加
	 * 
	 * @param date
	 *            日期字符串 yyyy-MM-dd
	 * @param days
	 * @return 日期字符串 yyyy-MM-dd
	 */
	public static String dateIncreaseByDay(String date, int days) {
		return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
	}

	/**
	 * 日期增加
	 * 
	 * @param date
	 *            日期字符串
	 * @param fmt
	 *            日期格式
	 * @param days
	 * @return
	 */
	public static String dateIncreaseByDay(String date, String fmt, int days) {
		return dateIncrease(date, fmt, Calendar.DATE, days);
	}

	/**
	 * 日期字符串格式转换
	 * 
	 * @param src
	 *            日期字符串
	 * @param srcfmt
	 *            源日期格式
	 * @param desfmt
	 *            目标日期格式
	 * @return
	 */
	public static String stringToString(String src, String srcfmt, String desfmt) {
		return dateToString(stringToDate(src, srcfmt), desfmt);
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static final String getCurrentTime() {
		long time = Calendar.getInstance().getTimeInMillis();
		return format(time, DATETIME_PATTERN);
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param d
	 *            java.util.Date
	 * @return
	 */
	public static final String getCurrentTime(Date d) {
		return format(d, DATETIME_PATTERN);
	}

	/**
	 * 获取当前时间 yyyy-MM-dd 00:00:00
	 * 
	 * @return
	 */
	public static final String getCurrentDate() {
		long time = Calendar.getInstance().getTimeInMillis();
		return format(time, "yyyy-MM-dd 00:00:00");
	}

	/**
	 * 获取当前时间 yyyy-MM-dd 00:00:00
	 * 
	 * @param d
	 *            java.util.Date
	 * @return
	 */
	public static final String getCurrentDate(Date d) {
		return format(d, "yyyy-MM-dd 00:00:00");
	}

	/**
	 * 获取当前时间 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static final String getCurrentDateShortStyle() {
		long time = Calendar.getInstance().getTimeInMillis();
		return format(time, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 获取当前时间 yyyy-MM-dd
	 * 
	 * @param d
	 *            java.util.Date
	 * @return
	 */
	public static final String getCurrentDateShortStyle(Date d) {
		return format(d, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 截取 yyyy-MM-dd HH:mm:ss 为 yyyy-MM-dd
	 * 
	 * @param longStyleDate
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static final String shortStyle(String longStyleDate) {
		if (longStyleDate == null || "".equals(longStyleDate))
			return "0000-00-00";
		return longStyleDate.substring(0, 10);
	}

	/**
	 * 补全yyyy-MM-dd 为 yyyy-MM-dd 00:00:00
	 * 
	 * @param shortStyleDate
	 *            yyyy-MM-dd
	 * @return
	 */
	public static final String longStyle(String shortStyleDate) {
		if (shortStyleDate == null || "".equals(shortStyleDate))
			return "0000-00-00 00:00:00";
		return shortStyleDate + " 00:00:00";
	}

	/**
	 * 日期增加-按日增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date:返回与当天间隔指定日期的日期,返回日期格式"yyyy-MM-dd 00:00:00");
	 */
	public static String dateIncreaseByDay(int days) {
		Date d = dateIncreaseByDay(new Date(), days);
		return format(d.getTime(), "yyyy-MM-dd 00:00:00");
	}

	/**
	 * 日期增加-按日增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date:返回与当天间隔指定日期的时间,日期格式DATETIME_PATTERN);
	 */
	public static String timeIncreaseByDay(int days) {
		Date d = dateIncreaseByDay(new Date(), days);
		return format(d.getTime(), DATETIME_PATTERN);
	}

	/**
	 * 日期增加-按月增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date:返回与当天间隔指定月的日期,
	 */
	public static String dateIncreaseByMonth(int mnt) {
		Date d = dateIncreaseByMonth(new Date(), mnt);
		return format(d.getTime(), "yyyy-MM-dd 00:00:00");
	}

	/**
	 * 日期增加-按月增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date:返回与当天间隔指定月的时间,
	 */
	public static String timeIncreaseByMonth(int mnt) {
		Date d = dateIncreaseByMonth(new Date(), mnt);
		return format(d.getTime(), DATETIME_PATTERN);
	}

	/**
	 * 日期增加-按年增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date:返回与当天间隔指定年的时间
	 */
	public static String dateIncreaseByYear(int mnt) {
		Date d = dateIncreaseByYear(new Date(), mnt);
		return format(d.getTime(), "yyyy-MM-dd 00:00:00");
	}

	/**
	 * 日期增加-按年增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date:返回与当天间隔指定年的时间
	 */
	public static String timeIncreaseByYear(int mnt) {
		Date d = dateIncreaseByYear(new Date(), mnt);
		return format(d.getTime(), DATETIME_PATTERN);
	}

	/**
	 * 返回n小时后的时间
	 * 
	 * @param endTime
	 *            yyyy-MM-dd HH:mm:ss
	 * @param n
	 * @return n为正数，则返回endTime后n小时的时间；n为负数，则返回endTime前n小时的时间
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static String getPreviousTime(String endTime, int n) {
		String previousTime = "";
		SimpleDateFormat df = new SimpleDateFormat(DATETIME_PATTERN, Locale.CHINA);
		try {
			previousTime = df.format(df.parse(endTime).getTime() + n * 60 * 60 * 1000);
		} catch (ParseException e) {
			logger.error("返回n小时后的时间异常：" + e.getMessage());
		}
		return previousTime;
	}



	/**
	 * 获取当前日期 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat ft = new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT, Locale.CHINA);
		Date dd = new Date();
		return ft.format(dd);
	}

	/**
	 * 计算两个日期的相差天数
	 * 
	 * @param time1
	 *            yyyy-MM-dd HH:dd:ss
	 * @param time2
	 *            yyyy-MM-dd HH:dd:ss
	 * @return 天
	 */
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT, Locale.CHINA);
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (Exception e) {
			logger.error("计算两个日期的相差天数异常：" + e.getMessage());
		}
		return quot;
	}

	/**
	 * 根据指定格式对时间格式化
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Object date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern, Locale.CHINA);
			sfDate.setLenient(false);
			return sfDate.format(date);
		} catch (Exception e) {
			logger.error("根据指定格式对时间格式化异常：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取昨日
	 * 
	 * @param i
	 *            =-1
	 * 
	 * 
	 * @return昨日
	 */
	@SuppressWarnings("static-access")
	public static Date getYesterday(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(calendar.DAY_OF_MONTH, calendar.get(calendar.DAY_OF_MONTH) + i);
		return calendar.getTime();
	}

	/**
	 * 获取本周一
	 * 
	 * @return 本周一
	 */
	public static Date getNowWeekMonday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 解决周日会出现 并到下一周的情况
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return 本月第一天
	 */
	public static Date getFirstDayMonth() {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return calendar.getTime();
	}

	/**
	 * 获取今年第一天
	 * 
	 * @return 本月第一天
	 */
	public static Date getFirstDayYear() {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR, 1);// 设置为1号,当前日期既为本月第一天
		return calendar.getTime();
	}

	/**
	 * 通过今日/昨日/本周/本月/今年获取开始+结束时间
	 * 
	 * @return 开始时间,结束时间
	 */
	public static String getBeginAndEndTime(String shortcutTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		String result = "";
		if (shortcutTime.equals("今日")) {
			result = sdf.format(new Date()) + "," + sdf.format(new Date());
		} else if (shortcutTime.equals("昨日")) {
			result = sdf.format(getYesterday(-1)) + "," + sdf.format(getYesterday(-1));
		} else if (shortcutTime.equals("本周")) {
			result = sdf.format(getNowWeekMonday()) + "," + sdf.format(new Date());
		} else if (shortcutTime.equals("本月")) {
			result = sdf.format(getFirstDayMonth()) + "," + sdf.format(new Date());
		} else if (shortcutTime.equals("今年")) {
			result = sdf.format(getFirstDayYear()) + "," + sdf.format(new Date());
		} else {
			result = "参数不正确";
		}
		return result;
	}

	/**
	 * 查询指定时间段内的时间点集合
	 * 
	 * @param dBegin
	 *            开始时间
	 * @param dEnd
	 *            结束时间
	 * @return
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * 按周分组
	 * 
	 * @param starTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 * @throws ParseException
	 */
	public static List<Map<String, Object>> findWeekDate(String starTime, String endTime) throws ParseException {
		List<Map<String, Object>> maplists = new ArrayList<Map<String, Object>>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date sdate = fmt.parse(starTime);
		Date edate = fmt.parse(endTime);
		String ksTime = getSundayOfThisWeek(sdate);// 开始时间的--周日
		String jsTime = getMondayOfThisWeek(edate);// 结束时间的--周一
		String jsTimeCopy = getSundayOfThisWeek(edate);// 结束时间周日

		// 同一周
		if (ksTime.equals(jsTimeCopy)) {
			datemap.put("stime", starTime);
			datemap.put("etime", endTime);
			maplists.add(datemap);
			// 非同一周
		} else {
			// 上半周
			// logger.info("上半周开始   " + starTime + "  周日: " + ksTime);
			// 下半周
			// logger.info("下半周结束  " + endTime + "  周一: " + jsTime);
			// 上半周
			datemap.put("stime", starTime);// 开始时间
			datemap.put("etime", ksTime);// 周日
			maplists.add(datemap);

			// 两个日期中有周
			String sundayTime = getSpecifiedDayBefore(jsTime);// 结束时间-周一的前一天【周日】
			if (!ksTime.equals(sundayTime)) {
				String mondayTime = getSpecifiedDayAfter(ksTime);// 开始时间-周日的后一天【周一】
				Date ksdate = null;
				Date jsdate = null;
				try {
					ksdate = fmt.parse(mondayTime);
					jsdate = fmt.parse(sundayTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calenBegin = new GregorianCalendar();
				Calendar calenEnd = new GregorianCalendar();

				calenBegin.setTime(ksdate);
				calenEnd.setTime(jsdate); // set(2015,5,3)Calendar的月从0-11，所以5月是4.
				calenEnd.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

				datemap = new HashMap<String, Object>();
				while (calenBegin.before(calenEnd)) {
					// 周一
					if (calenBegin.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						datemap.put("stime", fmt.format(calenBegin.getTime()));
					}
					// 周日
					if (calenBegin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						datemap.put("etime", fmt.format(calenBegin.getTime()));
					}
					if (datemap.containsKey("etime") && !datemap.isEmpty()) {
						maplists.add(datemap);
						datemap = new HashMap<String, Object>();
					}

					calenBegin.add(Calendar.DAY_OF_YEAR, 1);

				}

			}

			// 下半周
			datemap = new HashMap<String, Object>();
			datemap.put("stime", jsTime);// 周一
			datemap.put("etime", endTime);// 结束时间
			maplists.add(datemap);

		}
		return maplists;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 *            日期字符串
	 * @return 前一天日期
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = stringToDate(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return dateToString(c.getTime());
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 *            日期字符串
	 * @return 后一天日期
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = stringToDate(specifiedDay);
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		return dateToString(c.getTime());
	}

	/**
	 * 得到指定时间的周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		return dateToString(c.getTime());
	}

	/**
	 * 得到指定时间的周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		return dateToString(c.getTime());

	}

	/**
	 * yyyy-MM-dd 转换为：yyyy/MM/dd
	 * 
	 * @param time
	 *            当前时间
	 * @return
	 */
	public static String dateHorToDateBias(String time) {
		SimpleDateFormat sdfh = new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT, Locale.CHINA);
		SimpleDateFormat sdfx = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		try {
			String nowTime = sdfx.format(sdfh.parse(time));
			return nowTime;
		} catch (ParseException e) {
			logger.error("字符串转换为日期异常：" + e.getMessage());
			return null;
		}

	}

	/**
	 * 按月分组
	 * 
	 * @param starTime
	 * @param endTime
	 * @return
	 */
	public static List<Map<String, Object>> findGroupByMonth(String starTime, String endTime) {
		List<Map<String, Object>> maplists = new ArrayList<Map<String, Object>>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		Date startDate = stringToDate(starTime);
		Date endDate = stringToDate(endTime);

		// 若是同一个年月内
		if (isSameYYYYMM(startDate, endDate)) {
			datemap.put("stime", starTime);
			datemap.put("etime", endTime);
			maplists.add(datemap);
		} else {

			// 上半月
			Date sDate = getLastDayOfMonth(startDate);
			String sDateStr = dateToString(sDate);// 上半月月末
			Date eDate = getFirstDayOfMonth(endDate);
			String eDateStr = dateToString(eDate) == null ? "" : String.valueOf(dateToString(eDate));// 下半月月初

			datemap.put("stime", starTime);// 上半月开始时间
			datemap.put("etime", sDateStr);// 上半月结束时间
			maplists.add(datemap);

			Date ksTime = dateIncreaseByDay(sDate, +1);// 上半月月末+1
			String ksTimeStr = dateToString(ksTime) == null ? "" : String.valueOf(dateToString(ksTime));
			Date jsTime = dateIncreaseByDay(eDate, -1);// 下半月月初-1
			if (null !=ksTimeStr && !ksTimeStr.equals(eDateStr)) {
				// 中间有间隔月份
				Calendar calendarBegin = new GregorianCalendar();
				Calendar calendarEnd = new GregorianCalendar();

				calendarBegin.setTime(ksTime);
				calendarEnd.setTime(jsTime);

				calendarEnd.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天

				datemap = new HashMap<String, Object>();
				// 循环添加每月月初和月末
				while (calendarBegin.before(calendarEnd)) {
					datemap.put("stime", dateToString(getFirstDayOfMonth(calendarBegin.getTime())));// 月初
					datemap.put("etime", dateToString(getLastDayOfMonth(calendarBegin.getTime())));// 月末
					if (!maplists.contains(datemap)) {
						maplists.add(datemap);
						datemap = new HashMap<String, Object>();
					}
					calendarBegin.add(Calendar.DAY_OF_YEAR, 1);

				}
			}else{
				logger.info("查询条件不一致：ksTimeStr："+ksTimeStr+" eDateStr:"+eDateStr);
			}

			// 下半月
			datemap = new HashMap<String, Object>();
			datemap.put("stime", eDateStr);// 下半月第一天
			datemap.put("etime", endTime);// 下半月结束时间
			maplists.add(datemap);

		}

		return maplists;
	}

	/**
	 * 返回指定日期的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return calendar.getTime();
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 判断两个日期是否为同年同月
	 * 
	 * @param date1
	 *            date1
	 * @param date2
	 *            date2
	 * @return boolean
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isSameYYYYMM(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
	}

	/**
	 * reg 转换为：nReg
	 * 
	 * @param time
	 * @param reg
	 * @param nReg
	 * @return
	 */
	public static String dateHorToDateBias(String time, String reg, String nReg) {
		SimpleDateFormat sdfh = new SimpleDateFormat(reg, Locale.CHINA);
		SimpleDateFormat sdfx = new SimpleDateFormat(nReg, Locale.CHINA);
		try {
			String nowTime = sdfx.format(sdfh.parse(time));
			return nowTime;
		} catch (ParseException e) {
			logger.error("字符串转换为日期异常：" + e.getMessage());
			return null;
		}

	}

	
	/**
	 * 将数字格式化，一位数字前面补0
	 * 
	 * @param i
	 * @return
	 */
	public static String formatNum(int i) {
		String result = "";
		if (i < 10) {
			result += "0" + i;
		} else {
			result += i;
		}
		return result;
	}

	/**
	 * 获取哪一年第几周的周一/周日日期
	 * 
	 * @param string
	 *            yyyy-week 年+周
	 * @return monday+","+sunday
	 */
	@SuppressWarnings("static-access")
	public static String getMondayAndSunday(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		int weekYear = Integer.parseInt(string.split("/")[0]);
		int weekOfYear = Integer.parseInt(string.split("/")[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 中国习俗，周一为一周开始
		calendar.set(calendar.YEAR, weekYear);
		calendar.set(calendar.WEEK_OF_YEAR, weekOfYear);
		calendar.set(calendar.DAY_OF_WEEK, 1);
		String sunday = sdf.format(calendar.getTime());
		calendar.set(calendar.DAY_OF_WEEK, 2);
		String monday = sdf.format(calendar.getTime());
		// calendar.setWeekDate(weekYear, weekOfYear, 1);//获取周日的日期jdk1.7才有
		// String sunday =sdf.format(calendar.getTime());
		// calendar.setWeekDate(weekYear, weekOfYear, 2);//获取周一日期
		// String monday = sdf.format(calendar.getTime());
		return monday + "-" + sunday;
	}

	/**
	 * 获取某月的1号和最后一号
	 * 
	 * @param string
	 * @return 如：2014/08/01-2014/08/31
	 */
	public static String getMonthFirstLastDay(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		Date date;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return "参数错误";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		String lastDay = sdf.format(calendar.getTime());
		return firstDay + "-" + lastDay;
	}

	public static void main(String[] args) {
		String a = "516 Kbps";
		a = a.replace(" Kbps", "");
		System.out.println(a);
	}

}