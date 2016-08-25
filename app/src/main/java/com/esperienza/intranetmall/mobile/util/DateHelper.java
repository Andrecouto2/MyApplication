package com.esperienza.intranetmall.mobile.util;

//import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.text.format.Time;


import android.annotation.SuppressLint;

import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class DateHelper {

	    public static long lastdatesession=0;

	    public static void setLastdatesession()
		{
			lastdatesession=new Date().getTime();
		}


		public static Date toDate(String date){
			Date r = null;
			if(date != null && !date.equals("")){
				try {
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			        r = (Date)formatter.parse(date);
				} catch (Exception e) {
					Log.e(AppHelper.getAppName(), "Erro na conversao da data! error:"+e.getMessage());
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Log.d("DateHelper", "tentando parsear denovo");
                        r = (Date)formatter.parse(date);
                    } catch (ParseException e1) {
                        Log.e(AppHelper.getAppName(), "Erro na 2 tentativa de conversao da data! error:"+e.getMessage());
                    }
                }
			}
			return r;
		}
		public static Date toDateUS(String date){
			Date r = null;
			if(date != null && !date.equals("")){
				try {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					r = (Date) formatter.parse(date.replace("T", " "));
				} catch (Exception e) {
					Log.e(AppHelper.getAppName(), "Erro na conversao da data! error:"+e.getMessage());
				}
			}
			return r;
		}
		public static String toString(Date date){
			String s = null;
			try {
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				s = formatter.format(date);
			} catch (Exception e) {
				Log.e(AppHelper.getAppName(), "Erro na conversao da data para String! error:"+e.getMessage());
			}
			return s;
		}
		public static String toStringSQLServer(Date date){
			String s = null;
			try {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				s = formatter.format(date);
			} catch (Exception e) {
				Log.e(AppHelper.getAppName(), "Erro na conversao da data para String! error:"+e.getMessage());
			}
			return s;
		}
		public static String toStringDateTimeSQLServer(Date date){
			String s = null;
			try {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				s = formatter.format(date);
			} catch (Exception e) {
				Log.e(AppHelper.getAppName(), "Erro na conversao da data para String! error:"+e.getMessage());
			}
			return s;
		}
	public static Time parseHHMM(String time)
	{
		Time t = new Time();
		// if the format is not "HH:MM" we return null
		try
		{
			String hours = time.substring(0, 2);
			String minutes = time.substring(3);

			t.hour = Integer.parseInt(hours);
			t.minute = Integer.parseInt(minutes);
		}
		catch (Exception e) // TODO is this nice enough?
		{
			return null;
		}

		return t;
	}
		public static Integer getIdade(String dtnasc){
		        Calendar dateOfBirth = new GregorianCalendar();
		        dateOfBirth.setTime(DateHelper.toDate(dtnasc));
		        Calendar today = Calendar.getInstance();
		        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		        dateOfBirth.add(Calendar.YEAR, age);
		        if (today.before(dateOfBirth)) {
		            age--;
		        }
		        return age;
		}
		
		@SuppressLint("UseValueOf")
		public static Long getminutos(Integer minutos){
			return new Long((minutos * 60)*1000);
		}
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);

	}
	public static String addDays(Date date, int days)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return sdf.format(cal.getTime());
	}
	public static String currentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());

		return currentDate;
	}
	public static String currentDate2()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = sdf.format(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());

		return currentDate;
	}
	public static int diadasemanaatual()
	{
		int dayOfWeek = Calendar.getInstance(TimeZone.getTimeZone("GMT")).get(Calendar.DAY_OF_WEEK);

		return dayOfWeek;
	}
	public static String horaatual()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String retorno = sdf.format(cal.getTime());

		return retorno;
	}
	public static boolean comparelimithour(String horaatual,String horalimite)
	{
		boolean retorno=false;
		Date clTimeLimitHour = new Date();
		Date clTimeCurrent = new Date();
		SimpleDateFormat timeParser = new SimpleDateFormat("HH:mm", Locale.US);
		try
		{
			clTimeLimitHour = timeParser.parse(horalimite);
			clTimeCurrent = timeParser.parse(horaatual);
			if(clTimeCurrent.after(clTimeLimitHour))
				retorno=true;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return retorno;

	}
	public static boolean pastlimitdate(String dataatual,String datalimite)
	{
		boolean retorno=false;
		Date clDateLimitHour;
		Date clDateCurrent;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			clDateLimitHour =  dateFormat.parse(datalimite);
			clDateCurrent =  dateFormat.parse(dataatual);
			if(clDateCurrent.after(clDateLimitHour)||dataatual.equals(datalimite))
				retorno=true;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return retorno;

	}
	public static boolean pastlimitdate2(String dataatual,String datalimite)
	{
		boolean retorno=false;
		Date clDateLimitHour;
		Date clDateCurrent;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			clDateLimitHour =  dateFormat.parse(datalimite);
			clDateCurrent =  dateFormat.parse(dataatual);
			if(clDateCurrent.after(clDateLimitHour))
				retorno=true;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return retorno;

	}
	public static String convertDate(Long dateInMilliseconds) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTimeInMillis(dateInMilliseconds);
		return toString(cal.getTime());
	}

}
