/**
 * 各种转换、格式化方法或工具
 * @author 董一华
 * @since 1.00
 * @version 1.00 
 * 
 * 修订列表： v1.00 2012-12-4
 * 			修订人：王美蓉
 * 			修订内容：
 */

package com.htdz.utms.common.util;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class MyUtil {

	// 日志
	private static Logger log = Logger.getLogger(MyUtil.class);

	// 状态
	public static enum Status {
		NEW, // 新增
		AUDIT, // 审批/修改
		VIEW
		// 显示只读
	};

	/** ***************** 1. 日期方法开始 ************************** */

	/**
	 * 获取Calendar
	 * 
	 * @author dyh 2011-11-12
	 * @param date
	 *            YYYY-MM-dd
	 * @return 如果有异常，则返回今天
	 */
	public static Calendar getCalendar(String date) {
		return getCalendar(date, "yyyy-MM-dd");
	}

	/**
	 * 获取Calendar
	 * 
	 * @author dyh 2011-11-12
	 * @param date
	 * @param format
	 *            date的格式
	 * @return
	 */
	public static Calendar getCalendar(String date, String format) {
		return getCalendar(date, new SimpleDateFormat(format));
	}

	/**
	 * 获取Calendar
	 * 
	 * @author dyh 2011-11-12
	 * @param date
	 * @param format
	 *            date的格式
	 * @return
	 */
	public static Calendar getCalendar(String date, SimpleDateFormat format) {
		Calendar calendar = Calendar.getInstance();
		if (format != null) {
			try {
				calendar.setTime(format.parse(date));
			} catch (Exception e) {
			}
		}
		return calendar;
	}

	/** ***************** 1.1 日期方法开始：时分秒 ************************** */
	/**
	 * 获取当前时间HH:mm:ss
	 * 
	 * @author dyh 2009-10-24
	 * @return HH:mm:ss
	 */
	public final static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	// 考核报表的时长计算
	// 输入分钟数，返回“X天X小时X分钟”
	public static String getTime(int minute) {
		String strTime = "";
		int iDay = 0, iHour = 0, iMinute = 0, tmp = 0;
		tmp = minute;
		if (tmp > 0) {
			if (tmp / 1440 >= 1) {
				iDay = tmp / 1440;
				tmp = tmp - iDay * 1440;
			}

			if (tmp / 60 >= 1) {
				iHour = tmp / 60;
				tmp = tmp - iHour * 60;
			}

			iMinute = tmp;

			if (iDay > 0)
				strTime += iDay + "天";
			if (iHour > 0)
				strTime += iHour + "小时";
			if (iMinute > 0) {
				if (iDay > 0 && iHour == 0)
					strTime += "0小时";
				strTime += iMinute + "分钟";
			}
		} else if (tmp == 0)
			strTime = "0分钟";
		return strTime;
	}

	// 输入毫秒，返回“X天X小时X分钟X秒”
	public static String formatTime(long millisecond) {
		String strTime = "";
		long iDay = 0, iHour = 0, iMinute = 0, iSecond = 0, iMillisecond = 0, tmp = 0;
		tmp = millisecond;
		if (tmp > 0) {
			if (tmp / 86400000 >= 1) {
				iDay = tmp / 86400000;
				tmp = tmp - iDay * 86400000;
			}
			if (tmp / 3600000 >= 1) {
				iHour = tmp / 3600000;
				tmp = tmp - iHour * 3600000;
			}
			if (tmp / 60000 >= 1) {
				iMinute = tmp / 60000;
				tmp = tmp - iMinute * 60000;
			}
			if (tmp / 1000 >= 1) {
				iSecond = tmp / 1000;
				tmp = tmp - iSecond * 1000;
			}
			iMillisecond = tmp;

			if (iDay > 0)
				strTime += iDay + "天";
			if (iHour > 0)
				strTime += iHour + "小时";
			if (iMinute > 0) {
				if (iDay > 0 && iHour == 0)
					strTime += "0小时";
				strTime += iMinute + "分钟";
			}
			if (iSecond > 0) {
				if (iDay > 0) {
					if (iHour == 0)
						strTime += "0小时";
					if (iMinute == 0)
						strTime += "0分钟";
				} else if (iHour > 0) {
					if (iMinute == 0)
						strTime += "0分钟";
				}
				strTime += iSecond + "秒";
			}
			if (iMillisecond > 0) {
				if (iDay > 0) {
					if (iHour == 0)
						strTime += "0小时";
					if (iMinute == 0)
						strTime += "0分钟";
					if (iSecond == 0)
						strTime += "0秒";
				} else if (iHour > 0) {
					if (iMinute == 0)
						strTime += "0分钟";
					if (iSecond == 0)
						strTime += "0秒";
				} else if (iMinute > 0) {
					;
					if (iSecond == 0)
						strTime += "0秒";
				}
				strTime += iMillisecond + "毫秒";
			}
		} else if (tmp == 0)
			strTime = "0毫秒";
		return strTime;
	}

	/**
	 * 输入当前时间，判断是否在开始和结束时间段内
	 * 
	 * @author dyh 2011-1-11
	 * @param now
	 *            HH:mm
	 * @param starttime
	 *            HH:mm
	 * @param endtime
	 *            HH:mm
	 * @return
	 */
	public static boolean isInTime(String now, String starttime, String endtime) {
		if (isStrNull(now) || isStrNull(starttime) || isStrNull(endtime)) {
			return false;
		}
		if (starttime.compareTo(endtime) > 0) {// 如果开始时间迟于结束时间，则需要对调
			String tmp = starttime;
			starttime = endtime;
			endtime = tmp;
		}
		return now.compareTo(starttime) >= 0 && now.compareTo(endtime) <= 0;
	}

	/**
	 * 判断当前时间是否在开始和结束时间段内
	 * 
	 * @author dyh 2011-1-11
	 * @param starttime
	 *            HH:mm
	 * @param endtime
	 *            HH:mm
	 * @return
	 */
	public static boolean isInTime(String starttime, String endtime) {
		DateFormat df = new java.text.SimpleDateFormat("HH:mm");
		return isInTime(df.format(Calendar.getInstance().getTime()), starttime,
				endtime);
	}

	/** ***************** 1.1 日期方法结束：时分秒 ************************** */

	/** ***************** 1.2 日期方法开始：日 ************************** */
	/**
	 * 按"yyyy-MM-dd HH:mm"格式化日期
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 *            日期
	 * @return 返回格式化后的日期
	 */
	public final static String getDateFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}

	/**
	 * 按"yyyy-MM-dd HH:mm:ss"格式化日期
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 *            日期
	 * @return 返回格式化后的日期
	 */
	public final static String getFullDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = null;
		try {
			strDate = df.format(date);
		} catch (Exception e) {
		}
		return strDate;
	}

	/**
	 * 将"yyyy-MM-dd HH:mm:ss"转化成Date
	 * 
	 * @author dyh 2009-10-24
	 * @param strDate
	 *            yyyy-MM-dd HH:mm:ss
	 * @return 返回Date
	 */
	public final static Date setFullDate(String strDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	/**
	 * 获取今天(日期)
	 * 
	 * @author dyh 2009-10-24
	 * @return yyyy-MM-dd
	 */
	public final static String getToday() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 获取今天(中文日期)
	 * 
	 * @author dyh 2009-10-24
	 * @return yyyy年MM月dd日
	 */
	public final static String getTodayInCN() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日");
		return df.format(date);
	}

	/**
	 * 获取今天(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @author dyh 2009-10-24
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public final static String getTodayFull() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 获取本日
	 * 
	 * @author dyh 2009-12-24
	 * @return
	 */
	public final static int getDay() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd");
		String day = df.format(date);
		return Integer.parseInt(day);
	}

	/**
	 * 输入Date，输出日期
	 * 
	 * @author dyh 2011-01-18
	 * @param date
	 *            YYYY-MM-DD
	 * @return
	 */
	public final static int getDay(String date) {
		return MyUtil.formatInt(date.substring(8));
	}

	/**
	 * 获取上月1日
	 * 
	 * @author dyh 2009-10-24
	 * @return
	 */
	public final static String getFirstDayInLastMonth() {
		return getFirstDayInLastMonth(getToday());
	}

	/**
	 * 获取上月最后一日
	 * 
	 * @author dyh 2010-08-26
	 * @return
	 */
	public final static String getLastDayInLastMonth() {
		return getLastDayInLastMonth(getToday());
	}

	/**
	 * 获取本月1日
	 * 
	 * @author dyh 2009-10-24
	 * @return
	 */
	public final static String getFirstDayInThisMonth() {
		return getFirstDayInMonth(getToday());
	}

	/**
	 * 获取本月最后一日
	 * 
	 * @author dyh 2010-08-26
	 * @return
	 */
	public final static String getLastDayInThisMonth() {
		return getLastDayInMonth(getToday());
	}

	/**
	 * 获取下月1日
	 * 
	 * @author dyh 2010-08-26
	 * @return
	 */
	public final static String getFirstDayInNextMonth() {
		return getFirstDayInNextMonth(getToday());
	}

	/**
	 * 获取下月最后一日
	 * 
	 * @author dyh 2010-08-26
	 * @return
	 */
	public final static String getLastDayInNextMonth() {
		return getLastDayInNextMonth(getToday());
	}

	/**
	 * 获取输入日期的上月1日
	 * 
	 * @author dyh 2010-03-21
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getFirstDayInLastMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		String lastDay = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.MONTH, -1);
			lastDay = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return lastDay.substring(0, 7) + "-01";
	}

	/**
	 * 获取输入日期的上月最后一日
	 * 
	 * @author dyh 2010-08-21
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getLastDayInLastMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date.substring(0, 7) + "-01"));
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 获取输入日期当月1日
	 * 
	 * @author dyh 2010-03-21
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getFirstDayInMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		return date.substring(0, 7) + "-01";
	}

	/**
	 * 获取输入日期当月最后一日
	 * 
	 * @author dyh 2010-08-17
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getLastDayInMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date.substring(0, 7) + "-01"));
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 获取输入日期的下月1日
	 * 
	 * @author dyh 2010-08-26
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getFirstDayInNextMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		String lastDay = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.MONTH, 1);
			lastDay = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return lastDay.substring(0, 7) + "-01";
	}

	/**
	 * 获取输入日期的下月最后一日
	 * 
	 * @author dyh 2010-08-21
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getLastDayInNextMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date.substring(0, 7) + "-01"));
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 获取今年第1天
	 * 
	 * @author dyh 2010-12-31
	 * @return
	 */
	public final static String getFirstDayInThisYear() {
		return getFirstDayInThisYear(getToday());
	}

	/**
	 * 获取输入日期的当年第1天
	 * 
	 * @author dyh 2010-12-31
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getFirstDayInThisYear(String date) {
		if (date == null || date.length() != 10)
			return "";
		return date.substring(0, 4) + "-01-01";
	}

	/**
	 * 输入X年第Y月，输出起始日期
	 * 
	 * @author dyh 2010-03-31
	 * @param year
	 *            年份
	 * @prama monthInYear 第Y月
	 * @param dates
	 *            输出起始日期
	 * @return
	 */
	public final static void getDayByMonthInYear(int year, int monthInYear,
			String dates[]) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(MyConstant.FIRSTDAY_WEEK);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, monthInYear - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = df.format(c.getTime());

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, monthInYear);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		dates[1] = df.format(c.getTime());
	}

	/**
	 * 获取输入日期的上月同一日
	 * 
	 * @author dyh 2010-03-21
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getSameDayInLastMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		String lastDay = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.MONTH, -1);
			lastDay = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return lastDay;
	}

	/**
	 * 获取输入日期的下月同一日
	 * 
	 * @author ycl 2012-10-9
	 * @param date
	 *            yyyy-mm-dd
	 * @return
	 */
	public final static String getSameDayInNextMonth(String date) {
		if (date == null || date.length() != 10)
			return "";
		String lastDay = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.MONTH, 1);
			lastDay = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return lastDay;
	}

	/**
	 * 按DateFormat格式返回今天
	 * 
	 * @author dyh 2009-10-24
	 * @param DateFormat
	 *            SimpleDateFormat格式
	 * @return 按DateFormat格式返回今天
	 */
	public final static String getToday(String DateFormat) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DateFormat);
		return df.format(date);
	}

	/**
	 * 输入Date，输出YYYY-MM-dd
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 *            YYYY-MM-DD
	 * @return YYYY-MM-dd
	 */
	public final static String getDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 输入Date，按DateFormat格式输出日期
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 * @param DateFormat
	 *            SimpleDateFormat格式
	 * @return SimpleDateFormat格式的日期
	 */
	public final static String getDate(Date date, String DateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(DateFormat);
		return df.format(date);
	}

	/**
	 * 获取上个月的今天
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 * @return YYYY-MM-dd
	 */
	public final static String getDayInLastMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return getDate(c.getTime());
	}

	/**
	 * 获取前七日的今天
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 * @return YYYY-MM-dd
	 */
	public final static String getDayInLastWeek() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.WEEK_OF_YEAR, -1);
		return getDate(c.getTime());
	}

	/**
	 * 获取今后七日
	 * 
	 * @author dyh 2009-11-24
	 * @param date
	 * @return YYYY-MM-dd
	 */
	public final static String getDayInNextWeek() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.WEEK_OF_YEAR, 1);
		return getDate(c.getTime());
	}

	/**
	 * 获取离days天的年月日
	 * 
	 * @author dyh 2009-10-24
	 * @param days
	 *            负数表示历史日期，正数表示未来日期
	 * @return YYYY-MM-dd
	 */
	public final static String getDate(int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, days);
		return getDate(c.getTime());
	}

	/**
	 * 获取输入日期过去days天的年月日
	 * 
	 * @author dyh 2010-03-21
	 * @param days
	 *            负数表示历史日期，正数表示未来日期
	 * @return YYYY-MM-dd
	 */
	public final static String getDate(int days, String date) {
		if (date == null || date.length() != 10)
			return "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(date));
			c.add(Calendar.DAY_OF_YEAR, days);
			date = df.format(c.getTime());
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 获取第二天(日期)
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 *            当前日期(YYYY-MM-DD)
	 * @return 第二天日期(YYYY-MM-DD)
	 */
	public final static String getTomorrow(String date) {
		if (date == null || date.length() != 10)
			return "";
		String nextDay = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			nextDay = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return nextDay;
	}

	/**
	 * 获取前一天(日期)
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 *            当前日期(YYYY-MM-DD)
	 * @return 前一天日期(YYYY-MM-DD)
	 */
	public final static String getYesterday(String date) {
		if (date == null || date.length() != 10)
			return "";
		String lastDay = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			lastDay = df.format(calendar.getTime());
		} catch (Exception e) {
		}
		return lastDay;
	}

	/**
	 * 获取前一天日期（YYYY年MM月DD日）
	 * 
	 * @author YOUCL 2011-1-14
	 * @return
	 */
	public final static String getYesterdayInCN() {
		return getYesterday(getToday());
	}

	/**
	 * 获取明天(日期)
	 * 
	 * @author dyh 2009-10-24
	 * @return 明天日期(YYYY-MM-DD)
	 */
	public final static String getTomorrow() {
		return getDate(1);
	}

	/**
	 * 获取昨天(日期)
	 * 
	 * @author dyh 2009-10-24
	 * @return 昨天日期(YYYY-MM-DD)
	 */
	public final static String getYesterday() {
		return getDate(-1);
	}

	/**
	 * 获取两个日期相隔天数(如果是同一天，则相隔为1。如果为次日，则为2，依次类推)
	 * 
	 * @author dyh 2010-08-05
	 * @param startdate
	 *            开始日期YYYY-MM-DD
	 * @param enddate
	 *            结束日期YYYY-MM-DD
	 * @return 两个日期相隔天数
	 */
	public final static int getDayInterval(String startdate, String enddate) {
		int interval = 0;
		if (!isStrNull(startdate) && !isStrNull(enddate)) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = df.parse(startdate);
				Date d2 = df.parse(enddate);
				long tmp = d2.getTime() - d1.getTime();
				interval = ((int) (tmp / 86400000)) + 1;
			} catch (Exception e) {
			}
		}
		return interval;
	}

	/**
	 * 获取输入日期所在月份的天数
	 * 
	 * @author ycl 2012-8-15
	 * @param date
	 * @return
	 */
	public static final int getDaysInMonth(String date) {
		String startdate = getFirstDayInMonth(date);
		String lastdate = getLastDayInMonth(date);
		return getDayInterval(startdate, lastdate);
	}

	/**
	 * 输入YYYY-MM-DD，输出YYYY年M月D日
	 * 
	 * @author dyh 2009-10-24
	 * @param date
	 *            YYYY-MM-DD
	 * @return YYYY年M月D日
	 */
	public final static String getDateInCN(String date) {
		if (date == null || date.length() != 10)
			return "";
		return date.substring(0, 4) + "年" + getRealNumber(date.substring(5, 7))
				+ "月" + getRealNumber(date.substring(8, 10)) + "日";
	}

	/**
	 * 输入YYYY-MM-DD，输出M月D日
	 * 
	 * @author dyh 2010-03-24
	 * @param date
	 *            YYYY-MM-DD
	 * @return M年D日
	 */
	public final static String getDateInCNNoYear(String date) {
		if (date == null || date.length() != 10)
			return "";
		return getRealNumber(date.substring(5, 7)) + "月"
				+ getRealNumber(date.substring(8, 10)) + "日";
	}

	/**
	 * 输入起止日期，如果起始日相同，则输出开始日期；如果同年，则输出：YYYY年M月D日-M月D日；如果不同年，则输出YYYY年M月D日-YYYY年M月D日
	 * 
	 * @author dyh 2009-10-24
	 * @param startdate
	 *            YYYY-MM-DD
	 * @param enddate
	 *            YYYY-MM-DD
	 * @return YYYY年M月D日
	 */
	public final static String getStartAndEndDate(String startdate,
			String enddate) {
		return getStartAndEndDate(startdate, enddate, true, true);
	}

	/**
	 * 输入起止日期，根据年月日相同情况，分别显示最少的信息。
	 * 
	 * @author dyh 2011-09-16
	 * @param startdate
	 *            YYYY-MM-DD
	 * @param enddate
	 *            YYYY-MM-DD
	 * @return YYYY年M月D日
	 */
	public final static String getStartAndEndDateInMonth(String startdate,
			String enddate) {
		return getStartAndEndDate(startdate, enddate, false, false);
	}

	/**
	 * 输入起止日期，根据年月日相同情况，分别显示最少的信息。比如：年相同，则不显示年。但无论如何至少显示月份
	 * 
	 * @author dyh 2011-09-16
	 * @param startdate
	 *            YYYY-MM-DD
	 * @param enddate
	 *            YYYY-MM-DD
	 * @return YYYY年M年D日
	 */
	public final static String getStartAndEndDateInShort(String startdate,
			String enddate) {
		return getStartAndEndDate(startdate, enddate, false, true);
	}

	/**
	 * 输入起止日期，根据年月日相同情况，合并相同的年月日，分别显示最少的信息。比如：年相同，则不显示年
	 * 
	 * @author dyh 2011-09-16
	 * @param startdate
	 *            YYYY-MM-DD
	 * @param enddate
	 *            YYYY-MM-DD
	 * @param showYear
	 *            如果起止日期年相同，是否显示年
	 * @param showMonth
	 *            如果起止日期年相同且月相同，是否显示月
	 * @return YYYY年M年D日
	 */
	public final static String getStartAndEndDate(String startdate,
			String enddate, boolean showYear, boolean showMonth) {
		if (startdate == null || enddate == null || startdate.length() != 10
				|| enddate.length() != 10)
			return "";
		int startyear = MyUtil.formatInt(startdate.substring(0, 4), 0);
		int startmonth = MyUtil.formatInt(startdate.substring(5, 7), 0);
		int startday = MyUtil.formatInt(startdate.substring(8), 0);
		int endyear = MyUtil.formatInt(enddate.substring(0, 4), 0);
		int endmonth = MyUtil.formatInt(enddate.substring(5, 7), 0);
		int endday = MyUtil.formatInt(enddate.substring(8), 0);
		StringBuilder dateOK = new StringBuilder();
		if (startyear == endyear) {// 如果年相同
			if (showYear) {
				dateOK.append(String.valueOf(startyear));
				dateOK.append("年");
			}
			if (startmonth == endmonth) {
				if (showMonth) {
					dateOK.append(String.valueOf(startmonth));
					dateOK.append("月");
				}
				dateOK.append(String.valueOf(startday));
				if (startday == endday) {

				} else {
					dateOK.append("-");
					dateOK.append(String.valueOf(endday));
				}
				dateOK.append("日");
			} else {
				dateOK.append(String.valueOf(startmonth));
				dateOK.append("月");
				dateOK.append(String.valueOf(startday));
				dateOK.append("日-");
				dateOK.append(String.valueOf(endmonth));
				dateOK.append("月");
				dateOK.append(String.valueOf(endday));
				dateOK.append("日");
			}
		} else {
			dateOK.append(String.valueOf(startyear));
			dateOK.append("年");
			dateOK.append(String.valueOf(startmonth));
			dateOK.append("月");
			dateOK.append(String.valueOf(startday));
			dateOK.append("日-");
			dateOK.append(String.valueOf(endyear));
			dateOK.append("年");
			dateOK.append(String.valueOf(endmonth));
			dateOK.append("月");
			dateOK.append(String.valueOf(endday));
			dateOK.append("日");
		}
		return dateOK.toString();
	}

	/**
	 * 输入起止日期，如果起始日相同，则输出开始日期；如果同年，则输出：YYYY年M月D日0时-M月D日24时；如果不同年，则输出YYYY年M月D日0时-YYYY年M月D日24时
	 * 
	 * @author dyh 2010-08-21
	 * @param startdate
	 *            YYYY-MM-DD
	 * @param enddate
	 *            YYYY-MM-DD
	 * @return YYYY年M年D日
	 */
	public final static String getValidDate(String startdate, String enddate) {
		String date = getStartAndEndDate(startdate, enddate);
		String validdate = "";
		if (!isStrNull(date)) {
			int index = date.indexOf("-");
			if (index >= 0) {
				validdate = date.substring(0, index) + "0时-"
						+ date.substring(index + 1) + "24时";
			} else {
				validdate = date + "0时-24时";
			}
		}
		return validdate;
	}

	/**
	 * 规格化日期：输入long，输出YYYY-MM-DD hh:mm:ss
	 * 
	 * @author dyh 2010-07-16
	 * @param date
	 *            时间值
	 * @return YYYY-MM-DD hh:mm:ss
	 */
	public static String long2DateStr(long d) {
		Date date = new java.util.Date(d);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	// 检查时间格式是否正确
	public static boolean checkDate(String strDate, String pattern) {
		boolean ok = false;
		if (strDate != null && strDate.length() >= pattern.length()) {
			try {
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
						pattern);
				df.setLenient(false);
				df.parse(strDate);
				ok = true;
			} catch (Exception e) {
				System.out.println("Common.java(checkDate) error");
			}
		}
		return ok;
	}

	/**
	 * 将YYYYMMDD格式化为YYYY-MM-DD，其中“-”可自定义
	 * 
	 * @param strDate
	 *            YYYYMMDD
	 * @param pattern
	 *            间隔符：-，或中文；否则原样返回
	 * @return
	 */
	public static String formatDate(String strDate, String pattern) {
		if ("-".equals(pattern))
			return strDate.substring(0, 4) + "-" + strDate.substring(4, 6)
					+ "-" + strDate.substring(6, 8);
		else if ("年月日".equals(pattern)) {
			String tmp1 = strDate.substring(4, 6);
			String tmp2 = strDate.substring(6, 8);
			return strDate.substring(0, 4) + "年"
					+ (tmp1.startsWith("0") ? tmp1.substring(1) : tmp1) + "月"
					+ (tmp2.startsWith("0") ? tmp2.substring(1) : tmp2) + "日";
		} else
			return strDate;
	}

	/**
	 * 日期比较，返回较小日期
	 * 
	 * @author youcl 2011-11-23
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String compareSmallDate(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (df.parse(date1).after(df.parse(date2)))
				return date2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}

	/** ***************** 1.2 日期方法结束：日 ************************** */

	/** ***************** 1.3 日期方法开始：周 ************************** */
	/**
	 * 规格化一周首日
	 * 
	 * @author dyh 2012-04-12
	 * @return
	 */
	public final static int formatFirstDayInWeek(int firstdayInWeek) {
		if (!(firstdayInWeek >= Calendar.SUNDAY && firstdayInWeek <= Calendar.SATURDAY)) {
			firstdayInWeek = MyConstant.FIRSTDAY_WEEK;
		}
		return firstdayInWeek;
	}

	/**
	 * 获取当前日期的周数
	 * 
	 * @author dyh 2010-03-31
	 * @return
	 */
	public final static int getWeekInYear() {
		return getWeekInYear(MyConstant.FIRSTDAY_WEEK, Calendar.getInstance());
	}

	/**
	 * 获取输入日期的周数
	 * 
	 * @author dyh 2011-11-19
	 * @param date
	 * @return
	 */
	public final static int getWeekInYear(String date) {
		return getWeekInYear(MyConstant.FIRSTDAY_WEEK, getCalendar(date));
	}

	/**
	 * 获取输入日期的周数
	 * 
	 * @author dyh 2012-4-12
	 * @param firstdayInWeek
	 * @param c
	 * @return
	 */
	public final static int getWeekInYear(int firstdayInWeek, Calendar c) {
		c.setFirstDayOfWeek(formatFirstDayInWeek(firstdayInWeek));
		int week = c.get(Calendar.WEEK_OF_YEAR);
		if (week == 1) {
			int month = c.get(Calendar.MONTH) + 1;
			if (month == 12) {
				week = MyUtil.getTotalWeeksInYear(c.get(Calendar.YEAR));
			}
		}
		return week;
	}

	/**
	 * 获取输入日期的年和周次
	 * 
	 * @author dyh 2012-7-15
	 * @param date
	 * @return
	 */
	public final static int[] getYearWeek(String date) {
		int[] yearweek = new int[2];
		if (!MyUtil.isStrNull(date)) {
			int year = MyUtil.formatInt(date.substring(0, 4));
			if (year > 0) {
				int week = getWeekInYear(date);
				yearweek[0] = year;
				yearweek[1] = week;
			}
		}
		return yearweek;
	}

	/**
	 * 获取今年总周数
	 * 
	 * @author dyh 2011-11-15
	 * @return
	 */
	public final static int getTotalWeeksInYear() {
		return getTotalWeeksInYear(getYear());
	}

	/**
	 * 输入年份，输出该年全年周数
	 * 
	 * @author dyh 2010-03-31
	 * @param year
	 *            年份
	 * @return
	 */
	public final static int getTotalWeeksInYear(int year) {
		return getTotalWeeksInYear(MyConstant.FIRSTDAY_WEEK, year);
	}

	/**
	 * 输入年份，输出该年全年周数
	 * 
	 * @author dyh 2012-4-12
	 * @param year
	 * @return
	 */
	public final static int getTotalWeeksInYear(int firstdayInWeek, int year) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(formatFirstDayInWeek(firstdayInWeek));
		c.set(year + 1, 0, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 输入X年第Y周，输出起止日期
	 * 
	 * @author dyh 2010-03-31
	 * @param year
	 *            年份
	 * @prama weekInYear 第Y周
	 * @return 起止日期
	 */
	public final static String[] getWeekInYear(int year, int weekInYear) {
		String dates[] = { "", "" };
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(MyConstant.FIRSTDAY_WEEK);
		c.set(Calendar.YEAR, year);
		c.set(year, 0, 1);
		int firstday = c.get(Calendar.DAY_OF_WEEK);
		c.set(Calendar.DAY_OF_YEAR, (7 - firstday + 2) + 7 * (weekInYear - 2));
		dates[0] = df.format(c.getTime());
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_YEAR, (7 - firstday + 1) + 7 * (weekInYear - 1));
		dates[1] = df.format(c.getTime());
		return dates;
	}

	/**
	 * 输入X年第Y周，输出起止日期
	 * 
	 * @author dyh 2010-03-31
	 * @param year
	 *            年份
	 * @prama weekInYear 第Y周
	 * @return 起止日期
	 */
	public final static String[][] getWeekInYear(int year, int startweek,
			int endweek) {
		int weekcount = endweek - startweek + 1;
		if (weekcount <= 0) {
			return null;
		}
		String[][] weeks = new String[weekcount][2];
		for (int i = 0; i < weekcount; i++) {
			weeks[i] = getWeekInYear(year, startweek + i);
		}
		return weeks;
	}

	/**
	 * 输入今天所在周的周一和周日
	 * 
	 * @author dyh 2011-11-07
	 * @return 起止日期
	 */
	public static String[] getWeek() {
		return getWeek(null, 0);
	}

	/**
	 * 输入日期输出该日期所在周的周一和周日
	 * 
	 * @author dyh 2011-11-07
	 * @param date
	 * @return 起止日期
	 */
	public static String[] getWeek(String date) {
		return getWeek(date, 0);
	}

	/**
	 * 输入周次,输出今天对应周次的周一与周日。
	 * 
	 * @author dyh 2011-11-09
	 * @param week
	 *            距离本周的周数。0表示本周，1表示下1周，-1表示上1周，依次类推
	 * @return 起止日期
	 */
	public static String[] getWeek(int week) {
		return getWeek(null, week);
	}

	/**
	 * 输入日期,周次,输出对应日期对应周次的周一与周日。若日期为空,则默认为当天
	 * 
	 * @author dyh 2011-11-09
	 * @param date
	 *            输入日期(缺省值为今天)
	 * @param week
	 *            距离输入日期所在周的周数。0表示本周，1表示下1周，-1表示上1周，依次类推
	 * @return 起止日期
	 */
	public static String[] getWeek(String date, int week) {
		return getWeek(MyConstant.FIRSTDAY_WEEK, date, week);
	}

	/**
	 * 输入日期,周次,输出对应日期对应周次的周一与周日。若日期为空,则默认为当天
	 * 
	 * @author dyh 2011-11-09
	 * @param firtdayInWeek
	 *            星期首日
	 * @param date
	 *            输入日期(缺省值为今天)
	 * @param week
	 *            距离输入日期所在周的周数。0表示本周，1表示下1周，-1表示上1周，依次类推
	 * @return 起止日期
	 */
	public static String[] getWeek(int firtdayInWeek, String date, int week) {
		String dates[] = { "", "" };
		if (MyUtil.isStrNull(date))
			date = MyUtil.getToday();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date formatdate = df.parse(date);
			Calendar c = Calendar.getInstance();
			c.setFirstDayOfWeek(firtdayInWeek);// 设置星期首日
			c.setTime(formatdate);// 传入日期值
			c.add(Calendar.WEEK_OF_MONTH, week);// 传入周次
			c.set(Calendar.DAY_OF_WEEK, firtdayInWeek);
			String startdate = df.format(c.getTime());

			int enddayInWeek = firtdayInWeek == Calendar.SUNDAY ? Calendar.SATURDAY
					: firtdayInWeek - 1;
			c.set(Calendar.DAY_OF_WEEK, enddayInWeek);
			String enddate = df.format(c.getTime());
			dates[0] = startdate;
			dates[1] = enddate;
		} catch (Exception e) {
		}
		return dates;
	}

	/**
	 * 判断今日是否是周末
	 * 
	 * @author dyh 2010-03-22
	 * @param true：周末,false：非周末
	 * @return
	 */
	public static boolean TodayIsWeekend() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return (day == 1 || day == 7);
	}

	/**
	 * 获取今日是周几
	 * 
	 * @author dyh 2011-10-25
	 * @return
	 */
	public static int getWeekDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 星期的中文表示
	 * 
	 * @author wmr 2012-12-5
	 * @param
	 * @return String
	 */
	public final static String getWeekDay_cn() {
		String str = "";
		int key = getWeekDay();
		switch (key) {
		case 1:
			str = "星期日";
			break;
		case 2:
			str = "星期一";
			break;
		case 3:
			str = "星期二";
			break;
		case 4:
			str = "星期三";
			break;
		case 5:
			str = "星期四";
			break;
		case 6:
			str = "星期五";
			break;
		case 7:
			str = "星期六";
			break;
		default:
			break;
		}
		return str;
	}

	/**
	 * 检测是否在设置的星期时间内，比如周一10点到周二17点
	 * 
	 * @author dyh 2011-11-14
	 * @return
	 */
	public static boolean inWeekDay(int firstDay, int firstTime, int lastDay,
			int lastTime) {
		boolean ok = false;
		Calendar now = Calendar.getInstance();
		int weekday = now.get(Calendar.DAY_OF_WEEK);// 今日
		if (weekday > firstDay && weekday < lastDay) {
			ok = true;
		} else if (weekday == firstDay || weekday == lastDay) {
			int hour = now.get(Calendar.HOUR_OF_DAY);
			if (firstDay != lastDay) {
				if ((weekday == firstDay && hour >= firstTime)
						|| (weekday == lastDay && hour < lastTime)) {
					ok = true;
				}
			} else {
				if (hour >= firstTime && hour < lastTime) {
					ok = true;
				}
			}
		}
		return ok;
	}

	/**
	 * 获取未来N周的起止日期集合
	 * 
	 * @author dyh 2011-11-14
	 * @param weekcount
	 *            未来N周
	 * @return 未来N周起止日期集合
	 */
	public static String[][] getWeeks(int weekcount) {
		if (weekcount <= 0)
			weekcount = 1;
		String dates[][] = new String[weekcount][2];
		for (int i = 1; i <= weekcount; i++) {
			dates[i - 1] = getWeek(i);
		}
		return dates;
	}

	/**
	 * 获取输入日期开始的N周的起止日期集合
	 * 
	 * @author dyh 2011-11-14
	 * @param firstdate
	 *            首日
	 * @param weekcount
	 *            未来N周
	 * @return 未来N周起止日期集合
	 */
	public static String[][] getWeeks(String firstdate, int weekcount) {
		if (weekcount <= 0)
			weekcount = 1;
		String dates[][] = new String[weekcount][2];
		for (int i = 0; i < weekcount; i++) {
			dates[i] = getWeek(firstdate, i);
		}
		return dates;
	}

	/**
	 * 获取未来N周的年和周次集合
	 * 
	 * @author dyh 2012-7-15
	 * @param weekcount
	 * @return
	 */
	public static int[][] getYearWeeks(int weekcount) {
		int yearweeks[][] = new int[weekcount][2];
		String dates[][] = getWeeks(weekcount);
		if (dates != null) {
			int temp[] = null;
			for (int i = 0; i < weekcount; i++) {
				temp = getYearWeek(dates[i][0]);
				if (temp == null) {
					break;
				}
				yearweeks[i][0] = temp[0];
				yearweeks[i][1] = temp[1];
			}
		}
		return yearweeks;
	}

	/** ***************** 1.3 日期方法结束：周 ************************** */

	/** ***************** 1.4 日期方法开始：月 ************************** */
	/**
	 * 获取本月
	 * 
	 * @author dyh 2009-10-24
	 * @return
	 */
	public final static int getMonth() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MM");
		String month = df.format(date);
		return Integer.parseInt(month);
	}

	/**
	 * 获取本月(YYYY-MM)
	 * 
	 * @author dyh 2011-12-06
	 * @return
	 */
	public final static String getYearMonth() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}

	/**
	 * 获取距离monthcount个月的月份(YYYY-MM)
	 * 
	 * @author dyh 2012-01-23
	 * @param monthcount
	 *            距离本月的月数，正数表示未来月份，负数表示过去月份
	 * @return
	 */
	public final static String getYearMonth(int monthcount) {
		int year = MyUtil.getYear();
		int month = MyUtil.getMonth();
		int newmonth = month + monthcount;
		int newyear = newmonth / 12 + year;
		if (newmonth <= 0 || (newmonth > 0 && newmonth % 12 == 0)) {
			newyear -= 1;
		}
		newmonth = newmonth % 12;
		if (newmonth <= 0) {
			newmonth += 12;
		}
		return newyear + "-" + MyUtil.getFixedNumber(newmonth, 2);
	}

	/**
	 * 输入日期，输出月份
	 * 
	 * @author dyh 2011-01-18
	 * @param date
	 *            日期(YYYY-MM-DD)
	 * @return
	 */
	public final static String getMonth(String date) {
		return date.substring(0, 7);
	}

	/**
	 * 输入终端公司报表使用的月份(从2011年7月起)
	 * 
	 * @author dyh 2011-12-02
	 * @return
	 */
	public final static Map<String, String> getMonthZD() {
		return getMonths("2011-07");
	}

	/**
	 * 输入从开始月到当前月的所有年月
	 * 
	 * @author dyh 2011-12-3
	 * @param startmonth
	 * @return
	 */
	public final static Map<String, String> getMonths(String startmonth) {
		return MyUtil.getMonth(startmonth, MyUtil.getMonth(MyUtil
				.getYesterday()));
	}

	/**
	 * 输入从开始月到上月的所有年月
	 * 
	 * @author ycl 2012-10-1
	 * @param startmonth
	 * @return
	 */
	public final static Map<String, String> getMonthsLastMonth(String startmonth) {
		return MyUtil.getMonth(startmonth, MyUtil.getLastMonth(MyUtil
				.getToday()));
	}

	/**
	 * 输入日期，输出该日为第几月
	 * 
	 * @author dyh 2011-01-18
	 * @param date
	 *            日期(YYYY-MM-DD)
	 * @return
	 */
	public final static int getMonthInt(String date) {
		return MyUtil.formatInt(date.substring(5, 7));
	}

	/**
	 * 输入年和月，输出YYYY-MM
	 * 
	 * @author dyh 2011-08-04
	 * @param date
	 *            YYYY-MM
	 * @return YYYY年M月
	 */
	public final static String getMonth(int year, int month) {
		return year + "-" + getFixedNumber(month, 2);
	}

	/**
	 * 输入起止月份，输出月份列表
	 * 
	 * @author dyh 2011-08-04
	 * @param startmonth
	 *            YYYY-MM
	 * @param endmonth
	 *            YYYY-MM
	 * @return 月份列表
	 */
	public final static Map<String, String> getMonth(String startmonth,
			String endmonth) {
		Map<String, String> hMonth = new LinkedHashMap<String, String>();
		int year_start = getYear(startmonth);
		int month_start = getMonthInt(startmonth);
		int year_end = getYear(endmonth);
		int month_end = getMonthInt(endmonth);

		int year = 0;
		int month = 0;
		int month_max = 12;
		String monthok = "";
		for (year = year_start, month = month_start; year <= year_end; year++) {
			if (year > year_start) {
				month = 1;
			}
			if (year == year_end) {
				month_max = month_end;
			} else {
				month_max = 12;
			}
			for (; month <= month_max; month++) {
				monthok = MyUtil.getMonth(year, month);
				hMonth.put(monthok, MyUtil.getMonthInCN(monthok));
			}
		}
		return hMonth;
	}

	/**
	 * 获取输入月份或日期的上一月
	 * 
	 * @author dyh 2011-09-16
	 * @param month
	 *            yyyy-mm
	 * @return
	 */
	public final static String getLastMonth(String month) {
		if (month == null)
			return "";
		String date = "";
		if (month.length() == 10) {
			date = month;
		} else if (month.length() == 7) {
			date = month + "-01";
		} else {
			return "";
		}
		return MyUtil.getFirstDayInLastMonth(date).substring(0, 7);
	}

	/**
	 * 获取输入月份或日期的上一月
	 * 
	 * @author dyh 2011-09-16
	 * @param month
	 *            yyyy-mm
	 * @return
	 */
	public final static String getLastMonthInCN(String month) {
		return getMonthInCN(getLastMonth(month));
	}

	/**
	 * 输入YYYY-MM，输出YYYY年M月
	 * 
	 * @author dyh 2011-08-04
	 * @param date
	 *            YYYY-MM
	 * @return YYYY年M月
	 */
	public final static String getMonthInCN(String month) {
		if (month == null || month.length() != 7)
			return "";
		return month.substring(0, 4) + "年" + getRealNumber(month.substring(5))
				+ "月";
	}

	/**
	 * 输入起止月份，输出这两个月份间的全部日数(比如输入2011-08和2011-10，则输出8月、9月和10月的天数，为31+30+31=92天)
	 * 
	 * @author dyh 2011-08-04
	 * @param startmonth
	 *            YYYY-MM
	 * @param endmonth
	 *            YYYY-MM
	 * @return
	 */
	public final static int getDayBetweenMonths(String startmonth,
			String endmonth) {
		return getDayInterval(startmonth + "-01", getLastDayInMonth(endmonth
				+ "-01")) + 1;
	}

	/**
	 * 比较两个月份
	 * 
	 * @author dyh 2012-11-23
	 * @param startmonth
	 *            YYYY-MM
	 * @param endmonth
	 *            YYYY-MM
	 * @return -1表示开始月份大，0表示两个月相同，1表示结束月份大
	 */
	public final static int compareMonths(String startmonth, String endmonth) {
		int interval = getDayInterval(startmonth + "-01", endmonth + "-01");
		int result = 0;
		if (interval > 1) {
			result = 1;
		} else if (interval < 1) {
			result = -1;
		}
		return result;
	}

	/**
	 * 获取指定年月起止月份数组,如输入2010,1,2010,2返回{'2010-01','2010-02'}
	 * 
	 * @author ycl 2010-05-28
	 * @param startyear
	 * @param startmonth
	 * @param endyear
	 * @param endmonth
	 * @return
	 */
	public final static String[] getStartEndMonthInYear(int startyear,
			int startmonth, int endyear, int endmonth) {
		String month[] = null;
		int row = getSpaceMonthInYear(startyear, startmonth, endyear, endmonth);
		month = new String[row];
		int step = 0;
		int space = endyear - startyear;
		String flag = "";
		if (space > 0) {
			for (int j = 0; j < space; j++) {
				for (int i = startmonth; i <= 12; i++) {
					if (i < 10)
						flag = "-0";
					else
						flag = "-";
					month[step] = startyear + flag + i;
					step++;
				}
				startmonth = 1;
				startyear++;
			}
			for (int i = 1; i <= endmonth; i++) {
				if (i < 10)
					flag = "-0";
				else
					flag = "-";
				month[step] = endyear + flag + i;
				step++;
			}
		} else {
			for (int i = startmonth; i <= endmonth; i++) {
				if (i < 10)
					flag = "-0";
				else
					flag = "-";
				month[step] = startyear + flag + i;
				step++;
			}
		}
		return month;
	}

	/**
	 * 获取指定日期起止月份数组,如输入2010-01-15,2010-02-15返回{'2010-01','2010-02'}
	 * 
	 * @author ycl 2012-8-16
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public final static String[] getStartEndMonths(String startdate,
			String enddate) {
		int startyear = MyUtil.getYear(startdate);
		int startmonth = MyUtil.getMonthInt(startdate);
		int endyear = MyUtil.getYear(enddate);
		int endmonth = MyUtil.getMonthInt(enddate);
		return getStartEndMonthInYear(startyear, startmonth, endyear, endmonth);
	}

	/**
	 * 获取指定年月相差的月份数,(如输入2009,1,2010,3返回15)
	 * 
	 * @author ycl 2010-05-28
	 * @param startyear
	 * @param startmonth
	 * @param endyear
	 * @param endmonth
	 * @return
	 */
	public final static int getSpaceMonthInYear(int startyear, int startmonth,
			int endyear, int endmonth) {
		int space = 0;
		if (endyear > startyear)
			space += (endyear - startyear) * 12;
		space += (endmonth - startmonth) + 1;
		return space;
	}

	/**
	 * 获取指定日期相差的月份数,(如输入2009-01-01,2010-03-01返回15)
	 * 
	 * @author ycl 2012-8-16
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public final static int getSpaceMonth(String startdate, String enddate) {
		int startyear = MyUtil.getYear(startdate);
		int startmonth = MyUtil.getMonthInt(startdate);
		int endyear = MyUtil.getYear(enddate);
		int endmonth = MyUtil.getMonthInt(enddate);
		return getSpaceMonthInYear(startyear, startmonth, endyear, endmonth);
	}

	/**
	 * 获取指定年月第一日,yyyy-MM-dd(如输入2010,1,返回2010-01-01)
	 * 
	 * @author ycl 2010-05-28
	 * @param year
	 *            年份
	 * @prama month 第Y月
	 * @return
	 */
	public final static String getFirstDayInMonth(int year, int month) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return df.format(c.getTime());
	}

	/**
	 * 获取指定年月最后一日,yyyy-MM-dd(如输入2010,1,返回2010-01-31)
	 * 
	 * @author ycl 2010-05-28
	 * @param year
	 *            年份
	 * @prama month 第Y月
	 * @return
	 */
	public final static String getLastDayInMonth(int year, int month) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return df.format(c.getTime());
	}

	/**
	 * 获取指定年月的首日和末日(yyyy-MM-dd)
	 * 
	 * @author dyh 2011-12-01
	 * @param year
	 *            年份
	 * @prama month 月份
	 * @return
	 */
	public final static String[] getDatesInMonth(int year, int month) {
		String dates[] = { getFirstDayInMonth(year, month),
				getLastDayInMonth(year, month) };
		return dates;
	}

	/**
	 * 获取指定日期的首日和末日(yyyy-MM-dd)
	 * 
	 * @author ycl 2012-8-16
	 * @param date
	 * @return
	 */
	public final static String[] getDatesInMonth(String date) {
		String dates[] = { getFirstDayInMonth(date), getLastDayInMonth(date) };
		return dates;
	}

	/**
	 * 获取两个日期间，各月的起始日期，如输入2012-01-15,2012-02-15,返回{{2012-01-01,2012-01-31},{2012-02-01,2012-02-29}}
	 * 
	 * @author ycl 2012-8-16
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public final static String[][] getDatesInMonth(String startdate,
			String enddate) {
		int interval = getSpaceMonth(startdate, enddate);
		String[][] dates = new String[interval][2];
		String tempdate = startdate;
		for (int i = 0; i < interval; i++) {
			dates[i] = getDatesInMonth(tempdate);
			tempdate = MyUtil.getFirstDayInNextMonth(tempdate);
		}
		return dates;
	}

	/**
	 * 输入起止月份，根据年月相同情况，合并相同的年月，分别显示最少的信息。比如：年相同，则不显示年
	 * 
	 * @author dyh 2011-12-23
	 * @param startmonth
	 *            YYYY-MM
	 * @param endmonth
	 *            YYYY-MM
	 * @param showYear
	 *            如果起止月份的年相同，是否显示年
	 * @return YYYY年M月
	 */
	public final static String getStartAndEndMonth(String startmonth,
			String endmonth, boolean showYear) {
		if (startmonth == null || endmonth == null || startmonth.length() != 7
				|| endmonth.length() != 7)
			return "";
		int startyear = MyUtil.formatInt(startmonth.substring(0, 4), 0);
		int startmon = MyUtil.formatInt(startmonth.substring(5, 7), 0);
		int endyear = MyUtil.formatInt(endmonth.substring(0, 4), 0);
		int endmon = MyUtil.formatInt(endmonth.substring(5, 7), 0);
		StringBuilder dateOK = new StringBuilder();
		if (startyear == endyear) {// 如果年相同
			if (showYear) {
				dateOK.append(String.valueOf(startyear));
				dateOK.append("年");
			}
			if (startmon == endmon) {
				dateOK.append(String.valueOf(startmon));
				dateOK.append("月");
			} else {
				dateOK.append(String.valueOf(startmon));
				dateOK.append("月-");
				dateOK.append(String.valueOf(endmon));
				dateOK.append("月");
			}
		} else {
			dateOK.append(String.valueOf(startyear));
			dateOK.append("年");
			dateOK.append(String.valueOf(startmon));
			dateOK.append("月-");
			dateOK.append(String.valueOf(endyear));
			dateOK.append("年");
			dateOK.append(String.valueOf(endmon));
			dateOK.append("月");
		}
		return dateOK.toString();
	}

	/**
	 * 输入起止月份，根据年月相同情况，合并相同的年月，分别显示最少的信息。比如：年相同，则不显示年
	 * 
	 * @author dyh 2011-12-23
	 * @param startmonth
	 *            YYYY-MM
	 * @param endmonth
	 *            YYYY-MM
	 * @param showYear
	 *            如果起止月份的年相同，是否显示年
	 * @return YYYY年M月
	 */
	public final static String getStartAndEndMonth(String startmonth,
			String endmonth) {
		return getStartAndEndMonth(startmonth, endmonth, true);
	}

	/** ***************** 1.4 日期方法结束：月 ************************** */

	/** ***************** 1.5 日期方法开始：季 ************************** */

	/**
	 * 输入日期，输出该日为本季度第几天
	 * 
	 * @author dyh 2011-01-18
	 * @param date
	 *            日期(YYYY-MM-DD)
	 * @return
	 */
	public final static int getDaysInQuarter(String date) {
		boolean isLeapYear = MyUtil.isLeapYear(MyUtil.getYear(date));
		int Year[] = isLeapYear ? MyParam.DAYS_IN_LEAPYEAR
				: MyParam.DAYS_IN_YEAR;
		int quarter = MyUtil.getQuarter(date);
		int startmonth = (quarter - 1) * 3;
		int endmonth = MyUtil.getMonthInt(date) - 1;
		int days = 0;
		for (int i = startmonth; i >= 0 && i < 12 && i < endmonth; i++) {
			days += Year[i];
		}
		return days + MyUtil.getDay(date);
	}

	/**
	 * 输入年份和第几季度，输出该季度共有多少天
	 * 
	 * @author dyh 2011-01-18
	 * @param year
	 *            年份(YYYY)
	 * @param quarter
	 *            第几个季度
	 * @return
	 */
	public final static int getDaysInQuarter(String year, int quarter) {
		boolean isLeapYear = MyUtil.isLeapYear(year);
		int Year[] = isLeapYear ? MyParam.DAYS_IN_LEAPYEAR
				: MyParam.DAYS_IN_YEAR;
		int startmonth = (quarter - 1) * 3 + 1;
		int endmonth = startmonth + 2;
		int days = 0;
		for (int i = startmonth - 1; i < endmonth; i++) {
			days += Year[i];
		}
		return days;
	}

	/**
	 * 输入日期，输出该日为第几季度
	 * 
	 * @author dyh 2011-01-18
	 * @param date
	 *            日期(YYYY-MM-DD)
	 * @return
	 */
	public final static int getQuarter(String date) {
		if (MyUtil.isStrNull(date) || date.length() != 10)
			return MyConstant.INT_NULL;
		else {
			return (MyUtil.getMonthInt(date) - 1) / 3 + 1;
		}
	}

	/**
	 * 输入日期，输出所在季度首日
	 * 
	 * @author dyh 2012-03-11
	 * @param date
	 *            日期(YYYY-MM-DD)
	 * @return
	 */
	public final static String getFirstDayInQuarter(String date) {
		if (MyUtil.isStrNull(date) || date.length() != 10)
			return "";
		else {
			int quarter = MyUtil.getQuarter(date);
			int year = MyUtil.getYear(date);
			return year + "-" + MyUtil.getFixedNumber((quarter - 1) * 3 + 1, 2)
					+ "-01";
		}
	}

	/** ***************** 1.5 日期方法结束：季 ************************** */

	/** ***************** 1.6 日期方法开始：年 ************************** */
	/**
	 * 获取今年
	 * 
	 * @author dyh 2009-10-24
	 * @return YYYY
	 */
	public final static int getYear() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(date);
		return Integer.parseInt(year);
	}

	/**
	 * 输入年份位数(4位或2位)，输出今年
	 * 
	 * @author dyh 2009-10-24
	 * @param n
	 *            (4位或2位),默认4位
	 * @return 根据n位数(4位或2位)输出今年
	 */
	public final static String getYear(int n) {
		if (!(n == 2 || n == 4))
			n = 4;
		Date date = new Date();
		String year = "";
		for (int i = 0; i < n; i++)
			year += "y";
		SimpleDateFormat df = new SimpleDateFormat(year);
		return df.format(date);
	}

	/**
	 * 输入日期，取出年份
	 * 
	 * @author dyh 2011-01-24
	 * @param date
	 *            YYYY-MM-DD或YYYY-MM
	 * @return 年份
	 */
	public final static int getYear(String date) {
		return MyUtil.formatInt(date.substring(0, 4));
	}

	/**
	 * 判断是否为闰年
	 * 
	 * @author dyh 2011-01-18
	 * @param year(YYYY)
	 * @return
	 */
	public final static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
	}

	/**
	 * 判断是否为闰年
	 * 
	 * @author dyh 2011-01-18
	 * @param year(YYYY)
	 * @return
	 */
	public final static boolean isLeapYear(String year) {
		return isLeapYear(MyUtil.formatInt(year));
	}

	/**
	 * 输入年份，输出该年全年天数
	 * 
	 * @author dyh 2010-04-28
	 * @param year
	 *            年份
	 * @return
	 */
	public final static int getDaysInFullYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31);
		return c.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 输入日期，输出该日为一年中的第几天
	 * 
	 * @author dyh 2011-01-18
	 * @param date
	 *            日期(YYYY-MM-DD)
	 * @return
	 */
	public final static int getDaysInYear(String date) {
		Calendar c = Calendar.getInstance();
		c.set(MyUtil.getYear(date), MyUtil.getMonthInt(date) - 1, MyUtil
				.getDay(date));
		return c.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取去年年份
	 * 
	 * @author YOUCL 2011-1-18
	 * @return
	 */
	public final static String getLastYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		return df.format(calendar.getTime());
	}

	/**
	 * 获取指定年月按月统计的起止日期二维数组,只能统计相邻两年
	 * 如输入2010,1,2010,2返回{{'2010-01-01','2010-01-31'},{'2010-02-01','2010-02-28'}}
	 * 
	 * @author ycl 2010-05-28
	 * @param startyear
	 * @param startmonth
	 * @param endyear
	 * @param endmonth
	 * @return
	 */
	public final static String[][] getStartEndDayInYear(int startyear,
			int startmonth, int endyear, int endmonth) {
		String arr[][] = null;
		int row = getSpaceMonthInYear(startyear, startmonth, endyear, endmonth);
		arr = new String[row][];
		int step = 0;
		if (endyear > startyear) {
			for (int i = startmonth; i <= 12; i++) {
				String dates[] = new String[2];
				getDayByMonthInYear(startyear, i, dates);
				arr[step] = dates;
				step++;
			}
			for (int i = 1; i <= endmonth; i++) {
				String dates[] = new String[2];
				getDayByMonthInYear(endyear, i, dates);
				arr[step] = dates;
				step++;
			}
		} else {
			for (int i = startmonth; i <= endmonth; i++) {
				String dates[] = new String[2];
				getDayByMonthInYear(startyear, i, dates);
				arr[step] = dates;
				step++;
			}
		}
		return arr;
	}

	/** ***************** 1.6 日期方法结束：年 ************************** */

	/** ***************** 1. 日期方法结束 ************************** */

	/** ***************** 2. 格式化数据类型方法开始 ********************** */

	/** ***************** 2.1 格式化数据类型方法开始：String ********************** */

	/**
	 * 输入文本s，如果不为null，则输出s；如果为null，则输出sub
	 * 
	 * @author dyh 2010-06-04
	 * @param s
	 *            要格式化的字符串
	 * @param sub
	 *            替代的字符串
	 * @return 如果不为null，则输出s；如果为null，则输出sub
	 */
	public static String formatStr(Object s, String sub) {
		return isStrNull(s) ? sub : s.toString().trim();
	}

	/**
	 * 输入文本s，如果不为null，则输出s；如果为null，则输出""
	 * 
	 * @author dyh 2010-06-04
	 * @param s
	 *            要格式化的字符串
	 * @return 如果不为null，则输出s；如果为null，则输出""
	 */
	public static String formatStr(Object s) {
		return formatStr(s, "");
	}

	/**
	 * 查找字符串中find的数量
	 * 
	 * @author dyh 2011-2-6
	 * @param source
	 *            字符串
	 * @param find
	 *            要查找的字符串
	 * @return find数量
	 */
	public static int matchStrCount(String source, String find) {
		if (MyUtil.isStrNull(source) || MyUtil.isStrNull(find))
			return 0;
		String pattern = "(" + find + "){1}";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		int i = 0;
		while (m.find()) {
			i++;
		}
		return i;
	}

	/**
	 * 将两个路径拼接
	 * 
	 * @author dyh 2011-2-6
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String joinPath(String path1, String path2) {
		path1 = MyUtil.formatStr(path1);
		path2 = MyUtil.formatStr(path2);
		if (path1.endsWith("/") || path1.endsWith("\\")) {
			if (path2.startsWith("/") || path2.startsWith("\\")) {
				path2 = path2.substring(1);
			}
		} else {
			if (!(path2.startsWith("/") || path2.startsWith("\\"))) {
				path2 = "/" + path2;
			}
		}
		return path1 + path2;
	}

	/**
	 * 按规定字数输出字符串，超长则使用省略号...
	 * 
	 * @author dyh 2009-10-24
	 * @param length
	 *            字符数(一个中文为2个字符)
	 * @param str
	 *            需要限制长度的字符串
	 * @return 按length输出字符串，超长则使用省略号...(最多三个".")
	 */
	public final static String getLimitedString(int length, String str) {
		if (str == null || length == 0)
			return "";
		StringBuilder buff = new StringBuilder(str);
		int buffLen = buff.length();// buff长度
		int realLen = 0;// 真实字符数(中文为2个字符)
		int tmpLen = 0;// 真实字符数(中文为2个字符)
		int CharLen[] = new int[3];// 最后三个字符的长度
		int tmpCharLen = 0;// 最后一个字的长度
		String stmp;
		int i = 0;
		for (; i < buffLen; i++) {
			stmp = buff.substring(i, i + 1);
			try {
				stmp = new String(stmp.getBytes("UTF-8"));
			} catch (Exception e) {

			}
			if (stmp.getBytes().length > 1)
				tmpCharLen = 2;// 双字节字符
			else
				tmpCharLen = 1;// 单字符
			tmpLen = realLen + tmpCharLen;
			if (tmpLen > length)
				break;
			CharLen[2] = CharLen[1];
			CharLen[1] = CharLen[0];
			CharLen[0] = tmpCharLen;
			realLen += tmpCharLen;
		}
		if (i == buffLen)// 如果str真实长度在length内,则不用添加省略号
			return str;
		else {
			int l = 0, j = 0, LenOK = 0;
			for (; j < CharLen.length && CharLen[j] != 0; j++) {
				l += CharLen[j];
				LenOK = (realLen - l);
				if ((realLen - l) <= (length - 3))
					break;
			}
			String point = "";
			for (int k = 0; k < length - LenOK; k++) {
				point += ".";
			}

			return (i - (j + 1)) <= 0 ? point
					: (buff.substring(0, i - (j + 1)) + point);
		}
	}

	/**
	 * 按规定字数输出字符串，超长时截断，但不用省略号
	 * 
	 * @author dyh 2009-10-24
	 * @param length
	 *            字符数(一个中文为2个字符)
	 * @param str
	 *            需要限制长度的字符串
	 * @return 按length输出字符串，超长时截断，但不用省略号
	 */
	public final static String getLimitedStringNoStop(int length, String str) {
		if (str == null || length == 0)
			return "";
		StringBuilder buff = new StringBuilder(str);
		int buffLen = buff.length();// buff长度
		int realLen = 0;// 真实字符数(中文为2个字符)
		int tmpCharLen = 0;// 字的长度
		String stmp;
		int i = 0;
		for (; i < buffLen; i++) {
			stmp = buff.substring(i, i + 1);
			try {
				stmp = new String(stmp.getBytes("UTF-8"));
			} catch (Exception e) {

			}
			if (stmp.getBytes().length > 1)
				tmpCharLen = 2;// 双字节字符
			else
				tmpCharLen = 1;// 单字符
			if ((realLen + tmpCharLen) > length)
				break;
			realLen += tmpCharLen;
		}
		return buff.substring(0, i);
	}

	/**
	 * 获取字符串长度(一个中文为2个字符)
	 * 
	 * @author dyh 2009-10-24
	 * @param str
	 *            字符串
	 * @return 字符数(一个中文为2个字符)
	 */
	public final static int getStringLength(String str) {
		if (str == null)
			return 0;

		StringBuilder buff = new StringBuilder(str);
		int length = 0;
		String stmp;
		for (int i = 0; i < buff.length(); i++) {
			stmp = buff.substring(i, i + 1);
			try {
				stmp = new String(stmp.getBytes("utf-8"));
			} catch (Exception e) {

			}
			// 判断是否为汉字
			if (stmp.getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	public static String convertStr(String str) {
		if (str == null) {
			str = "";
		} else {
			try {
				str = str.replaceAll("<", "&lt;");
				str = str.replaceAll(">", "&gt;");
				str = str.replaceAll("&nbsp;", "&nbsp;");
				str = str.replaceAll("\r\n", "<br>&nbsp;");
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return str;
	}

	/** ***************** 2.1 格式化数据类型方法结束：String ********************** */

	/** ***************** 2.2 格式化数据类型方法开始：int ********************** */

	/**
	 * 输入Integer对象，输出数值,如果为null，输出MyConstant.INT_NULL
	 * 
	 * @author dyh 2010-12-28
	 * @param i
	 *            Integer对象
	 * @return 输出数值,如果为null，输出MyConstant.INT_NULL
	 */
	public static int formatInt(Integer i) {
		return formatInt(i, MyConstant.INT_NULL);
	}

	/**
	 * 输入Integer对象，输出数值,如果为null，输出intNull
	 * 
	 * @author dyh 2010-12-28
	 * @param i
	 *            Integer对象
	 * @param intNull
	 *            缺省值
	 * @return 输出数值,如果为null，输出intNull
	 */
	public static int formatInt(Integer i, int intNull) {
		return i == null ? intNull : i.intValue();
	}

	/**
	 * 输入Object对象，输出int值，如果Object为null或异常情况，则输出MyConstant.INT_NULL
	 * 
	 * @author dyh 2010-12-28
	 * @param o
	 *            Object对象
	 * @return 输出int值，如果Object为null或异常情况，则输出MyConstant.INT_NULL
	 */
	public static int formatInt(Object o) {
		return formatInt(o, MyConstant.INT_NULL);
	}

	/**
	 * 输入Object对象，输出数值,如果为null，输出intNull
	 * 
	 * @author dyh 2010-12-28
	 * @param o
	 *            Object对象
	 * @param intNull
	 *            缺省值
	 * @return 输出数值,如果为null，输出intNull
	 */
	public static int formatInt(Object o, int intNull) {
		int i = intNull;
		if (o != null) {
			try {
				i = Integer.parseInt(o.toString());
			} catch (Exception ex) {

			}
		}
		return i;
	}

	/**
	 * 输入N位字符串型数字，输出高位不带0的字符串型数字 如：输入“01”，则输出“1”；输入“12”，则输出“12”
	 * 
	 * @author dyh 2009-10-24
	 * @param x
	 *            N位字符串型数字
	 * @return 高位不带0的字符串型数字
	 */
	public final static String getRealNumber(String x) {
		String result = x;
		if (MyUtil.isStrNull(result))
			return "";
		while (result.length() > 1 && result.startsWith("0")) {
			result = result.substring(1);
			getRealNumber(result);
		}

		// 移除0的小数
		int decimalIndex = result.indexOf(".");
		if (decimalIndex >= 0) {
			String decimal = result.substring(decimalIndex + 1);
			if (formatInt(decimal) <= 0) {
				result = result.substring(0, decimalIndex);
			}
		}
		return result;
	}

	/**
	 * 输入数字，输出长度为n的字符串型数字（高位补0）
	 * 
	 * @author dyh 2009-10-24
	 * @param x
	 *            数字
	 * @return 高位带0的字符串型数字
	 */
	public final static String getFixedNumber(int x, int n) {
		String xx = String.valueOf(x);
		int length = xx.length();
		StringBuilder s = new StringBuilder();
		for (int i = length; i < n; i++) {
			s.append("0");
		}
		s.append(xx);
		return s.toString();
	}

	/**
	 * 输入数字，输出长度为n的字符串型数字（高位补0）
	 * 
	 * @author dyh 2009-10-24
	 * @param x
	 *            数字
	 * @return 高位带0的字符串型数字
	 */
	public final static String getFixedNumber(Long x, int n) {
		String xx = x + "";
		int length = xx.length();
		StringBuilder s = new StringBuilder();
		for (int i = length; i < n; i++) {
			s.append("0");
		}
		s.append(xx);
		return s.toString();
	}

	/**
	 * 输入数字，返回个位数向0,5取整的数字
	 * 
	 * @author youcl 2012-5-8
	 * @param n
	 * @return 返回个位数向0,5取整的数字
	 */
	public final static int getRoundToFive(int n) {
		int add = n % 10;// 取个位数
		if (add < 3)
			add = 0;
		else if (add >= 3 && add <= 7)
			add = 5;
		else
			add = 10;
		n = n - n % 10 + add;
		return n;
	}

	/** ***************** 2.2 格式化数据类型方法结束：int ********************** */

	/** ***************** 2.3 格式化数据类型方法开始：double ********************** */

	/**
	 * 输入Double对象，输出double值,如果为null，输出MyConstant.DOUBLE_NULL
	 * 
	 * @author dyh 2010-12-28
	 * @param i
	 *            Double对象
	 * @return 输出数值,如果为null，输出MyConstant.INT_NULL
	 */
	public static double formatDouble(Double i) {
		return formatDouble(i, MyConstant.DOUBLE_NULL);
	}

	/**
	 * 输入Double对象，输出double值,如果为null，输出dNull
	 * 
	 * @author dyh 2010-12-28
	 * @param i
	 *            Double对象
	 * @param dNull
	 *            缺省值
	 * @return 输出数值,如果为null，输出dNull
	 */
	public static double formatDouble(Double i, int dNull) {
		return i == null ? dNull : i.doubleValue();
	}

	/**
	 * 对double按位数进行四舍五入，返回带digit位小数
	 * 
	 * @author dyh 2011-09-05
	 * @param d
	 * @param digit
	 *            小数点位数
	 * @return
	 */
	public final static double roundingDouble(double d, int digit) {
		int bit = 1;
		for (int i = 0; i < digit; i++) {
			bit *= 10;
		}
		boolean minus = d < 0;
		if (minus) {
			d = -d;
		}
		return (((long) (d * bit + 1.5)) - 1) * 1.0 / bit * (minus ? -1 : 1);
	}

	/**
	 * 规格化double，返回带bit位小数(不含逗号)
	 * 
	 * @author dyh 2009-10-24
	 * @param d
	 * @param bit
	 *            小数点位数
	 * @return 00.0
	 */
	public final static String formatDouble(double d, int bit) {
		String patern = "";
		if (bit <= 0) {
			bit = 0;
			patern = "0";
		} else {
			for (int i = 0; i < bit; i++) {
				patern += "#";
			}
			patern = "0." + patern;
		}
		DecimalFormat f = new DecimalFormat(patern);
		return f.format(roundingDouble(d, bit));
	}

	/**
	 * 输入Object对象，输出double值，如果Object为null或异常情况，则输出MyConstant.DOUBLE_NULL
	 * 
	 * @author dyh 2010-12-28
	 * @param o
	 *            Object对象
	 * @return 输出double值，如果Object为null或异常情况，则输出MyConstant.DOUBLE_NULL
	 */
	public static double formatDouble(Object o) {
		return formatDouble(o, MyConstant.DOUBLE_NULL);
	}

	/**
	 * 输入Object对象，输出double数值,如果为null，输出dNull
	 * 
	 * @author dyh 2010-12-28
	 * @param o
	 *            Object对象
	 * @param dNull
	 *            缺省值
	 * @return 输出数值,如果为null，输出dNull
	 */
	public static double formatDouble(Object o, double dNull) {
		double i = dNull;
		if (o != null) {
			try {
				i = Double.parseDouble(o.toString());
			} catch (Exception ex) {

			}
		}
		return i;
	}

	/**
	 * 是否包含小数
	 * 
	 * @author dyh 2009-10-24
	 * @param d
	 *            金额
	 * @return
	 */
	public final static boolean isDouble(double d) {
		return (((double) ((int) d)) != d);
	}

	/**
	 * 输入double金额，输出除税销售额(单位：万元,保留一位小数)
	 * 
	 * @author dyh 2012-06-15
	 * @param money
	 * @return
	 */
	public static String getTaxSales(double money) {
		return MyUtil.formatDouble(money / 1.17 / 10000, 1);
	}

	/** ***************** 2.3 格式化数据类型方法结束：double ********************** */

	/** ***************** 2.4 格式化数据类型方法开始：boolean ********************** */
	/**
	 * 判断字符串是否为空或长度为0
	 * 
	 * @author dyh 2010-07-21
	 * @param str
	 *            字符串
	 * @return 是否为空或长度为0
	 */
	public static boolean isStrNull(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断字符串是否为空或长度为0
	 * 
	 * @author dyh 2010-07-21
	 * @param str
	 *            字符串
	 * @return 是否为空或长度为0
	 */
	public static boolean isStrNull(Object str) {
		return str == null || str.toString().trim().length() == 0;
	}

	/**
	 * 输入0或1，输出"是"或"否"
	 * 
	 * @author dyh 2010-06-06
	 * @param flag
	 *            要格式化的字符串
	 * @return 输出"是"或"否"，如果flag为null，输出"否"
	 */
	public static String formatFlag(String flag) {
		return "1".equals(flag) ? "是" : "否";
	}

	/**
	 * 输入0或1，输出"是"或"否",如果flag为null，输出strNull
	 * 
	 * @author dyh 2010-06-06
	 * @param flag
	 *            要格式化的字符串
	 * @return 输出"是"或"否"，如果flag为null，输出strNull
	 */
	public static String formatFlag(String flag, String strNull) {
		return flag == null ? strNull : ("1".equals(flag) ? "是" : "否");
	}

	/** ***************** 2.4 格式化数据类型方法结束：boolean ********************** */

	/** ***************** 2.5 格式化数据类型方法开始：percent ********************** */

	/**
	 * 两个值对比：新数据比旧数据提升或下降了N%
	 * 
	 * @author dyh 2010-03-23
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static double getRate(int newdata, int olddata) {
		if (newdata < 0)
			newdata = 0;
		return (newdata - (olddata < 0 ? 0 : olddata)) * 1.0
				/ (olddata <= 0 ? 1 : olddata);
	}

	/**
	 * 两个值对比：新数据比旧数据提升或下降了N%
	 * 
	 * @author dyh 2010-03-23
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static String formatPercentRaise(int newdata, int olddata) {
		double rate = getRate(newdata, olddata);
		return (rate >= 0 ? MyConstant.SMS_UP : MyConstant.SMS_DOWN)
				+ MyUtil.getPercentByInt(rate >= 0 ? rate : -rate);
	}

	/**
	 * 两个值对比：新数据比旧数据提升或下降了N%
	 * 
	 * @author dyh 2012-03-18
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static double getRate(double newdata, double olddata) {
		if (newdata < 0)
			newdata = 0;
		return (newdata - (olddata < 0 ? 0 : olddata)) * 1.0
				/ (olddata <= 0 ? 1 : olddata);
	}

	/**
	 * 两个值对比：新数据比旧数据提升或下降了N%
	 * 
	 * @author dyh 2012-03-18
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static String formatPercentRaise(double newdata, double olddata) {
		double rate = getRate(newdata, olddata);
		return (rate >= 0 ? MyConstant.SMS_UP : MyConstant.SMS_DOWN)
				+ MyUtil.getPercentByInt(rate >= 0 ? rate : -rate);
	}

	/**
	 * 两个值相减：新数据比旧数据提升或下降了N个百分点
	 * 
	 * @author dyh 2010-03-23
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static String formatPercentSubtract(double newdata, double olddata) {
		double result = newdata - olddata;
		return (result >= 0 ? MyConstant.SMS_UP : MyConstant.SMS_DOWN)
				+ MyUtil.getPercentByInt(result >= 0 ? result : -result);
	}

	/**
	 * 两个值相减：新数据比旧数据提升或下降值
	 * 
	 * @author dyh 2012-03-05
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static String formatSubtract(int newdata, int olddata) {
		double result = newdata - olddata;
		return (result >= 0 ? MyConstant.SMS_UP : MyConstant.SMS_DOWN)
				+ Math.abs(newdata - olddata);
	}

	/**
	 * 两个值相减：新数据比旧数据提升或下降值
	 * 
	 * @author dyh 2012-03-05
	 * @param olddata
	 *            旧数据
	 * @param newdata
	 *            新数据
	 * @return 经规格化后的结果
	 */
	public static String formatSubtract(double newdata, double olddata) {
		double result = newdata - olddata;
		return (result >= 0 ? MyConstant.SMS_UP : MyConstant.SMS_DOWN)
				+ MyUtil.formatDouble(Math.abs(newdata - olddata), 1);
	}

	/**
	 * 百分比转为double
	 * 
	 * @author dyh 2012-08-22
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static double getPercent2Double(double data, double total) {
		if (total <= 0 || data < 0)
			return 0;
		return data * 1.0 / total * 1.0;
	}

	/**
	 * 百分比转为double
	 * 
	 * @author dyh 2012-08-22
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static double getPercent2Double(int data, int total) {
		return getPercent2Double(data * 1.0, total * 1.0);
	}

	/**
	 * 百分比转为int
	 * 
	 * @author dyh 2012-08-22
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static int getPercent2Int(double data, double total) {
		if (total <= 0 || data < 0)
			return 0;
		return formatInt(data * 1.0 / total * 1.0, 0);
	}

	/**
	 * 百分比转为int
	 * 
	 * @author dyh 2012-08-22
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static int getPercent2Int(int data, int total) {
		return getPercent2Int(data * 1.0, total * 1.0);
	}

	/**
	 * 两个值占比：部分数据占总数据N%,返回1位小数
	 * 
	 * @author dyh 2010-03-23
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static String getPercent(double data, double total) {
		return getPercent(getPercent2Double(data, total));
	}

	/**
	 * 两个值占比：部分数据占总数据N%,返回1位小数
	 * 
	 * @author ycl 2012-8-21
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static String getPercent(int data, int total) {
		return getPercent(data * 1.0, total * 1.0);
	}

	/**
	 * 两个值占比：部分数据占总数据N%
	 * 
	 * @author dyh 2010-03-23
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static String getPercentByInt(double data, double total) {
		return MyUtil.getPercentByInt(getPercent2Double(data, total));
	}

	/**
	 * 两个值占比：部分数据占总数据N%(返回带一位小数%)
	 * 
	 * @author dyh 2010-03-23
	 * @param data
	 *            部分数据
	 * @param total
	 *            总数据
	 * @return 经规格化后的结果
	 */
	public static String getPercentByInt(int data, int total) {
		return MyUtil.getPercentByInt(getPercent2Double(data, total));
	}

	/**
	 * 规格化百分数，返回整数(输入0.015，输出2)
	 * 
	 * @author dyh 2010-03-17
	 * @param d
	 *            实际值
	 * @return 00
	 */
	public final static String getPercentByInt(double d) {
		return getPercent(d, 0);
	}

	/**
	 * 规格化百分数，返回带一位小数金额(输入0.015，输出1.5)
	 * 
	 * @author dyh 2009-10-24
	 * @param d
	 *            实际值
	 * @return 00.0
	 */
	public final static String getPercent(double d) {
		return getPercent(d, 1);
	}

	/**
	 * 规格化百分数，返回带N位小数金额
	 * 
	 * @author dyh 2012-08-20
	 * @param d
	 *            实际值
	 * @param digit
	 *            百分比小数位
	 * @return
	 */
	public final static String getPercent(double d, int digit) {
		String patern = "";
		for (int i = 0; i < digit; i++) {
			patern += "#";
		}
		if (patern.length() > 0) {
			patern = "." + patern;
		}
		DecimalFormat f = new DecimalFormat("0" + patern);
		return f.format(roundingDouble(d * 100.0, digit));
	}

	/** ***************** 2.5 格式化数据类型方法结束：percent ********************** */

	/** ***************** 2.6 格式化数据类型方法开始：money ********************** */

	/**
	 * 规格化金额，返回带两位小数金额(可含逗号)
	 * 
	 * @author dyh 2009-10-24
	 * @param d
	 *            金额
	 * @return 00.00
	 */
	public final static String getMoneyHasComma(double d) {
		DecimalFormat f = new DecimalFormat("#,##0.00");
		return f.format(roundingDouble(d, 2));
	}

	/**
	 * 规格化金额，返回带两位小数金额(可含逗号)
	 * 
	 * @author dyh 2011-09-18
	 * @param d
	 *            金额
	 * @return 00.00
	 */
	public final static String getRealMoneyHasComma(double d) {
		String patern = "";
		if (isDouble(d))// 包括小数
			patern = "#,##0.00";
		else
			patern = "#,##0.##";
		DecimalFormat f = new DecimalFormat(patern);
		return f.format(roundingDouble(d, 2));
	}

	/**
	 * 规格化金额，返回带两位小数金额(不含逗号)
	 * 
	 * @author dyh 2009-10-24
	 * @param d
	 *            金额
	 * @return 00.00
	 */
	public final static String getMoney(double d) {
		String patern = "";
		if (isDouble(d))// 包括小数
			patern = "0.00";
		else
			patern = "0.##";
		DecimalFormat f = new DecimalFormat(patern);
		return f.format(roundingDouble(d, 2));
	}

	/** ***************** 2.6 格式化数据类型方法结束：money ********************** */

	/** ***************** 2. 格式化数据类型方法结束 ********************** */

	/** ***************** 99. 控件、网页、IMEI、路径相关方法开始 ************************** */
	/**
	 * 输入地市ID(非全省)，检测该ID是否合法
	 * 
	 * @author dyh 2010-09-09
	 * @param areaid
	 * @return
	 */
	// public static boolean checkAreaID(int areaid) {
	// return areaid > 0 && areaid < MyConstant.AREA_MAX;
	// }
	/**
	 * 输入地市ID(非全省)，检测该ID是否合法
	 * 
	 * @author dyh 2010-09-09
	 * @param areaid
	 * @return
	 */
	// public static boolean checkAreaID(String areaid) {
	// if (isStrNull(areaid))
	// return false;
	// else {
	// return checkAreaID(Integer.parseInt(areaid));
	// }
	// }
	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2012-5-4
	 * @param name
	 * @param type
	 *            num：纯数字；money:货币（数字和小数点）；string:数字字符串(允许首位为0)
	 * @param value
	 * @param size
	 * @param maxlength
	 * @param fromJS
	 *            是否来自js。如果是js，则需要把里面的单引号均使用双斜杠
	 * @return
	 */
	public static String getInputTag(String name, String id, String type,
			String value, int size, int maxlength, String onchange,
			boolean disabled, String onkeyup, boolean readonly, String color,
			boolean noborder, boolean fromJS) {
		StringBuilder html = new StringBuilder();
		if (MyUtil.isStrNull(name)) {
			name = "mydata";
		}
		if (MyUtil.isStrNull(type)) {
			type = "num";
		} else
			type = type.toLowerCase();

		if (size <= 0 && maxlength <= 0) {
			if (size <= 0)
				size = 5;
			if (maxlength <= 0)
				maxlength = 7;
		} else {
			if (size <= 0)
				size = maxlength;
			if (maxlength <= 0)
				maxlength = size;
		}
		String onKeyPress = "";
		if ("money".equals(type)) {
			onKeyPress = "numberOnly()";
		} else if ("string".equals(type)) {
			onKeyPress = "intOnly()";
		} else {
			onKeyPress = "intOnly()";
		}
		if (noborder) {
			readonly = true;
		}

		html.append("<input name=\"");
		html.append(name);
		html.append("\" id=\"");
		html.append(name);
		html.append("\" type=\"text\" size=\"");
		html.append(size);
		html.append("\" maxlength=\"");
		html.append(maxlength);
		html.append("\" value=\"");
		if (value != null && value.length() > 0) {
			value = value.trim();
			if ("money".equals(type)) {
				value = MyUtil.getMoney(Double.parseDouble(value));
			} else if ("string".equals(type)) {

			} else {
				value = MyUtil.getRealNumber(value);
			}
			html.append(value);
		}
		html.append("\" onKeyPress=\"");
		html.append(onKeyPress);
		html.append("\" ");
		if (!MyUtil.isStrNull(onchange)) {
			html.append(" onchange=\"");
			html.append(onchange);
			html.append("\"");
		}
		if (!MyUtil.isStrNull(onkeyup)) {
			html.append(" onkeyup=\"");
			html.append(onkeyup);
			html.append("\"");
		}
		html.append(" style=\"ime-mode:Disabled");
		if (!MyUtil.isStrNull(color)) {
			html.append(";color:");
			html.append(color);
		}
		if (noborder) {
			html
					.append(";border:0px;background-color:transparent;text-align:center");
		}
		html.append("\"");
		html
				.append(" onbeforepaste=\"clipboardData.setData(\'text\',clipboardData.getData(\'text\').replace(/[^\\d]/g,\'\'))\" ");

		if (disabled) {
			html.append(" disabled ");
		}
		if (readonly) {
			html.append(" readonly ");
		}
		html.append("/>");
		if (fromJS) {
			return html.toString().replaceAll("\'", "\\\\'");
		} else {
			return html.toString();
		}
	}

	public static String getInputTag(String name, String type, String value,
			int size, int maxlength, boolean fromJS) {
		return getInputTag(name, name, type, value, size, maxlength, "", false,
				"", false, "", false, fromJS);
	}

	public static String getInputTag(String name, String type, String value) {
		return getInputTag(name, name, type, value, -1, -1, "", false, "",
				false, "", false, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author shb 2012-11-3
	 * @param name
	 * @param type
	 * @param value
	 * @param readonly
	 * @return
	 */
	public static String getInputTag(String name, int value, String onchange,
			boolean readonly) {
		return getInputTag(name, name, null, String.valueOf(value), -1, -1,
				onchange, false, null, readonly, null, false, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-10-28
	 * @param name
	 *            控件名称
	 * @return
	 */
	public static String getInputTag(String name) {
		return getInputTag(name, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-10-28
	 * @param name
	 *            控件名称
	 * @return
	 */
	public static String getInputTag(String name, String type) {
		return getInputTag(name, type, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-12-07
	 * @param name
	 *            控件名称
	 * @param value
	 *            值
	 * @return
	 */
	public static String getInputTag(String name, int value) {
		return getInputTag(name, null, String.valueOf(value), -1, -1, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-12-07
	 * @param name
	 *            控件名称
	 * @param value
	 *            值
	 * @return
	 */
	public static String getInputTag(String name, int value, String onchange) {
		return getInputTag(name, name, null, String.valueOf(value), -1, -1,
				onchange, false, null, false, null, false, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-12-07
	 * @param name
	 *            控件名称
	 * @param value
	 *            值
	 * @return
	 */
	public static String getInputTag(String name, double value) {
		return getInputTag(name, "money", String.valueOf(value), -1, -1, false);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-10-28
	 * @param name
	 *            控件名称
	 * @return
	 */
	public static String getInputTag(String name, boolean fromJS) {
		return getInputTag(name, null, null, -1, -1, fromJS);
	}

	/**
	 * 获取数字输入框HTML
	 * 
	 * @author dyh 2011-10-28
	 * @param name
	 *            控件名称
	 * @return
	 */
	public static String getInputTag(String name, String type, boolean fromJS) {
		return getInputTag(name, type, null, -1, -1, fromJS);
	}

	/**
	 * 获取日期HTML
	 * 
	 * @author dyh 2012-4-28
	 * @param name
	 * @param value
	 * @param date
	 * @param interval
	 * @return
	 */
	public static String getDateTag(String name, String value, String date,
			int interval) {
		if (MyUtil.isStrNull(name)) {
			name = "mydate";
		}
		if (MyUtil.isStrNull(value)) {
			if (date == null)
				value = MyUtil.getToday();
			else {
				date = date.toUpperCase();
				if (date.equals("LASTMONTH")) {// 上个月的今天
					value = MyUtil.getDayInLastMonth();
				} else if (date.equals("LASTWEEK")) {// 一周前
					value = MyUtil.getDayInLastWeek();
				} else if (date.equals("YESTERDAY")) {// 昨天
					value = MyUtil.getYesterday();
				} else if (date.equals("TOMORROW")) {// 明天
					value = MyUtil.getTomorrow();
				} else if (date.equals("NEXTWEEK")) {// 一周后
					value = MyUtil.getDayInNextWeek();
				} else if (date.equals("TODAY")) {// 今天
					value = MyUtil.getToday();
				} else if (date.equals("FIRSTDAYINTHISMONTH")) {// 本月1日
					value = MyUtil.getFirstDayInThisMonth();
				} else if (date.equals("LASTDAYINTHISMONTH")) {// 本月最后一日
					value = MyUtil.getLastDayInThisMonth();
				} else if (date.equals("FIRSTDAYINLASTMONTH")) {// 上月1日
					value = MyUtil.getFirstDayInLastMonth();
				} else if (date.equals("LASTDAYINLASTMONTH")) {// 上月最后一日
					value = MyUtil.getLastDayInLastMonth();
				} else if (date.equals("FIRSTDAYINNEXTMONTH")) {// 下月1日
					value = MyUtil.getFirstDayInNextMonth();
				} else if (date.equals("LASTDAYINNEXTMONTH")) {// 下月最后一日
					value = MyUtil.getLastDayInNextMonth();
				} else if (date.equals("FIRSTDAYINTHISYEAR")) {// 本年1日
					value = MyUtil.getFirstDayInThisYear();
				}
				if (MyUtil.isStrNull(value))
					value = MyUtil.getToday();
			}
			if (interval != 0) {
				value = MyUtil.getDate(interval, value);
			}
		}
		StringBuilder html = new StringBuilder();
		html.append("<input style=\"cursor:pointer\" name=\"");
		html.append(name);
		html.append("\"	id=\"");
		html.append(name);
		html.append("\" type=\"text\" size=\"10\" value=\"");
		html.append(value);
		html
				.append("\"  readonly=\"true\" /><script type=\"text/javascript\">");
		html.append("Calendar.setup({inputField : \"");
		html.append(name);
		html.append("\",ifFormat:\"%Y-%m-%d\",showsTime:false,button:\"");
		html.append(name);
		html.append("\",singleClick:false,step:1});</script>");
		return html.toString();
	}

	/**
	 * 获取日期HTML
	 * 
	 * @author dyh 2012-4-28
	 * @param name
	 * @param value
	 * @param date
	 * @param interval
	 * @return
	 */
	public static String getDateTag(String name, String value) {
		return getDateTag(name, value, null, 0);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param id
	 * @param firstoption
	 * @param firstvalue
	 * @param onchange
	 * @param selected
	 * @param disabled
	 * @param map
	 * @param group
	 *            用`间隔
	 * @param tip
	 *            下拉框文字说明(不要出现冒号,系统会自己添加)
	 * @return
	 */
	public static String getSelectTag(String name, String id,
			String firstoption, String firstvalue, String onchange,
			String selected, boolean disabled, Map<String, String> map,
			String group, String tip) {
		boolean hasFirstOption = true;
		int mapsize = 0;
		String groups[] = null;
		int groupsize = 0;

		if (MyUtil.isStrNull(name)) {
			name = "myselect";
		}
		if (map != null) {
			mapsize = map.size();
		}
		if (mapsize == 0 && !MyUtil.isStrNull(group)) {
			groups = group.split("`");
			groupsize = groups.length;
			if (groupsize > 0) {
				if (groupsize % 2 != 0) {// 如果不是成对出现，则无效
					groupsize = 0;
				}
			}
			map = new LinkedHashMap<String, String>();
			for (int i = 0; i < groupsize; i += 2) {
				map.put(groups[i], groups[i + 1]);
			}
			mapsize = map.size();
		}
		StringBuilder html = new StringBuilder();
		if (!MyUtil.isStrNull(tip)) {
			html.append(tip);
			html.append("：");
		}
		html.append("<select name=\"");
		html.append(name);
		html.append("\"	id=\"");
		if (id != null && id.length() > 0)
			html.append(id);
		else
			html.append(name);
		html.append("\" ");
		if (onchange != null && onchange.length() > 0) {
			html.append("onchange=\"");
			html.append(onchange);
			html.append("\"");
		}
		if (disabled)// 禁用
			html.append(" disabled ");
		html.append(">");
		if (MyConstant.SELECT_FIRST_OPTION_NO.equals(firstoption)) {
			hasFirstOption = false;
		} else if (MyConstant.SELECT_FIRST_OPTION_YES_OR_NO_ALL
				.equals(firstoption)
				|| MyConstant.SELECT_FIRST_OPTION_YES_OR_NO_SELECT
						.equals(firstoption)) {
			if (mapsize == 1 || (mapsize == 0 && groupsize == 2)) {
				hasFirstOption = false;
			} else {
				firstoption = MyConstant.SELECT_FIRST_OPTION_YES_OR_NO_ALL
						.equals(firstoption) ? "全部" : "请选择";
			}
		}
		if (hasFirstOption) {
			if (MyUtil.isStrNull(firstoption))
				firstoption = MyConstant.SELECT_FIRST_OPTION;

			if (MyUtil.isStrNull(firstvalue))
				firstvalue = MyConstant.SELECT_FIRST_VALUE;

			html.append("<option value=\"");
			html.append(firstvalue);
			html.append("\">");
			html.append(firstoption);
			html.append("</option>");
		}
		if (mapsize > 0) {
			Iterator<Map.Entry<String, String>> iterator = map.entrySet()
					.iterator();
			String key = null;
			String value = null;
			Map.Entry<String, String> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				key = entry.getKey();
				value = entry.getValue();
				html.append("<option value=\"");
				html.append(key);
				html.append("\" ");
				if (!MyUtil.isStrNull(selected) && key != null
						&& key.equals(selected))
					html.append("selected");
				html.append(">");
				html.append(value);
				html.append("</option>");
			}
		}
		html.append("</select>");
		return html.toString();
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param firstoption
	 * @param firstvalue
	 * @param onchange
	 * @param selected
	 * @param map
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, String firstoption,
			String firstvalue, String onchange, String selected,
			Map<String, String> map, String tip) {
		return getSelectTag(name, null, firstoption, firstvalue, onchange,
				selected, false, map, null, tip);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param firstoption
	 * @param firstvalue
	 * @param onchange
	 * @param selected
	 * @param group
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, String firstoption,
			String firstvalue, String onchange, String selected, String group,
			String tip) {
		return getSelectTag(name, null, firstoption, firstvalue, onchange,
				selected, false, null, group, tip);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param selected
	 * @param map
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, String selected,
			Map<String, String> map, String tip) {
		return getSelectTag(name, null, null, null, selected, map, tip);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param selected
	 * @param group
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, String selected,
			String group, String tip) {
		return getSelectTag(name, null, null, null, selected, group, tip);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param selected
	 * @param map
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, String firstoption,
			String selected, Map<String, String> map) {
		return getSelectTag(name, firstoption, null, null, selected, map, null);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param selected
	 * @param group
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, String group,
			String firstoption) {
		return getSelectTag(name, firstoption, null, null, null, group, null);
	}

	/**
	 * 获取下拉框HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param name
	 * @param selected
	 * @param group
	 * @param tip
	 * @return
	 */
	public static String getSelectTag(String name, Map<String, String> map,
			String firstoption) {
		return getSelectTag(name, firstoption, null, null, null, map, null);
	}

	/**
	 * 获取单/多选框HTML
	 * 
	 * @author dyh 2012-5-5
	 * @param isCheckBox
	 *            是否为多选框
	 * @param name
	 * @param group
	 * @param map
	 * @param selected
	 *            如果是checkbox，用英文逗号间隔
	 * @param tip
	 * @return
	 */
	public static String getRadioAndCheckBoxTag(boolean isCheckBox,
			String name, String group, Map<String, String> map,
			String selected, String tip) {
		return getRadioAndCheckBoxTag(isCheckBox, name, group, map, selected,
				tip, "<br/>");
	}

	public static String getRadioAndCheckBoxrTag(boolean isCheckBox,
			String name, String group, Map<String, String> map,
			String selected, String tip) {
		return getRadioAndCheckBoxTag(isCheckBox, name, group, map, selected,
				tip);
	}

	/**
	 * 获取单/多选框HTML
	 * 
	 * @author ycl 2012-10-3
	 * @param isCheckBox
	 *            是否为多选框
	 * @param name
	 * @param group
	 * @param map
	 * @param selected
	 *            如果是checkbox，用英文逗号间隔
	 * @param tip
	 * @param split
	 *            分隔符，默认为<br/> ;
	 * @return
	 */
	public static String getRadioAndCheckBoxTag(boolean isCheckBox,
			String name, String group, Map<String, String> map,
			String selected, String tip, String split) {
		int mapsize = 0;
		String groups[] = null;
		int groupsize = 0;

		if (MyUtil.isStrNull(name)) {
			name = "mybox";
		}
		if (MyUtil.isStrNull(split)) {
			split = "<br/>";
		}
		if (map != null) {
			mapsize = map.size();
		}
		if (mapsize == 0 && !MyUtil.isStrNull(group)) {
			groups = group.split("`");
			groupsize = groups.length;
			if (groupsize > 0) {
				if (groupsize % 2 != 0) {// 如果不是成对出现，则无效
					groupsize = 0;
				}
			}
			map = new LinkedHashMap<String, String>();
			for (int i = 0; i < groupsize; i += 2) {
				map.put(groups[i], groups[i + 1]);
			}
			mapsize = map.size();
		}
		StringBuilder html = new StringBuilder();
		if (!MyUtil.isStrNull(tip)) {
			html.append(tip);
			html.append("：");
		}
		if (mapsize > 0) {
			String type = isCheckBox ? "checkbox" : "radio";
			Iterator<Map.Entry<String, String>> iterator = map.entrySet()
					.iterator();
			Object key = null;
			Object value = null;
			Map.Entry<String, String> entry = null;
			int i = 0;
			while (iterator.hasNext()) {
				entry = iterator.next();
				key = entry.getKey();
				value = entry.getValue();
				html.append("<input name=\"");
				html.append(name);
				html.append("\" type=\"");
				html.append(type);
				html.append("\" value=\"");
				html.append(key);
				html.append("\" ");
				if (isCheckBox) {
					if ((selected + ",").indexOf(key + ",") >= 0) {
						html.append("checked");
					}
				} else {
					if (!MyUtil.isStrNull(selected) && key.equals(selected)) {
						html.append("checked");
					} else if (i == 0) {
						html.append("checked");
					}
				}
				html.append("/>");
				html.append(value);
				html.append(split);
				i++;
			}
		}
		return html.toString();
	}

	/**
	 * 获取单选框HTML
	 * 
	 * @author dyh 2012-5-5
	 * @param name
	 * @param group
	 * @return
	 */
	public static String getRadioTag(String name, String group) {
		return getRadioAndCheckBoxTag(false, name, group, null, "", "");
	}

	/**
	 * 获取单选框HTML
	 * 
	 * @author dyh 2012-5-5
	 * @param name
	 * @param map
	 * @return
	 */
	public static String getRadioTag(String name, Map<String, String> map) {
		return getRadioAndCheckBoxTag(false, name, "", map, "", "");
	}

	/**
	 * 获取多选框HTML
	 * 
	 * @author dyh 2012-5-5
	 * @param name
	 * @param group
	 * @return
	 */
	public static String getCheckBoxTag(String name, String group) {
		return getRadioAndCheckBoxTag(true, name, group, null, "", "");
	}

	/**
	 * 获取多选框HTML
	 * 
	 * @author dyh 2012-5-5
	 * @param name
	 * @param map
	 * @return
	 */
	public static String getCheckBoxTag(String name, Map<String, String> map) {
		return getRadioAndCheckBoxTag(true, name, "", map, "", "");
	}

	/**
	 * 输入字符串，输出带
	 * <p>
	 * 的段落HTML，用于页面显示
	 * 
	 * @author dyh 2009-10-24
	 * @param str
	 *            需要格式化的字符串
	 * @return 输出带
	 *         <p>
	 *         的段落HTML
	 */
	public final static String getParagraph(String str) {
		if (str == null || str.length() == 0)
			return "<p>&nbsp;</p>";
		str = str.replaceAll("\r", "</p><p>");
		str = str.replaceAll(" ", "&nbsp");
		return "<p>" + str + "</p>";
	}

	/**
	 * 输入一个数组，输出已合并上下同类项的数组，用于JSP页面的rowspan 例如：rowspan=3，表示只处理合并前三个字段
	 * 输入以下数组content： 第0行：联合康康,A,10,xx,yy 第1行：联合康康,A,10,xx,yy
	 * 第2行：联合康康,A,20,xx,yy 第3行：联合康康,A,20,xx,yy 第4行：叶氏兄弟,A,30,xx,yy
	 * 第5行：叶氏兄弟,A,30,xx,yy 输出数组规则：从第0列开始处理，对于第i行第j列元素M[i][j]来说，
	 * 如果M[i+1][j]等于M[i][j]，则合并此列，表示此列的rowspan的元素M[i][j+1]加1： 第0行：联合康康, A,
	 * 10,xx,yy, 4, 4, 2 第1行： null,null,null,xx,yy,null,null,null 第2行：
	 * null,null, 20,xx,yy,null,null, 2 第3行： null,null,null,xx,yy,null,null,null
	 * 第4行：叶氏兄弟, A, 30,xx,yy, 2, 2, 2 第5行： null,null,null,xx,yy,null,null,null
	 * 
	 * @author dyh 2009-10-24
	 * @param content
	 *            要处理的数组(此参数也为输出参数，当出现重复字段时，置为null)
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=0，表示不考虑从头合并。如果needRowspanCols=-1，表示数组所有列都需要考虑从头开始合并，当出现无法合并的列时，剩余字段不再合并。
	 * @param needRowspanLastCols
	 *            后N列需要合并同类项，如果needRowspanLastCols=0，表示不考虑从尾合并。当needRowspanLastCols=-1，表示数组所有列都需要考虑从尾开始合并，当出现无法合并的列时，剩余字段不再合并。
	 * @return 输出rowspan数组
	 */
	public static int[][] getRowspanArray(List<String[]> content,
			int needRowspanCols, int needRowspanLastCols) {
		if (content == null)
			return null;
		int rowcount = content.size();// 行数
		if (rowcount == 0)
			return null;
		int colcount = content.get(0).length;// 列数
		if (colcount == 0)
			return null;

		if (needRowspanCols == -1 || needRowspanCols > colcount)
			needRowspanCols = colcount;

		if (needRowspanLastCols == -1 || needRowspanLastCols > colcount)
			needRowspanLastCols = colcount;

		int[][] arrRowspan = new int[rowcount][colcount];
		String[] oldRow = new String[colcount];
		int row = 0, col = 0;
		int currentRow[] = new int[colcount];

		// 从头开始检测可合并列
		if (needRowspanCols > 0) {
			for (String[] contentRow : content) {
				if (row == 0) {
					for (col = 0; col < needRowspanCols; col++) {
						oldRow[col] = contentRow[col];
					}
					row++;
					continue;
				}
				for (col = 0; col < needRowspanCols; col++) {
					if (oldRow[col] == null)
						continue;
					if (oldRow[col].equals(contentRow[col])) {
						if (col > 0 && contentRow[col - 1] != null) {
							arrRowspan[currentRow[col]][col] = row
									- currentRow[col];
							currentRow[col] = row;
							oldRow[col] = contentRow[col];
						} else {
							if (row == (rowcount - 1))// 最后一行
								arrRowspan[currentRow[col]][col] = row
										- currentRow[col] + 1;
							contentRow[col] = null;
						}
					} else {
						arrRowspan[currentRow[col]][col] = row
								- currentRow[col];
						currentRow[col] = row;
						oldRow[col] = contentRow[col];
					}
				}
				row++;
			}
		}

		// 从尾开始检测可合并列
		if (needRowspanLastCols > 0) {
			for (int i = 0; i < colcount; i++) {
				currentRow[i] = 0;
			}
			row = 0;
			for (String[] contentRow : content) {
				if (row == 0) {
					for (col = colcount - 1; col >= colcount
							- needRowspanLastCols; col--) {
						oldRow[col] = contentRow[col];
					}
					row++;
					continue;
				}

				for (col = colcount - 1; col >= colcount - needRowspanLastCols; col--) {
					if (oldRow[col] == null)
						continue;
					if (oldRow[col].equals(contentRow[col])) {
						if (col < colcount - 1 && contentRow[col + 1] != null) {
							arrRowspan[currentRow[col]][col] = row
									- currentRow[col];
							currentRow[col] = row;
							oldRow[col] = contentRow[col];
						} else {
							if (row == (rowcount - 1))// 最后一行
								arrRowspan[currentRow[col]][col] = row
										- currentRow[col] + 1;
							contentRow[col] = null;
						}
					} else {
						arrRowspan[currentRow[col]][col] = row
								- currentRow[col];
						currentRow[col] = row;
						oldRow[col] = contentRow[col];
					}
				}
				row++;
			}

		}
		return arrRowspan;
	}

	public static int[][] getRowspanArray(List<String[]> content,
			int needRowspanCols) {
		return getRowspanArray(content, needRowspanCols, 0);

	}

	/**
	 * 输入一个数组，输出已合并上下同类项的表格tr和td
	 * 
	 * @author dyh 2010-07-24
	 * @param content
	 *            要处理的数组,每一行
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=-1，表示数组所有列都需要考虑合并。不包括行数i字段
	 * @param trStyle
	 *            tr样式(缺省值为center)
	 * @prama tdStyle td样式(可以为空)
	 * @return 输出已合并上下同类项的表格tr和td
	 */
	public static String getRowspanArray2HTML(List<String[]> content,
			int needRowspanCols) {
		return getRowspanArray2HTML(content, needRowspanCols, 0, "", null, -1);
	}

	/**
	 * 输入一个数组，输出已合并上下同类项的表格tr和td
	 * 
	 * @author dyh 2010-07-24
	 * @param content
	 *            要处理的数组,每一行
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=-1，表示数组所有列都需要考虑合并。不包括行数i字段
	 * @param trStyle
	 *            tr样式(缺省值为center)
	 * @prama tdStyle td样式(可以为空)
	 * @return 输出已合并上下同类项的表格tr和td
	 */
	public static String getRowspanArray2HTML(List<String[]> content,
			int needRowspanCols, int startRowIndex) {
		return getRowspanArray2HTML(content, needRowspanCols, 0, "", null,
				startRowIndex);
	}

	/**
	 * 输入一个数组，输出已合并上下同类项的表格tr和td
	 * 
	 * @author dyh 2010-07-24
	 * @param content
	 *            要处理的数组,每一行
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=-1，表示数组所有列都需要考虑合并。不包括行数i字段
	 * @param trStyle
	 *            tr样式(缺省值为center)
	 * @prama tdStyle td样式(可以为空)
	 * @return 输出已合并上下同类项的表格tr和td
	 */
	public static String getRowspanArray2HTML(List<String[]> content,
			int needRowspanCols, int needRowspanLastCols, int startRowIndex) {
		return getRowspanArray2HTML(content, needRowspanCols,
				needRowspanLastCols, "", null, startRowIndex);
	}

	/**
	 * 输入一个数组，输出已合并上下同类项的表格tr和td
	 * 
	 * @author dyh 2010-07-24
	 * @param content
	 *            要处理的数组,每一行
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=-1，表示数组所有列都需要考虑合并。不包括行数i字段
	 * @param trStyle
	 *            tr样式(缺省值为center)
	 * @parma tdStyle td样式(可以为空)
	 * @param startRowIndex
	 *            用于分页时的起始行数。如果-1，表示按内部序列自增。
	 * @return 输出已合并上下同类项的表格tr和td
	 */
	public static String getRowspanArray2HTML(List<String[]> content,
			int needRowspanCols, int needRowspanLastCols, String trStyle,
			String[] tdStyle, int startRowIndex) {
		if (content != null && content.size() > 0) {
			int rowsize = content.size();
			String[] trStyles = new String[rowsize];
			for (int i = 0; i < rowsize; i++) {
				trStyles[i] = trStyle;
			}
			return getRowspanArray2HTML(content, needRowspanCols,
					needRowspanLastCols, trStyles, tdStyle, startRowIndex);
		} else
			return "";
	}

	/**
	 * 输入一个数组，输出已合并上下同类项的表格tr和td
	 * 
	 * @author dyh 2010-07-24
	 * @param content
	 *            要处理的数组,每一行
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=-1，表示数组所有列都需要考虑合并。不包括行数i字段
	 * @param trStyle
	 *            tr样式(缺省值为center)
	 * @parma tdStyle td样式(可以为空)
	 * @param startRowIndex
	 *            用于分页时的起始行数。如果-1，表示按内部序列自增。
	 * @return 输出已合并上下同类项的表格tr和td
	 */
	public static String getRowspanArray2HTML(List<String[]> content,
			int needRowspanCols, String[] trStyles, String[] tdStyles,
			int startRowIndex) {
		return getRowspanArray2HTML(content, needRowspanCols, 0, trStyles,
				tdStyles, startRowIndex);
	}

	/**
	 * 输入一个数组，输出已合并上下同类项的表格tr和td
	 * 
	 * @author dyh 2010-07-24
	 * @param content
	 *            要处理的数组,每一行
	 * @param needRowspanCols
	 *            前N列需要合并同类项，如果needRowspanCols=-1，表示数组所有列都需要考虑合并。不包括行数i字段
	 * @param trStyle
	 *            tr样式(缺省值为center)
	 * @parma tdStyle td样式(可以为空)
	 * @param startRowIndex
	 *            用于分页时的起始行数。如果-1，表示按内部序列自增。
	 * @return 输出已合并上下同类项的表格tr和td
	 */
	public static String getRowspanArray2HTML(List<String[]> content,
			int needRowspanCols, int needRowspanLastCols, String[] trStyles,
			String[] tdStyles, int startRowIndex) {
		int[][] rowspan = getRowspanArray(content, needRowspanCols,
				needRowspanLastCols);
		if (rowspan == null)
			return "";
		boolean isCenter = false;
		StringBuilder html = new StringBuilder();
		int col = 0, rowid = 0, rowIndex = 1;
		if (startRowIndex > 0)
			rowIndex += startRowIndex - 1;
		String trStyle = "";
		for (String[] rows : content) {
			trStyle = trStyles[rowid];
			html.append("<tr ");
			trStyle = formatStr(trStyle, "align=\"center\"");
			isCenter = (trStyle.toLowerCase().indexOf("center") >= 0);
			html.append(trStyle);
			html.append("><td");
			if (!isCenter) {
				html.append(" align=\"center\"");
			}
			html.append(">");
			html.append(rowIndex);
			html.append("</td>");
			for (col = 0; col < rows.length; col++) {
				if (rows[col] != null) {
					html.append("<td ");
					if (tdStyles != null) {
						html.append(formatStr(tdStyles[col], ""));
					}
					if (rowspan[rowid][col] > 1) {
						html.append(" rowspan=\"");
						html.append(rowspan[rowid][col]);
						html.append("\" ");
					}
					html.append(">");
					html.append(rows[col]);
					html.append("</td>");
				}
			}
			html.append("</tr>");
			rowid++;
			rowIndex++;
		}
		return html.toString();
	}

	/**
	 * 输出按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param id
	 * @param type
	 * @param value
	 * @param onclick
	 * @param disabled
	 * @param hide
	 * @return
	 */
	public static String getButtonTag(String id, String type, String value,
			String onclick, boolean disabled, boolean hide) {
		if (MyUtil.isStrNull(value)) {
			return "";
		}
		if (MyUtil.isStrNull(type)) {
			type = "button";
		}
		StringBuilder html = new StringBuilder();

		html.append("<input type=\"");
		html.append(type);
		html.append("\"");
		if (!MyUtil.isStrNull(id)) {
			html.append(" id=\"");
			html.append(id);
			html.append("\"");
		}
		if (!MyUtil.isStrNull(onclick)) {
			html.append(" onclick=\"");
			html.append(onclick);
			html.append("\"");
		}

		value = value.replace("&nbsp;", " ").trim();
		int len = getStringLength(value);
		String buttonClass = disabled ? "button_disable" : "button";
		html.append(" style=\"width:");
		html.append(len * 7 + 13);
		html.append("px");
		if (hide) {
			html.append(";display:none");
		}
		html.append("\" value=\"");
		html.append(value);
		html.append("\" class=\"");
		html.append(buttonClass);
		html.append("\" onmouseover=\"this.className='");
		html.append(buttonClass);
		html.append("_over'\" onmouseout=\"this.className='");
		html.append(buttonClass);
		html.append("'\"");
		if (disabled) {
			html.append(" disabled=\"true\"");
		}
		html.append("/>");
		return html.toString();
	}

	/**
	 * 输出按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param value
	 * @param onclick
	 * @return
	 */
	public static String getButtonTag(String value, String onclick) {
		return getButtonTag(null, null, value, onclick, false, false);
	}

	/**
	 * 输出查询按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param value
	 * @param onclick
	 * @param disabled
	 * @param hide
	 * @return
	 */
	public static String getButtonSearchTag(String value, String onclick,
			boolean disabled, boolean hide) {
		if (MyUtil.isStrNull(value)) {
			value = "查 询";
		}
		if (MyUtil.isStrNull(onclick)) {
			onclick = "mySearchNoCondition()";
		}
		return getButtonTag(null, null, value, onclick, disabled, hide);
	}

	/**
	 * 输出返回按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param value
	 * @param url
	 * @param disabled
	 * @param hide
	 * @return
	 */
	public static String getButtonReturnTag(String value, String url,
			boolean disabled, boolean hide) {
		if (MyUtil.isStrNull(value)) {
			value = "返 回";
		}
		String onclick = "";
		if (!MyUtil.isStrNull(url)) {
			onclick = "javascript:showDiv();location.href='" + url + "';";
		}
		return getButtonTag(null, null, value, onclick, disabled, hide);
	}

	/**
	 * 输出返回按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param value
	 * @param url
	 * @param disabled
	 * @param hide
	 * @return
	 */
	public static String getButtonReturnTag(String value, String url) {
		return getButtonReturnTag(value, url, false, false);
	}

	/**
	 * 输出关闭窗口按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param hide
	 * @return
	 */
	public static String getButtonCloseWinTag(boolean hide) {
		return getButtonTag("closewin__", null, "返 回",
				"javascript:hideAllDiv();window.close();", false, hide);
	}

	/**
	 * 输出EXCEL导出和打印按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param value
	 * @param table
	 * @param onclick
	 * @param noprint
	 * @return
	 */
	public static String getButtonExcelTag(String value, String table,
			String onclick, boolean noprint) {
		StringBuilder html = new StringBuilder();
		if (!MyUtil.isStrNull(table)) {
			onclick = "excelExportByTableName('" + table + "')";
		} else if (MyUtil.isStrNull(onclick)) {
			onclick = "excelExport()";
		}
		if (MyUtil.isStrNull(value)) {
			value = "导出EXCEL";
		}
		html.append(getButtonTag(value, onclick));
		if (!noprint) {
			if (!MyUtil.isStrNull(table)) {
				onclick = "printMeByTableName('" + table + "')";
			} else {
				onclick = "printMe()";
			}
			html.append(" ");
			html.append(getButtonTag("打 印", onclick));
		}
		return html.toString();
	}

	/**
	 * 规格化文件大小：输入字节数，获取b,K或M字节
	 * 
	 * @author dyh 2010-07-16
	 * @param filelen
	 *            字节数
	 * @return b,K或M
	 */
	public static String formatFileLength(long filelen) {
		String strLen = "";
		if (filelen < 1024)
			strLen = String.valueOf(filelen) + " ";
		else if (filelen < 1024 * 1024) {
			strLen = new DecimalFormat("0.#").format(filelen / 1024.0) + " K";
		} else {
			strLen = new DecimalFormat("0.#").format(filelen / (1024.0 * 1024))
					+ " M";
		}
		return strLen + "bytes";
	}

	/**
	 * 除法
	 * 
	 * @author YOUCL 2011-1-2
	 * @param divider
	 *            除数
	 * @param total
	 *            被除数
	 * @return
	 */
	public static String divide(int total, int divider) {
		if (divider <= 0)
			return "0";
		return new DecimalFormat("0.#").format(total * 1.0 / divider);
	}

	/**
	 * 除法
	 * 
	 * @author YOUCL 2012-9-13
	 * @param divider
	 *            除数
	 * @param total
	 *            被除数
	 * @return
	 */
	public static double divide2Double(int total, int divider) {
		if (divider <= 0)
			return 0;
		return total * 1.0 / divider;
	}

	/**
	 * 去除所有含<>的代码
	 * 
	 * @author dyh 2009-11-15
	 * @param content
	 *            要格式化的内容
	 * @return
	 */
	public static String removeAllHTML(String content) {
		if (content != null && content.length() > 0) {
			content = content.trim();
			if (content.length() > 0) {
				if (content.indexOf("<") > -1)
					content = content.replaceAll("<([^<>]*)>", "").trim();
			}
		}
		return content;
	}

	/**
	 * 报表行列数据转换(以全省销售报表为例，表头为21个地市，表第一列为品牌，第二列为机型,则rows为机型的productid列表，cols为21个areaid)
	 * 
	 * @author dyh 2009-10-27
	 * @param rows
	 *            行头编码列表(如：productid),最好经过排序
	 * @param cols
	 *            列头编码列表(如：areaid),最好经过排序
	 * @param data
	 *            从数据库取出的原始数据集,最好能按行编码和列编码进行排序，以便提高性能（如：含productid,areaid,quantity三个字段的集合，最好能按productid,areaid排序）
	 * @param hasRowTotal
	 *            是否需要合计一行中所有数量（如：某机型21个地市的总销量）
	 * @param hasColTotal
	 *            是否需要合计一列中所有数量（如：某地市所有机型的总销量）
	 * @return int[rows][cols]已经过行列转换整理的二维数组，用于显示到网页的表格中：
	 */
	/**
	 * public static int[][] data2Array(List<String> rows, List<String> cols,
	 * List<DataArray> data, boolean hasRowTotal, boolean hasColTotal) { //
	 * 行与列不允许为空 if (cols == null || rows == null) return null; int colslen =
	 * cols.size(); int rowslen = rows.size();
	 * 
	 * int rowslenOK = rowslen + (hasColTotal ? 1 : 0); int colslenOK = colslen +
	 * (hasRowTotal ? 1 : 0);
	 * 
	 * int[][] dataOK; if (colslen == 0 || rowslen == 0) { dataOK = new
	 * int[(rowslen == 0 ? 1 : rowslenOK)][(colslen == 0 ? 1 : colslenOK)];
	 * return dataOK; }
	 * 
	 * String colcode = null; String rowcode = null; String colcodeold = null;
	 * String rowcodeold = null; int value = 0;
	 * 
	 * dataOK = new int[rowslenOK][colslenOK]; // 如果data为空，则返回一个0值的数组 int
	 * datalen = data.size(); if (data == null || datalen == 0) { return dataOK; }
	 * 
	 * int rowIndex = -1; int colIndex = -1; // 遍历数据集 for (int i = 0; i <
	 * datalen; i++) { if (data.get(i) == null) continue; rowcode =
	 * data.get(i).getRowcode(); colcode = data.get(i).getColcode(); value =
	 * data.get(i).getValue(); if (i == 0) { rowcodeold = rowcode; colcodeold =
	 * colcode; } if (rowIndex == -1 || !rowcode.equals(rowcodeold)) { rowIndex =
	 * -1;// 未找到行 for (int r = 0; r < rowslen; r++) { if
	 * (rowcode.equals(rows.get(r))) { rowIndex = r; break; } } } if (colIndex ==
	 * -1 || !colcode.equals(colcodeold)) { colIndex = -1;// 未找到列 for (int c =
	 * 0; c < colslen; c++) { if (colcode.equals(cols.get(c))) { colIndex = c;
	 * break; } } } if (rowIndex != -1 && colIndex != -1) {
	 * dataOK[rowIndex][colIndex] += value; if (hasRowTotal)
	 * dataOK[rowIndex][colslenOK - 1] += value;// 每行小计 if (hasColTotal)
	 * dataOK[rowslenOK - 1][colIndex] += value;// 每列小计 if (hasRowTotal &&
	 * hasColTotal) dataOK[rowslenOK - 1][colslenOK - 1] += value;// 全表合计 }
	 * rowcodeold = rowcode; colcodeold = colcode; } return dataOK; }
	 */

	/**
	 * 报表行列数据转换(以省连锁渠道销售明细报表为例，表头为两行：第一行为7家连锁企业，第二行为每家连锁下面的21个地市列表，相当于第二行长度至少为7*21；第一列为品牌，第二列为机型；则rows为机型的productid列表，
	 * cols1为7家连锁组织编码，cols2为21个areaid))
	 * 
	 * @author dyh 2009-10-27
	 * @param rows
	 *            行编码列表(如：productid),最好经过排序
	 * @param cols1
	 *            列头1编码列表(如：7个连锁企业的编码),最好经过排序
	 * @param cols2
	 *            列头2编码列表(如：21个areaid),最好经过排序
	 * @param data
	 *            从数据库取出的原始数据集,最好能按行编码和列编码进行排序，以便提高性能（如：含productid,orgacode,areaid,quantity四个字段的集合，最好能按productid,orgacode
	 *            ,areaid排序）
	 * @param hasRowTotal
	 *            是否需要合计一行中cols2所有数量（如：某机型每家连锁企业各地市总销量）
	 * @param hasRepeatCount
	 *            是否需要按cols1小计数量（如：某机某连锁21个地市的小计销量，7个连锁则有7个小计数量）
	 * @param hasColTotal
	 *            是否需要合计一列中所有数量（如：某连锁企业的某地市所有机型的总销量）
	 * @return int[rows][cols]已经过行列转换整理的二维数组，用于显示到网页的表格中：
	 */
	/**
	 * public static int[][] data2ArrayColRepeat(List<String> rows, List<String>
	 * cols1, List<String> cols2, List<DataArrayColRepeat> data, boolean
	 * hasRowTotal, boolean hasRepeatCount, boolean hasColTotal) { boolean
	 * hasTableTotal = hasRowTotal && hasColTotal; return
	 * data2ArrayColRepeat(rows, cols1, cols2, data, hasRowTotal,
	 * hasRepeatCount, hasColTotal, hasTableTotal); }
	 */

	/**
	 * 报表行列数据转换(以省连锁渠道销售明细报表为例，表头为两行：第一行为7家连锁企业，第二行为每家连锁下面的21个地市列表，相当于第二行长度至少为7*21；第一列为品牌，第二列为机型；则rows为机型的productid列表，
	 * cols1为7家连锁组织编码，cols2为21个areaid))
	 * 
	 * @author ycl 2012-08-22
	 * @param rows
	 *            行编码列表(如：productid),最好经过排序
	 * @param cols1
	 *            列头1编码列表(如：7个连锁企业的编码),最好经过排序
	 * @param cols2
	 *            列头2编码列表(如：21个areaid),最好经过排序
	 * @param data
	 *            从数据库取出的原始数据集,最好能按行编码和列编码进行排序，以便提高性能（如：含productid,orgacode,areaid,quantity四个字段的集合，最好能按productid,orgacode
	 *            ,areaid排序）
	 * @param hasRowTotal
	 *            是否需要合计一行中cols2所有数量（如：某机型每家连锁企业各地市总销量）
	 * @param hasRepeatCount
	 *            是否需要按cols1小计数量（如：某机某连锁21个地市的小计销量，7个连锁则有7个小计数量）
	 * @param hasColTotal
	 *            是否需要合计一列中所有数量（如：某连锁企业的某地市所有机型的总销量）
	 * @param hasTableTotal
	 *            是否全表合计
	 * @return int[rows][cols]已经过行列转换整理的二维数组，用于显示到网页的表格中：
	 */
	/**
	 * public static int[][] data2ArrayColRepeat(List<String> rows, List<String>
	 * cols1, List<String> cols2, List<DataArrayColRepeat> data, boolean
	 * hasRowTotal, boolean hasRepeatCount, boolean hasColTotal, boolean
	 * hasTableTotal) { // 行与列不允许为空 if (cols1 == null || cols2 == null || rows ==
	 * null) return null; int cols1len = cols1.size(); int cols2len =
	 * cols2.size(); int rowslen = rows.size(); int datalen = data.size();
	 * 
	 * String col1code = null; String col2code = null; String rowcode = null;
	 * String col1codeold = null; String col2codeold = null; String rowcodeold =
	 * null; int value = 0;
	 * 
	 * int rowslenOK = rowslen + (hasColTotal ? 1 : 0); int colslenOK = cols1len *
	 * cols2len + (hasRepeatCount ? cols1len : 0) + (hasRowTotal ? cols2len +
	 * (hasRepeatCount ? 1 : 0) : 0); int[][] dataOK = new
	 * int[rowslenOK][colslenOK]; if (cols1len == 0 || cols2len == 0 || rowslen ==
	 * 0) return dataOK; // 如果data为空，则返回一个0值的数组 if (data == null || datalen ==
	 * 0) { return dataOK; }
	 * 
	 * int rowIndex = -1; int colIndex = -1; int col1Index = -1; int col2Index =
	 * -1; // 遍历数据集 for (int i = 0; i < datalen; i++) { if (data.get(i) == null)
	 * continue; rowcode = data.get(i).getRowcode(); col1code =
	 * data.get(i).getCol1code(); col2code = data.get(i).getCol2code(); value =
	 * data.get(i).getValue(); if (i == 0) { rowcodeold = rowcode; col1codeold =
	 * col1code; col2codeold = col2code; } if (rowIndex == -1 ||
	 * !rowcode.equals(rowcodeold)) { rowIndex = -1;// 未找到行 for (int r = 0; r <
	 * rowslen; r++) { if (rowcode.equals(rows.get(r))) { rowIndex = r; break; } } }
	 * if (col1Index == -1 || !col1code.equals(col1codeold)) { col1Index = -1;//
	 * 未找到列头1 for (int c = 0; c < cols1len; c++) { if
	 * (col1code.equals(cols1.get(c))) { col1Index = c; break; } } } if
	 * (col1Index != -1) { if (col2Index == -1 || !col2code.equals(col2codeold)) {
	 * col2Index = -1;// 未找到列头2 for (int c = 0; c < cols2len; c++) { if
	 * (col2code.equals(cols2.get(c))) { col2Index = c; break; } } } } if
	 * (rowIndex != -1 && col1Index != -1 && col2Index != -1) { colIndex =
	 * col1Index * cols2len + col2Index + (hasRepeatCount ? col1Index : 0);
	 * dataOK[rowIndex][colIndex] += value; if (hasRepeatCount)
	 * dataOK[rowIndex][(col1Index + 1) * cols2len + col1Index] += value;//
	 * 按cols1小计数量 if (hasRowTotal) {// 每行小计 dataOK[rowIndex][cols1len * cols2len +
	 * (hasRepeatCount ? cols1len : 0) + col2Index] += value; if
	 * (hasRepeatCount) dataOK[rowIndex][colslenOK - 1] += value;// 每行最后一列合计 }
	 * if (hasColTotal) { dataOK[rowslenOK - 1][colIndex] += value;// 每列小计 //
	 * dataOK[rowslenOK - 1][(col1Index + 1) * cols2len + col1Index] += value;//
	 * 每列小计 if (hasRowTotal) {// 每行小计 dataOK[rowslenOK - 1][cols1len * cols2len +
	 * (hasRepeatCount ? cols1len : 0) + col2Index] += value; } } if
	 * (hasRepeatCount && hasColTotal) {// 最后一行, 按cols1小计数量 dataOK[rowslenOK -
	 * 1][(col1Index + 1) * cols2len + col1Index] += value; } if (hasTableTotal)
	 * dataOK[rowslenOK - 1][colslenOK - 1] += value;// 全表合计 } rowcodeold =
	 * rowcode; col1codeold = col1code; col2codeold = col2code; } return dataOK; }
	 */

	/**
	 * 报表行列数据转换(举例：表最左侧有两列：第一列为地市，第二列为每个地市相同数量各种渠道类型,则rows1为地市,rows2为渠道类型)
	 * 
	 * @author dyh 2011-01-20
	 * @param rows1
	 *            行1编码列表
	 * @param row2
	 *            行2编码列表
	 * @param cols
	 *            列头编码列表
	 * @param data
	 *            从数据库取出的原始数据集,最好能按行编码和列编码进行排序，以便提高性能（如：含productid,orgacode,areaid,quantity四个字段的集合，最好能按productid,orgacode
	 *            ,areaid排序）
	 * @param hasRowTotal
	 *            是否需要合计一行中所有数量（如：某机型所有7家连锁企业的总销量）
	 * @param hasRepeatCount
	 *            是否需要按cols1小计数量（如：某机某连锁21个地市的小计销量，7个连锁则有7个小计数量）
	 * @param hasColTotal
	 *            是否需要合计一列中所有数量（如：某连锁企业的某地市所有机型的总销量）
	 * @return int[rows][cols]已经过行列转换整理的二维数组，用于显示到网页的表格中：
	 */
	/**
	 * public static int[][] data2ArrayRowRepeat(List<String> rows1, List<String>
	 * rows2, List<String> cols, List<DataArrayRowRepeat> data, boolean
	 * hasRowTotal, boolean hasRepeatCount, boolean hasColTotal) { // 行与列不允许为空
	 * if (rows1 == null || rows2 == null || cols == null) return null; int
	 * rows1len = rows1.size(); int rows2len = rows2.size(); int colslen =
	 * cols.size(); int datalen = data.size(); if (rows1len == 0 || rows2len ==
	 * 0 || colslen == 0) return null;
	 * 
	 * String row1code = null; String row2code = null; String colcode = null;
	 * String row1codeold = null; String row2codeold = null; String colcodeold =
	 * null; int value = 0;
	 * 
	 * int colslenOK = colslen + (hasRowTotal ? 1 : 0); int rowslenOK = rows1len *
	 * rows2len + (hasRepeatCount ? rows1len : 0) + (hasColTotal ? 1 : 0);
	 * int[][] dataOK = new int[rowslenOK][colslenOK]; // 如果data为空，则返回一个0值的数组 if
	 * (data == null || datalen == 0) { return dataOK; }
	 * 
	 * int rowIndex = -1; int colIndex = -1; int row1Index = -1; int row2Index =
	 * -1; // 遍历数据集 for (int i = 0; i < datalen; i++) { if (data.get(i) == null)
	 * continue; row1code = data.get(i).getRow1code(); row2code =
	 * data.get(i).getRow2code(); colcode = data.get(i).getColcode(); value =
	 * data.get(i).getValue(); if (i == 0) { row1codeold = row1code; row2codeold =
	 * row2code; colcodeold = colcode; } if (colIndex == -1 ||
	 * !colcode.equals(colcodeold)) { colIndex = -1;// 未找到列 for (int c = 0; c <
	 * colslen; c++) { if (colcode.equals(cols.get(c))) { colIndex = c; break; } } }
	 * if (row1Index == -1 || !row1code.equals(row1codeold)) { row1Index = -1;//
	 * 未找到行头1 for (int r = 0; r < rows1len; r++) { if
	 * (row1code.equals(rows1.get(r))) { row1Index = r; break; } } } if
	 * (row1Index != -1) { if (row2Index == -1 || !row2code.equals(row2codeold)) {
	 * row2Index = -1;// 未找到列头1 for (int r = 0; r < rows2len; r++) { if
	 * (row2code.equals(rows2.get(r))) { row2Index = r; break; } } } } if
	 * (colIndex != -1 && row1Index != -1 && row2Index != -1) { rowIndex =
	 * row1Index * rows2len + row2Index + (hasRepeatCount ? row1Index : 0);
	 * dataOK[rowIndex][colIndex] += value; if (hasRepeatCount)
	 * dataOK[(row1Index + 1) * rows2len + row1Index][colIndex] += value;//
	 * 按rows1小计数量 if (hasColTotal) dataOK[rowIndex][colslenOK - 1] += value;//
	 * 每列小计 if (hasRowTotal) { dataOK[rowslenOK - 1][colIndex] += value;// 每行小计 //
	 * dataOK[(row1Index + 1) * rows2len + row1Index][colIndex] += value;// 每行小计 }
	 * if (hasRowTotal && hasColTotal) dataOK[rowslenOK - 1][colslenOK - 1] +=
	 * value;// 全表合计 } colcodeold = colcode; row1codeold = row1code; row2codeold =
	 * row2code; } return dataOK; }
	 */

	/**
	 * double型,报表行列数据转换(以全省销售报表为例，表头为21个地市，表第一列为品牌，第二列为机型,则rows为机型的productid列表，cols为21个areaid)
	 * 
	 * @author dyh 2012-02-05
	 * @param rows
	 *            行头编码列表(如：productid),最好经过排序
	 * @param cols
	 *            列头编码列表(如：areaid),最好经过排序
	 * @param data
	 *            从数据库取出的原始数据集,最好能按行编码和列编码进行排序，以便提高性能（如：含productid,areaid,quantity三个字段的集合，最好能按productid,areaid排序）
	 * @param hasRowTotal
	 *            是否需要合计一行中所有数量（如：某机型21个地市的总销量）
	 * @param hasColTotal
	 *            是否需要合计一列中所有数量（如：某地市所有机型的总销量）
	 * @return double[rows][cols]已经过行列转换整理的二维数组，用于显示到网页的表格中：
	 */
	/**
	 * public static double[][] data2ArrayDouble(List<String> rows, List<String>
	 * cols, List<DataArrayDouble> data, boolean hasRowTotal, boolean
	 * hasColTotal) { // 行与列不允许为空 if (cols == null || rows == null) return null;
	 * int colslen = cols.size(); int rowslen = rows.size();
	 * 
	 * int rowslenOK = rowslen + (hasColTotal ? 1 : 0); int colslenOK = colslen +
	 * (hasRowTotal ? 1 : 0);
	 * 
	 * double[][] dataOK; if (colslen == 0 || rowslen == 0) { dataOK = new
	 * double[(rowslen == 0 ? 1 : rowslenOK)][(colslen == 0 ? 1 : colslenOK)];
	 * return dataOK; }
	 * 
	 * String colcode = null; String rowcode = null; String colcodeold = null;
	 * String rowcodeold = null; double value = 0.0;
	 * 
	 * dataOK = new double[rowslenOK][colslenOK]; // 如果data为空，则返回一个0值的数组 int
	 * datalen = data.size(); if (data == null || datalen == 0) { return dataOK; }
	 * 
	 * int rowIndex = -1; int colIndex = -1; // 遍历数据集 for (int i = 0; i <
	 * datalen; i++) { if (data.get(i) == null) continue; rowcode =
	 * data.get(i).getRowcode(); colcode = data.get(i).getColcode(); value =
	 * data.get(i).getValue(); if (i == 0) { rowcodeold = rowcode; colcodeold =
	 * colcode; } if (rowIndex == -1 || !rowcode.equals(rowcodeold)) { rowIndex =
	 * -1;// 未找到行 for (int r = 0; r < rowslen; r++) { if
	 * (rowcode.equals(rows.get(r))) { rowIndex = r; break; } } } if (colIndex ==
	 * -1 || !colcode.equals(colcodeold)) { colIndex = -1;// 未找到列 for (int c =
	 * 0; c < colslen; c++) { if (colcode.equals(cols.get(c))) { colIndex = c;
	 * break; } } } if (rowIndex != -1 && colIndex != -1) {
	 * dataOK[rowIndex][colIndex] += value; if (hasRowTotal)
	 * dataOK[rowIndex][colslenOK - 1] += value;// 每行小计 if (hasColTotal)
	 * dataOK[rowslenOK - 1][colIndex] += value;// 每列小计 if (hasRowTotal &&
	 * hasColTotal) dataOK[rowslenOK - 1][colslenOK - 1] += value;// 全表合计 }
	 * rowcodeold = rowcode; colcodeold = colcode; } return dataOK; }
	 */

	/**
	 * 根据当前行数，组合成编码列表(用英文逗号间隔),主要用于生成页面动态表时记录行数
	 * 
	 * @author dyh 2012-5-10
	 * @param startrowindex
	 * @param endrowindex
	 * @return
	 */
	public static String getRowIDs(int startrowindex, int endrowindex) {
		StringBuilder html = new StringBuilder();
		if (endrowindex > 0) {
			for (int i = startrowindex; i <= endrowindex; i++) {
				html.append(i);
				html.append(",");
			}
		}
		return html.toString();
	}

	/**
	 * 添加动态表格最后一列的“删除”按钮
	 * 
	 * @author dyh 2012-5-10
	 * @param rowindex
	 *            行号
	 * @param rowidTagID
	 *            存放行号的hidden控件
	 * @param tableID
	 * @param headrows
	 *            表头行数
	 * @return
	 */
	public static String getDelButton(int rowindex, String rowidTagID,
			String tableID, int headrows) {
		StringBuilder html = new StringBuilder();
		html
				.append("<input type=button value=\"删除\" class=\"button\" onclick=\"delRow(");
		html.append(rowindex);
		html.append(",'");
		html.append(rowidTagID);
		html.append("','");
		html.append(tableID);
		html.append("',");
		html.append(headrows);
		html.append(")\">");
		return html.toString();
	}

	/**
	 * 添加动态表格最后一列的“删除”按钮
	 * 
	 * @author dyh 2012-5-10
	 * @param rowindex
	 *            行号
	 * @param rowidTagID
	 *            存放行号的hidden控件
	 * @param tableID
	 * @return
	 */
	public static String getDelButton(int rowindex, String rowidTagID,
			String tableID) {
		return getDelButton(rowindex, rowidTagID, tableID, 1);

	}

	/**
	 * 根据数组各元素值在数组中的大小位置，按升序或降序返回数组元素的排名，默认降序 如传入{6,8,5,7,4},orderFlag为asc,返回{3,
	 * 5, 2, 4, 1}
	 * 
	 * @author youcl 2012-8-9
	 * @param data
	 * @param orderFlag
	 *            asc，升序，desc，降序
	 * @return
	 */
	public static int[] getOrder(double[] data, String orderFlag) {
		if (data == null || data.length == 0)
			return null;
		int order[] = new int[data.length];
		double copy[] = data.clone();
		double temp = 0;
		if (MyConstant.ORDER_ASC.equalsIgnoreCase(orderFlag)) {
			for (int i = 1; i < copy.length; i++)
				for (int j = 0; j < i; j++)
					if (copy[j] > copy[i]) {
						temp = copy[i];
						copy[i] = copy[j];
						copy[j] = temp;
					}

		} else {
			for (int i = 1; i < copy.length; i++)
				for (int j = 0; j < i; j++)
					if (copy[j] < copy[i]) {
						temp = copy[i];
						copy[i] = copy[j];
						copy[j] = temp;
					}
		}
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < data.length; j++) {
				if (copy[i] == data[j]) {
					if (order[j] == 0) {
						order[j] = i + 1;
						break;
					}
				}
			}
		}
		return order;
	}

	/**
	 * 根据数组各元素值在数组中的大小位置，按升序或降序返回数组元素的排名，默认降序 如传入{6,8,5,7,4},orderFlag为asc,返回{3,
	 * 5, 2, 4, 1}
	 * 
	 * @author youcl 2012-8-9
	 * @param data
	 * @param orderFlag
	 *            asc，升序，desc，降序
	 * @return
	 */
	public static int[] getOrder(int[] data, String orderFlag) {
		if (data == null || data.length == 0)
			return null;
		double copy[] = new double[data.length];
		for (int i = 0; i < data.length; i++)
			copy[i] = data[i];
		return getOrder(copy, orderFlag);
	}

	/** ***************** 99. 控件、网页、IMEI、路径相关方法结束 ************************** */

	/**
	 * 删除有目录文件夹的方法
	 * 
	 * @author wmr 2012-12-5
	 * @param file
	 *            要删除的文件
	 * @return
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			log.warn(file.getName() + " file is not exit!");
		}
	}

	/**
	 * 得到文件后缀名的方法
	 * 
	 * @author wmr 2012-12-5
	 * @param fileName
	 *            文件名
	 * @return String 文件后缀名
	 */
	public static String getFileLastName(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
		return suffix;
	}

//	public static void main(String[] args) {
//		deleteFile(new File("f:/a"));
//	}

	/**
	 * 生成DWZ正常的按钮
	 * 
	 * @author tcl [Dec 10, 2012]
	 * @param name
	 * @param className
	 * @param type
	 * @param id
	 * @param onclick
	 * @param others
	 * @return
	 */
	public static String getNormalButtonTag(String name, String className,
			String type, String id, String onclick, String others) {
		if (isStrNull(name)) {
			return "";
		}

		StringBuilder html = new StringBuilder();

		html.append("<div");
		if (!isStrNull(className)) {
			html.append(" class=\"");
			html.append(className);
			html.append("\">");
		} else {
			html.append(" class=\"buttonActive\">");
		}

		html.append("<div class=\"buttonContent\"><button");

		if (!isStrNull(type)) {
			html.append(" type=\"");
			html.append(type);
			html.append("\"");
		} else {
			html.append(" type=\"button\"");
		}
		if (!isStrNull(id)) {
			html.append(" id=\"");
			html.append(id);
			html.append("\"");
		}
		if (!isStrNull(onclick)) {
			html.append(" onclick=\"");
			html.append(onclick);
			html.append("\"");
		}

		if (!isStrNull(others)) {
			html.append(" " + others);
		}
		html.append(">" + name + "</button></div></div>");
		return html.toString();
	}

	/**
	 * 生成DWZ连接类型的按钮
	 * 
	 * @author tcl [Dec 10, 2012]
	 * @param name
	 * @param className
	 * @param type
	 * @param id
	 * @param onclick
	 * @param others
	 * @return String
	 */

	public static String getALinkButtonTag(String name, String className,
			String href, String target, String rel, String title, String id,
			String others) {
		if (isStrNull(name)) {
			return "";
		}

		StringBuilder html = new StringBuilder();

		html.append("<a");
		if (!isStrNull(className)) {
			html.append(" class=\"");
			html.append(className);
			html.append("\"");
		} else {
			html.append(" class=\"button\"");
		}
		if (!isStrNull(href)) {
			html.append(" href=\"");
			html.append(href);
			html.append("\"");
		}
		if (!isStrNull(target)) {
			html.append(" target=\"");
			html.append(target);
			html.append("\"");
		}
		if (!isStrNull(rel)) {
			html.append(" rel=\"");
			html.append(rel);
			html.append("\"");
		}
		if (!isStrNull(title)) {
			html.append(" title=\"");
			html.append(title);
			html.append("\"");
		}

		if (!isStrNull(id)) {
			html.append(" id=\"");
			html.append(id);
			html.append("\"");
		}

		if (!isStrNull(others)) {
			html.append(" " + others);
		}

		html.append("><span>" + name + "</span></a>");
		return html.toString();
	}

	/**
	 * 生成下拉框html
	 * 
	 * @author tcl [Dec 11, 2012]
	 * @param tip
	 * @param name
	 * @param id
	 * @param className
	 * @param firstoption
	 * @param firstvalue
	 * @param map
	 * @param group
	 * @param ref
	 * @param refUrl
	 * @param selected
	 * @param disabled
	 * @param onchange
	 * @return String
	 */
	public static String getDWZSelectTag(String firstoptionshow, String name,
			String id, String className, String firstoption, String firstvalue,
			Map<Object, Object> map, String group, String ref, String refUrl,
			String selected, boolean disabled, String onchange, String others) {

		StringBuilder html = new StringBuilder();

		/*
		 * if (!isStrNull(tip)) { html.append(tip); html.append("："); }
		 */

		if (isStrNull(name)) {
			name = "myselect";
		}
		html.append("<select name=\"");
		html.append(name);
		html.append("\"	id=\"");
		if (!isStrNull(id)) {
			html.append(id);
		} 
			 
		html.append("\" ");

		boolean isSelect = false;

		if (isStrNull(firstoption)) {
			firstoption = MyConstant.DWZ_SELECT_FIRST_OPTION_ALL;
		}

		if (firstoption
				.equalsIgnoreCase(MyConstant.DWZ_SELECT_FIRST_OPTION_SELECT)) {
			isSelect = true;
		}

		if (!isStrNull(className)) {
			html.append("class=\"");
			if (!isSelect) {
				html.append(className);
			} else {
				html.append("required " + className);
			}
			html.append("\"");
		} else {
			html.append("class=\"");
			if (!isSelect) {
				html.append("combox");
			} else {
				html.append("required combox");
			}
			html.append("\"");
		}

		if (!isStrNull(ref)) {
			html.append(" ref=\"");
			html.append(ref);
			html.append("\"");
		}

		if (!isStrNull(refUrl)) {
			html.append(" refUrl=\"");
			html.append(refUrl);
			html.append("\"");
		}

		// 禁用
		if (disabled) {
			html.append(" disabled=\"disabled\"");
		}
		if (!isStrNull(onchange)) {
			html.append(" onchange=\"");
			html.append(onchange);
			html.append("\"");
		}

		if (!isStrNull(others)) {
			html.append(" " + others);
		}

		html.append(">");

		boolean hasFirstOption = false;
		// String firstOptionShow = null;
		if (MyConstant.DWZ_SELECT_FIRST_OPTION_ALL
				.equalsIgnoreCase(firstoption)
				|| MyConstant.DWZ_SELECT_FIRST_OPTION_SELECT
						.equalsIgnoreCase(firstoption)) {
			hasFirstOption = true;
			if (isStrNull(firstvalue)) {
				firstvalue = MyConstant.DWZ_SELECT_FIRST_VALUE;
			}
			if (isStrNull(firstoptionshow)) {
				firstoptionshow = isSelect ? "请选择" : "全部";
			}

		}

		if (hasFirstOption) {
			html.append("<option value=\"");

			html.append(firstvalue);
			html.append("\">");
			html.append(firstoptionshow);
			html.append("</option>");
		}

		int mapsize = 0;
		String groups[] = null;
		int groupsize = 0;

		if (map != null) {
			mapsize = map.size();
		}

		if (mapsize == 0 && !isStrNull(group)) {
			groups = group.split("`");
			groupsize = groups.length;
			if (groupsize > 0) {
				if (groupsize % 2 != 0) {// 如果不是成对出现，则无效
					groupsize = 0;
				}
			}
			map = new LinkedHashMap<Object, Object>();
			for (int i = 0; i < groupsize; i += 2) {
				map.put(groups[i], groups[i + 1]);
			}
			mapsize = map.size();
		}

		if (mapsize > 0) {
			Iterator<Map.Entry<Object, Object>> iterator = map.entrySet()
					.iterator();
			String key = null;
			String value = null;
			Map.Entry<Object, Object> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				key = entry.getKey().toString();
				value = entry.getValue().toString();
				html.append("<option value=\"");
				html.append(key);
				html.append("\" ");
				if (!MyUtil.isStrNull(selected) && key != null
						&& key.equals(selected))
					html.append("selected=\"selected\"");
				html.append(">");
				html.append(value);
				html.append("</option>");
			}
		}
		html.append("</select>");
		return html.toString();
	}

	/**
	 * 多行文本框html
	 * 
	 * @author tcl [Dec 11, 2012]
	 * @param name
	 * @param id
	 * @param rows
	 * @param cols
	 * @param maxlength
	 * @param value
	 * @param className
	 * @param readonly
	 * @param disabled
	 * @param others
	 * @param disablShowTip
	 * @return String
	 */
	public static String getTextareaTag(String name, String id, int rows,
			int cols, int maxlength, String value, String className,
			boolean readonly, boolean disabled, String others,
			boolean disablShowTip) {

		if (isStrNull(name)) {
			return "";
		}
		if (maxlength <= 0) {
			return "";
		}

		if (isStrNull(id)) {
			id = name;
		}
		if (rows <= 0)
			rows = 5;
		if (cols <= 0)
			cols = 50;

		StringBuilder html = new StringBuilder();
		html.append("<textarea name=\"");
		html.append(name);
		html.append("\" id=\"");
		html.append(id);
		html.append("\"");

		if (!isStrNull(className)) {
			html.append(" class=\"");
			html.append(className);
			html.append("\"");
		}

		if (readonly) {
			html.append(" readonly=\"true\"");
		}

		if (disabled) {
			html.append(" disabled=\"true\"");
		}

		if (!isStrNull(others)) {
			html.append(" " + others);
		}
		html.append(" maxlength=\"");
		html.append(maxlength);
		html.append("\" rows=\"");
		html.append(rows);
		html.append("\" cols=\"");
		html.append(cols);
		html.append("\">");
		html.append(formatStr(value));
		html.append("</textarea>");
		if (!disablShowTip) {
			String fontid = id + "___";
			html.append("您最多可以输入<font id=\"");
			html.append(fontid);
			html.append("\" color=\"red\">");
			html.append(maxlength);
			html.append("</font>个字符 ");
		}
		return html.toString();
	}

	/**
	 * 生成单行文本框的html
	 * 
	 * @author tcl [Dec 12, 2012]
	 * @param name
	 * @param id
	 * @param type
	 * @param className
	 * @param value
	 * @param size
	 * @param maxlength
	 * @param alt
	 * @param disabled
	 * @param readonly
	 * @param onchange
	 * @return String
	 */
	public static String getInputHtmlTag(String name, String id, String type,
			String className, String value, int size, int maxlength,
			String alt, boolean disabled, boolean readonly, String onchange,String onfocus,String onblur,
			String others) {
		StringBuilder html = new StringBuilder();
		if (MyUtil.isStrNull(name)) {
			name = "myinput";
		}
		if (MyUtil.isStrNull(type)) {
			type = "text";
		} else {
			type = type.toLowerCase();
		}
		if (size <= 0) {
			// size = 5;
		}
		if (maxlength <= 0) {
			maxlength = 25;
		}

		html.append("<input name=\"");
		html.append(name);
		html.append("\" id=\"");

		if (!MyUtil.isStrNull(id)) {
			html.append(id);
		} else {
			html.append(name);
		}
		html.append("\" type=\"");
		html.append(type + "\"");

		if (size > 0) {
			html.append(" size=\"");
			html.append(size);
			html.append("\"");
		}
		if (maxlength > 0) {
			html.append(" maxlength=\"");
			html.append(maxlength);
			html.append("\" ");
		}
		if (!MyUtil.isStrNull(value)) {
			html.append(" value=\"");
			html.append(value);
			html.append("\"");
		}

		if (!MyUtil.isStrNull(className)) {
			html.append(" class=\"");
			html.append(className);
			html.append("\"");
		}

		if (!MyUtil.isStrNull(alt)) {
			html.append(" alt=\"");
			html.append(alt);
			html.append("\"");
		}

		if (disabled) {
			html.append(" disabled=\"true\"");
		}

		if (readonly) {
			html.append(" readonly=\"true\"");
		}

		if (!MyUtil.isStrNull(onchange)) {
			html.append(" onchange=\"");
			html.append(onchange);
			html.append("\"");
		}
		
		if (!MyUtil.isStrNull(onfocus)) {
			html.append(" onfocus=\"");
			html.append(onfocus);
			html.append("\"");
		}
		
		if (!MyUtil.isStrNull(onblur)) {
			html.append(" onblur=\"");
			html.append(onblur);
			html.append("\"");
		}

		if (!MyUtil.isStrNull(others)) {
			html.append(" " + others);
		}

		html.append("/>");
		return html.toString();

	}

	/**
	 * 获取DWZ单/多选框HTML
	 * 
	 * @author ycl 2012-10-3
	 * @param isCheckBox
	 *            是否为多选框
	 * @param name
	 * @param group
	 * @param map
	 * @param selected
	 *            如果是checkbox，用英文逗号间隔
	 * @param tip
	 * @param split
	 *            分隔符，默认为<br/> ;
	 * @return
	 */
	public static String getDWZRadioAndCheckBoxTag(boolean isCheckBox,
			String name, String group, Map<Object, String> map,
			String selected, String tip, String split, String others) {
		int mapsize = 0;
		String groups[] = null;
		int groupsize = 0;

		if (MyUtil.isStrNull(name)) {
			if (isCheckBox) {
				name = "c1";
			} else {
				name = "r1";
			}
		}
		if (MyUtil.isStrNull(split)) {
			split = "<br/>";
		}
		if (map != null) {
			mapsize = map.size();
		}
		if (mapsize == 0 && !MyUtil.isStrNull(group)) {
			groups = group.split("`");
			groupsize = groups.length;
			if (groupsize > 0) {
				if (groupsize % 2 != 0) {// 如果不是成对出现，则无效
					groupsize = 0;
				}
			}
			map = new LinkedHashMap<Object, String>();
			for (int i = 0; i < groupsize; i += 2) {
				map.put(groups[i], groups[i + 1]);
			}
			mapsize = map.size();
		}
		StringBuilder html = new StringBuilder();
		if (!MyUtil.isStrNull(tip)) {
			html.append(tip);
			html.append("：");
		}
		if (mapsize > 0) {
			String type = isCheckBox ? "checkbox" : "radio";
			Iterator<Map.Entry<Object, String>> iterator = map.entrySet()
					.iterator();
			Object key = null;
			Object value = null;
			Map.Entry<Object, String> entry = null;
			int i = 0;

			boolean selectedExists = false;

			if (!isStrNull(selected)) {
				selectedExists = true;
			}

			while (iterator.hasNext()) {
				entry = iterator.next();
				key = entry.getKey();
				value = entry.getValue();
				html.append("<input name=\"");
				html.append(name);
				html.append("\" type=\"");
				html.append(type + "\" ");

				if (!isStrNull(key.toString())) {
					html.append(" value=\"");
					html.append(key);
					html.append("\" ");
				}

				if (isCheckBox) {

					if (selectedExists
							&& (selected + ",").indexOf(key.toString() + ",") >= 0) {
						html.append("checked");
					}

				} else {

					if (!MyUtil.isStrNull(selected)
							&& selected.equals(key.toString())) {
						html.append("checked");
					} else if (i == 0) {
						html.append("checked");
					}

				}

				if (!MyUtil.isStrNull(others)) {
					html.append(" " + others);
				}
				html.append("/>");
				html.append(value);
				html.append(split);
				i++;
			}
		}
		return html.toString();
	}
	
	
	/**
	 * 输出EXCEL导出和打印按钮HTML
	 * 
	 * @author dyh 2012-4-29
	 * @param value
	 * @param table
	 * @param onclick
	 * @param noprint
	 * @return
	 */
	public static String getButtonExcelPrintTag(String name, String tableid,
			String onclick, boolean noprint) {
		StringBuilder html = new StringBuilder();
		if (!isStrNull(tableid)) {
			onclick = "excelExportByTableName('" + tableid + "')";
		} else if (isStrNull(onclick)) {
			onclick = "excelExport()";
		}
		if (isStrNull(name)) {
			name = "导出EXCEL";
		}
 
		html.append(getNormalButtonTag(name,null,null,null, onclick,null));
		if (!noprint) {
			if (!isStrNull(tableid)) {
				onclick = "printMeByTableName('" + tableid + "')";
			} else {
				onclick = "printMe()";
			}
			html.append(" ");
			html.append(getNormalButtonTag("打 印",null,null,null, onclick,null));
		}
		return html.toString();
	}
	
	/**
	 * 品牌的级联下拉框 
	* @param name2 第二级，可选项，默认为productId
	* @param name3 第三级，可选项，默认brandId
	* @param name4 第四级，可选项，默认bomId
	* @param id2 第二级，可选项，默认是productName
	* @param id3 第三级，可选项，默认是brandName
	* @param id4 第四级，可选项，默认是bomName
	* @param firstoption2 第二级下拉框的首选项,默认是全部
	* @param firstoption3 第三级下拉框的首选项,默认是全部
	* @param firstoption4 第四级下拉框的首选项,默认是全部
	* @param firstvalue2 第二级下拉框的首选项值，默认是""
	* @param firstvalue3 第三级下拉框的首选项值，默认是""
	* @param firstvalue4 第四级下拉框的首选项值，默认是""
	* @param num 下拉框级数，最小是2
	* @param title2 第二级下拉框的名称
	* @param title3 第三级下拉框的名称
	* @param title4 第四级下拉框的名称
	* @param require2; 第二级是否必填，默认为false
	* @param require3; 第三级是否必填，默认为false
	* @param require4; 第四级是否必填，默认为false
	* @return
	 */
	public static String getProductSelectTag(String name1,String name2,String name3,String name4,String id1,String id2,String id3,String id4, 
			String firstoption2,String firstoption3,String firstoption4,String firstvalue2,String firstvalue3,String firstvalue4,
			int num,String title2,String title3,String title4,boolean require2,boolean require3,boolean require4) {

		StringBuilder html = new StringBuilder();

		/*
		 * if (!isStrNull(tip)) { html.append(tip); html.append("："); }
		 */

		num = num < 2 ? 2: num;
		if(isStrNull(name1)){
		   name1 = "firstId";
		}
		if(isStrNull(name2)){
			name2="brandId";
		}
		if(isStrNull(name3)){
			name2="productId";
		}
		if(isStrNull(name4)){
			name2="bomId";
		}
		if(isStrNull(id1)){
		    id1="firstName";
		}
		if(isStrNull(id2)){
			id2="brandName";
		}
		if(isStrNull(id3)){
			id3="productName";
		}
		if(isStrNull(id4)){
			id4="bomName";
		}
		html.append("<span><select class=\"combox\" ref=\""+id2+"\" refUrl=\"/select/getProductSelect.c?selectId={value}&flag=2\" name=\"");
		html.append(name1);
		html.append("\"	id=\"");
		if (!isStrNull(id1)) {
			html.append(id1);
		} 
		html.append("\">");
		html.append("<option value =\"\">"+"是否终端");
		html.append("</option>");
		/*html.append("<option value =\""+BizTypeEnum.YES.getValue() +"\">" + BizTypeEnum.YES.getName());
		html.append("</option>");
		html.append("<option value =\""+BizTypeEnum.NO.getValue() +"\">" + BizTypeEnum.NO.getName());
		html.append("</option>");*/
		html.append("</select></span>");
		if(num == 2){
			html.append(getNextSelect(title2,firstoption2,firstvalue2,name2,id2,null,null,require2));
		}
		else if(num == 3){
			html.append(getNextSelect(title2,firstoption2,firstvalue2,name2,id2,id3,"3",require2));
			html.append(getNextSelect(title3,firstoption3,firstvalue3,name3,id3,null,null,require3));
		}
		else if(num == 4){
			html.append(getNextSelect(title2,firstoption2,firstvalue2,name2,id2,id3,"3",require2));
			html.append(getNextSelect(title3,firstoption3,firstvalue3,name3,id3,id4,"4",require3));
			html.append(getNextSelect(title4,firstoption4,firstvalue4,name4,id4,null,null,require4));
		}
		return html.toString();
	}
	
	
	public static String getNextSelect(String title,String firstShow, String firstValue,String name,String id,String nextId,String flag,boolean require){
		StringBuilder html = new StringBuilder();
		
		if (isStrNull(name)) {
			name = "";
		}
		if(isStrNull(title)){
			html.append("<div><span class=\"mySelect\"></span>");
		}else{
			html.append("<div><span class=\"mySelect\">"+title+":</span>");
		}
		if(require){
			html.append("<span><select class=\"combox required\"");
		}else{
			html.append("<span><select class=\"combox\"");
		}
		if (!isStrNull(nextId)) {
				html.append("ref=\""+nextId+"\" refUrl=\"/select/getProductSelect.c?selectId={value}&flag="+flag+"\"");
		}
		html.append("name=\""+name);
		html.append("\"	id=\"");
		if (!isStrNull(id)) {
			html.append(id);
		} 
		html.append("\">");
		if(isStrNull(firstShow)){
			firstShow = "全部";
		}
		if(isStrNull(firstValue)){
			firstValue="";
		}
		html.append("<option value=\"" + firstValue + "\" selected=\"selected\">" +firstShow+ "</option>");
		html.append("</select></span>");
		html.append("</div>");
		return html.toString();
	}
	
	
	/**
	 * 得到一个的下拉框 
	* @param firstShow
	* @param firstValue
	* @param name
	* @param id
	* @param map
	* @return
	 */
	@SuppressWarnings("unused")
	public static String getSelect(String title,String firstShow, String firstValue,String name,String id,Map<Long, String> map,String onChange){
		StringBuilder html = new StringBuilder();
		title = (title == null) ? "" : title;
		if (isStrNull(name)) {
			name = "";
		}
		html.append(title+"：<select class=\"combox\" name=\"");
		html.append(name);
		html.append("\"	id=\"");
		if (!isStrNull(id)) {
			html.append(id);
		} 
		html.append("\" onchange=\""+onChange+"\">");
		if(isStrNull(firstShow)){
			firstShow = "全部";
		}
		if(isStrNull(firstValue)){
			firstValue="";
		}
		html.append("<option value=\"" + firstValue + "\" selected=\"selected\">" +firstShow+ "</option>");
		if(map != null){
			Iterator<Long> it = map.keySet().iterator();
			while(it.hasNext()){
				Long key =it.next();
				html.append("<option value=\"" + key + "\">" +map.get(key)+ "</option>");
			}
		}
		html.append("</select>");
		
		return html.toString();
	}
	
	
	/**
	 * 返回alert脚本
	 * @author tcl 2011-3-11
	 * @param alertmsg 提示框内容
	 * @param alertType alert种类，有5种： confirm  确认（是/否）； error,info,warn,correct
	 * @param  others  其他信息  如 {okCall: function(){
	 *		//$.post(url, data, DWZ.ajaxDone, "json");
	 *		alert("1");
	 *	},
	 *	cancelCall:function(){
	 *		alert("0");
	 *	}
	 */
	public static String getAlertScript(String alertmsg, String alertType,String others) {
		StringBuilder script = new StringBuilder();
		if (!isStrNull(alertmsg) &&  !isStrNull(alertType)){
			script.append("<script type='text/javascript'>");
			
			
			script.append("alert.");
			script.append(alertType.trim());
			script.append("('");
			script.append(alertmsg);
			script.append("'");
			
			 if(!isStrNull(others)){
				 script.append(","+others); 
			 }	
			script.append(");");
			 
 
			script.append("</script>");
		}
		System.out.println("script.toString():"+script.toString());
		
		return script.toString();
	}
	
	public static void main(String[] args) {
//		Map<Long, String> map = new HashMap<Long, String>();
//		map.put(1l, "1");
//		map.put(2l, "2");
//		map.put(3l, "3");
//		String string = getProductSelectTag("name2", "name3", "name4", "id2", "id3", "id4", "firstoption2",
//				"firstoption3", "firstoption4", "firstvalue2", "firstvalue3", "firstvalue4", 4,"1","2","3");
//		
//		System.out.println(string);
	}
}
