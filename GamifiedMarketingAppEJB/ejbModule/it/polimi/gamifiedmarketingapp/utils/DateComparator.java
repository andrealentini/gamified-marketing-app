package it.polimi.gamifiedmarketingapp.utils;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateComparator implements Comparator<Date> {
	
	private static DateComparator instance;
	
	private DateComparator() {
		
	}
	
	public static DateComparator getInstance() {
		if (instance == null)
			instance = new DateComparator();
		return instance;
	}

	@Override
	public int compare(Date d1, Date d2) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(d1);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date date1 = calendar.getTime();
		calendar.setTime(d2);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		Date date2 = calendar.getTime();
		return date1.compareTo(date2);
	}

}
