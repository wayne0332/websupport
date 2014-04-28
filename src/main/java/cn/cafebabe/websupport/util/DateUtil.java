package cn.cafebabe.websupport.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
	public static final int SECOND = 1000, MINUTE = 60 * SECOND,
			HOUR = 60 * MINUTE, DAY = 24 * HOUR;

	public static Calendar getCalendar(int year, int month)
	{

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		return calendar;
	}

	public static Calendar getCalendar(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static String getDateByFormat()
	{
		return getDateByFormat(new Date(), "yyyy-MM-dd");
	}

	public static String getDateByFormat(String daf)
	{
		return getDateByFormat(new Date(), daf);
	}

	public static String getDateByFormat(SimpleDateFormat daf)
	{
		return getDateByFormat(new Date(), daf);
	}

	public static String getDateByFormat(Date date, String daf)
	{
		SimpleDateFormat dafe = new SimpleDateFormat(daf);
		return getDateByFormat(date, dafe);
	}

	public static String getDateByFormat(Date date, SimpleDateFormat daf)
	{
		String dateStr = daf.format(date);
		return dateStr;
	}

	public static Date getDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static int currentYear()
	{
		return getCalendar(new Date()).get(Calendar.YEAR);
	}
}
