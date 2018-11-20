package com.rahul.UtilSet;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import java.util.Calendar;

public class UtilDate
{
	static final Logger LOGGER = Logger.getLogger(UtilDate.class);
	
	public UtilDate()
	{
	}

	public String CurrTimeStamp() {
		Date date = new Date();  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSSZ");  
		return formatter.format(date) + " ";  
	}

	public String CurrTimeStamp(String lDateFormat) {
		Date date = new Date();  
		SimpleDateFormat formatter = new SimpleDateFormat(lDateFormat);  
		return formatter.format(date);  
	}

	public static String DateAddDay(String lDate, int lDays) throws Exception
	{
		//final String sdate = "2012-01-01";
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
		Date date = null;
		if(0 == lDate.length())
		{
			date = new Date();
		}
		else
		{
			date = df.parse( lDate );
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime( date );
		cal.add( GregorianCalendar.DAY_OF_MONTH, lDays );

		return df.format( cal.getTime());
	}
	public static String DateSubDay(String lDate, int lDays) throws Exception
	{
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
		Date date = null;
		if(0 == lDate.length())
		{
			date = new Date();
		}
		else
		{
			date = df.parse( lDate );
		}
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime( date );
		cal.add( GregorianCalendar.DAY_OF_MONTH, -1 );

		return df.format( cal.getTime());
	}

	public String DateFormatConverter(String lInFormat, String lOutFormat, String lInDate) throws Exception
	{
		SimpleDateFormat dt = new SimpleDateFormat(lInFormat);
		Date date = dt.parse(lInDate);

		SimpleDateFormat dt1 = new SimpleDateFormat(lOutFormat);
		return dt1.format(date);
	}

	public String EpocToTime(String lOutFormat, String lEpoc) throws Exception
	{
		long lepoc = Long.parseLong(lEpoc) * 1000;
		Date date = new Date(lepoc);
		DateFormat format = new SimpleDateFormat(lOutFormat);
		format.setTimeZone(TimeZone.getTimeZone("IST/UTC"));
		return format.format(date);
	}

	public String AddWorkingDays(String lDateFormat, String lDate, int lDays){
		SimpleDateFormat df = new SimpleDateFormat(lDateFormat);
		Date d = null;//df.parse(lDate);
		try{
			d = df.parse(lDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		int count = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		do {
			c.add(Calendar.DAY_OF_YEAR, 1);
			if(isWeekday(c.get(Calendar.DAY_OF_WEEK))) {
				++count;
			}
		} while(count < lDays);

		return df.format(c.getTime());
	}
	public static boolean isWeekday(int dayOfWeek) {
		return ((dayOfWeek != Calendar.SATURDAY) && (dayOfWeek != Calendar.SUNDAY));
	}
	public String SubWorkingDays(String lDateFormat, String lDate, int lDays){
		SimpleDateFormat df = new SimpleDateFormat(lDateFormat);
		Date d = null;//df.parse(lDate);
		try{
			d = df.parse(lDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		int count = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		do {
			c.add(Calendar.DAY_OF_YEAR, -1);
			if(isWeekday(c.get(Calendar.DAY_OF_WEEK))) {
				++count;
			}
		} while(count < lDays);

		return df.format(c.getTime());
	}

	public int DateTimeCompare(String lStartDate, String lEndDate, String lDateFormat)throws Exception {
		try{
			Date start = new SimpleDateFormat(lDateFormat/*, Locale.ENGLISH*/).parse(lStartDate);
			Date end = new SimpleDateFormat(lDateFormat/*, Locale.ENGLISH*/).parse(lEndDate);
			return start.compareTo(end);
		}catch(Exception e){
			System.out.println(this.CurrTimeStamp() + "DateTimeCompare-> lStartDate:" + lStartDate + " lEndDate:" + lEndDate + " lDateFormat:" + lDateFormat);
			e.printStackTrace();
			throw(e);
		}

	}

	public static void main (String[] args)
	{
		System.out.println("HELLO");
		//UtilDate obj = new UtilDate();
		//System.out.println(obj.GetNextWorkingDay("yyyy-MM-dd", "2010-02-06"));
		//System.out.println(obj.AddWorkingDays("yyyy-MM-dd", "2010-02-06", 1));
		/*
		try{
		int lValue = obj.DateTimeCompare("08:00:00", "09:00:00", "HH:mm:ss");
		System.out.println(lValue);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		//System.out.println(obj.CurrTimeStamp("HH"));
		//System.out.println(obj.SubWorkingDays("yyyy-MM-dd", "2017-12-01 12:12:12", 10));
		//System.out.println(obj.SubWorkingDays("yyyy-MM-dd", obj.CurrTimeStamp("yyyy-MM-dd"), 10));

		//System.out.println(new SimpleDateFormat("EEEEE").format(new Date()));

	}
}
