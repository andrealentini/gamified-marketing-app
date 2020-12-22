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
		Calendar c1 = GregorianCalendar.getInstance();
		Calendar c2 = GregorianCalendar.getInstance();
		c1.setTime(d1);
		c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), 0, 0, 0);
		c2.setTime(d2);
		c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), 0, 0, 0);
		c1.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		return c1.before(c2) ? -1 : c1.after(c2) ? 1 : 0;
	}

}
