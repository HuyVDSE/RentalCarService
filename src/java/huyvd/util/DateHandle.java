/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

/**
 *
 * @author BlankSpace
 */
public class DateHandle {

    public static String createCurrentDate() {
	Date currentDate = new Date();
	return new SimpleDateFormat("YYYY-MM-dd").format(currentDate);
    }

    //new way to convert to sql date
    public static java.sql.Date createCurDate() {
	LocalDate date = new LocalDate(DateTimeZone.UTC);
	return new java.sql.Date(date.toDateTimeAtStartOfDay().getMillis());
    }

    /*
	this method is use to prevent sql bug when get date from sql 2014 it will
	be odd 2 days
	==> this function will plus 2 days to make it normal
    
	This method use joda time library
     */
    public static java.sql.Date maintainDate(java.sql.Date curDate) {
	LocalDate date = new LocalDate(curDate, DateTimeZone.UTC);
	LocalDate tomorrow = date.plusDays(3);

	DateTime startOfDay = tomorrow.toDateTimeAtStartOfDay(DateTimeZone.UTC);

	return new java.sql.Date(startOfDay.getMillis());
    }
}
