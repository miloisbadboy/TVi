package com.chiemtinhapp.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatHelper {
	public final static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	public final static SimpleDateFormat fbFormatter = new SimpleDateFormat("MM/dd", Locale.US);
	public final static SimpleDateFormat displayFormatter = new SimpleDateFormat("dd/MM", Locale.US);
	
	// Return horoscope number from birthday
	public static int horoscopeNumber(Date birthday) {		
		int range = 29;
		Calendar calLower = Calendar.getInstance();
		Calendar calBirthday = Calendar.getInstance();

		calBirthday.setTime(birthday);
		calBirthday.set(Calendar.YEAR, 2012);

		calLower.set(2011, Calendar.DECEMBER, 22);

		for (int i = 1; i <= 12; i++) {
			if (i == 4 || i == 9) {
				range = 30;
			}
			if (i == 8) {
				range = 31;
			}
			if (i == 10) {
				range = 29;
			}
			if (i == 12) {
				range = 28;
			}
			if (calBirthday.after(calLower)) {
				calLower.add(Calendar.DATE, range);
				if (calBirthday.before(calLower)) {
					return i;
				}
			}
			else {
				calLower.add(Calendar.DATE, range);
			}
			calLower.add(Calendar.DATE, 1);
		}
		return 0;
	}
}
