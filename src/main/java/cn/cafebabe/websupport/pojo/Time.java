package cn.cafebabe.websupport.pojo;

import java.util.Calendar;

import cn.cafebabe.websupport.exception.WebsupportException;


public class Time
{
	private int time = 0;

	public Time()
	{
		Calendar calendar = Calendar.getInstance();
		time = calendar.get(Calendar.HOUR_OF_DAY) * 100
				+ calendar.get(Calendar.MINUTE);
	}

	public Time(int time)
	{
		if (time < 0 || time > 2400 || time - time / 100 > 60)
		{
			throw new WebsupportException("��Χ����!,Time�����ֵ����Ϊ" + time);
		}
		this.time = time;
	}

	public int getHour()
	{
		return time / 100;
	}

	public int getMinute()
	{
		return time - getHour();
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}
}
