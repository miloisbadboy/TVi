/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.lichvansu.helper;

/**
 *
 * @author DELL
 */
public class MyDateHelper extends DateHelper {

	private static double TZ = 7.00;
	public final static int INVALID_RESULT = Integer.MIN_VALUE;

	/**
	 * Kiểm tra một năm có phải nhuận hay không.
	 *
	 * @param year	Giá trị năm.
	 * @return	Trả về true nếu là năm nhuận, false nếu như không phải.
	 */
	public static boolean isSolarLeapYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}

	/**
	 * Lấy số ngày trong tháng dương lịch.
	 *
	 * @param month	Giá trị tháng. (1 - 12)
	 * @param year	Giá trị năm.
	 * @return	Số ngày trong tháng, INVALID_RESULT nếu như có lỗi xảy ra.
	 */
	public static int countSolarDates(int month, int year) {
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				if (isSolarLeapYear(year)) {
					return 29;
				} else {
					return 28;
				}
		}
		return INVALID_RESULT;
	}

	/**
	 * Lấy giá trị ngày trong tuần.
	 *
	 * @param date	Giá trị ngày trong tháng. (1 - 31)
	 * @param month	Giá trị tháng. (1 - 12)
	 * @param year	Giá trị năm.
	 * @return	0, 1..6 tương ứng với thứ hai, thứ ba.. chủ nhật. INVALID_RESULT
	 * nếu ngày/tháng/năm không hợp lệ.
	 */
	public static int getSolarDayOfWeek(int date, int month, int year) {
		if (!isValidSolarDate(date, month, year)) {
			return INVALID_RESULT;
		}

		int jd = jdFromDate(date, month, year);

		return jd % 7;
	}

	/**
	 * Kiểm tra xem năm âm lịch có phải nhuận hay không.
	 *
	 * @param year	Giá trị năm âm lịch.
	 * @return	Trả về tháng nhuận (1 - 12) nếu đúng, INVALID_RESULT nếu sai.
	 */
	public static int isLunarLeapYear(int year) {
		int temp = MyDateHelper.getLunarMonth11(year, TZ);
		int tempM1 = MyDateHelper.getLunarMonth11(year - 1, TZ);
		int z = tempM1 - temp;

		if (Math.abs(z) > 355) {
			int o = MyDateHelper.getLeapMonthOffset(tempM1, TZ);
			if (o >= 3) {
				return o - 2;
			}
		}

		z = temp - MyDateHelper.getLunarMonth11(year + 1, TZ);

		if (Math.abs(z) > 355) {
			int o = MyDateHelper.getLeapMonthOffset(temp, TZ);
			if (o < 3) {
				return 10 + o;
			}
		}

		return INVALID_RESULT;
	}

	/**
	 * Lấy số ngày trong tháng âm lịch.
	 *
	 * @param month	Giá trị tháng âm lịch. (1 - 12)
	 * @param year	Giá trị năm âm lịch.
	 * @return	Số ngày trong tháng, INVALID_RESULT nếu như gặp lỗi.
	 */
	public static int countLunarDates(int month, int year) {
		if (month < 1 || month > 12) {
			return INVALID_RESULT;
		}

		int[] solarDate = convertLunar2Solar(1, month, year, 0, TZ);
		int jd = jdFromDate(solarDate[0], solarDate[1], solarDate[2]);
		jd += 29;
		solarDate = jdToDate(jd);
		int[] lunarDate = convertSolar2Lunar(solarDate[0], solarDate[1], solarDate[2], TZ);

		if (lunarDate[1] != month) {
			return 29;
		}
		return 30;
	}

	/**
	 * Lấy số ngày trong tháng nnhuaanjtrong một năm nhuận.
	 *
	 * @param year	Giá trị năm.
	 * @return	Số ngày nhuận, INVALID_RESULT nếu như không phải là năm nhuận.
	 */
	public static int countLunarLeapDates(int year) {
		int month = isLunarLeapYear(year);
		if (month == INVALID_RESULT) {
			return INVALID_RESULT;
		}

		int[] solarDate = convertLunar2Solar(1, month, year, 1, TZ);
		int jd = jdFromDate(solarDate[0], solarDate[1], solarDate[2]);
		jd += 29;
		solarDate = jdToDate(jd);
		int[] lunarDate = convertSolar2Lunar(solarDate[0], solarDate[1], solarDate[2], TZ);

		if (lunarDate[1] != month) {
			return 29;
		}
		return 30;
	}

	/**
	 * Tính Can Chi năm âm lịch. Can (0 - 9): Giáp, Ất, Bính, Đinh, Mậu, Kỷ,
	 * Canh, Tân, Nhâm, Quý. Chi (0 - 11): Tý, Sửu, Dần, Mão, Thìn, Tỵ, Ngọ,
	 * Mùi, Thân, Dậu, Tuất, Hợi.
	 *
	 * @param year	Giá trị năm âm lịch.
	 * @return	Mảng giá trị {can, chi} của năm âm lịch.
	 */
	public static int[] getLunarYearTitle(int year) {
		int[] title = new int[2];
		title[0] = (year + 6) % 10;
		title[1] = (year + 8) % 12;

		return title;
	}

	/**
	 * Tính Can Chi tháng âm lịch. Can (0 - 9): Giáp, Ất, Bính, Đinh, Mậu, Kỷ,
	 * Canh, Tân, Nhâm, Quý. Chi (0 - 11): Tý, Sửu, Dần, Mão, Thìn, Tỵ, Ngọ,
	 * Mùi, Thân, Dậu, Tuất, Hợi.
	 *
	 * @param month	Giá trị tháng âm lịch.
	 * @param year	Giá trị năm âm lịch.
	 * @return	Mảng giá trị {can, chi} của tháng âm lịch, null nếu input không
	 * hợp lệ.
	 */
	public static int[] getLunarMonthTitle(int month, int year) {
		if (month < 1 || month > 12) {
			return null;
		}

		int[] title = new int[2];
		title[0] = (year * 12 + month + 3) % 10;
		title[1] = (month + 13) % 12;

		return title;
	}

	/**
	 * Tính Can Chi ngày âm lịch. Can (0 - 9): Giáp, Ất, Bính, Đinh, Mậu, Kỷ,
	 * Canh, Tân, Nhâm, Quý. Chi (0 - 11): Tý, Sửu, Dần, Mão, Thìn, Tỵ, Ngọ,
	 * Mùi, Thân, Dậu, Tuất, Hợi.
	 *
	 * @param date	Giá trị ngày âm lịch.
	 * @param month	Giá trị tháng âm lịch.
	 * @param year	Giá trị năm âm lịch.
	 * @return	Mảng giá trị {can, chi} của ngày âm lịch, null nếu input không
	 * hợp lệ.
	 */
	public static int[] getLunarDateTitle(int date, int month, int year, boolean isLeap) {
		int[] solarDate = convertLunar2Solar(date, month, year, isLeap);
		if (solarDate == null) {
			return null;
		}

		int jd = jdFromDate(solarDate[0], solarDate[1], solarDate[2]);
		int[] title = new int[2];
		title[0] = (jd + 9) % 10;
		title[1] = (jd + 1) % 12;

		return title;
	}

	/**
	 * Tính Can Chi ngày âm lịch. Can (0 - 9): Giáp, Ất, Bính, Đinh, Mậu, Kỷ,
	 * Canh, Tân, Nhâm, Quý. Chi (0 - 11): Tý, Sửu, Dần, Mão, Thìn, Tỵ, Ngọ,
	 * Mùi, Thân, Dậu, Tuất, Hợi.
	 *
	 * @param hour	Giờ. (0 - 23)
	 * @param date	Giá trị ngày âm lịch.
	 * @param month	Giá trị tháng âm lịch.
	 * @param year	Giá trị năm âm lịch.
	 * @return	Mảng giá trị {can, chi} của giờ âm lịch, null nếu input không hợp
	 * lệ.
	 */
	public static int[] getLunarHourTitle(int hour, int date, int month, int year, boolean isLeap) {
		if (hour < 0 || hour > 23) {
			return null;
		}

		int[] solarDate = convertLunar2Solar(date, month, year, isLeap);
		if (solarDate == null) {
			return null;
		}

		int jd = jdFromDate(solarDate[0], solarDate[1], solarDate[2]);
		int[] title = new int[2];
		title[0] = (jd * 24 - 3 + hour) / 2 % 10;
		title[1] = (hour + 25) / 2 % 12;

		return title;
	}

	/**
	 * @see http://www.informatik.uni-leipzig.de/~duc/sach/phongtuc/cau_112.html
	 *
	 * @param month
	 * @param year
	 * @param isLeap
	 * @return
	 */
	public static int[] getLunarDateStatusInMonth(int month, int year, boolean isLeap) {
		int[] dateTitle = getLunarDateTitle(1, month, year, isLeap);
		if (dateTitle == null) {
			return null;
		}

		int m = (month - 1) % 6;
		int dtFirst = dateTitle[1];
		int dmax = isLeap ? countLunarLeapDates(year)
			: countLunarDates(month, year);
		int[] result = new int[dmax];
		for (int i = 1; i <= dmax; i++) {
			int dt = (i + dtFirst - 1) % 12;

			if (dt == m * 2
				|| dt == m * 2 + 1
				|| dt == (5 + m * 2) % 12
				|| dt == (7 + m * 2) % 12) {
				result[i - 1] = 1;
			} else if (dt == (6 + m * 2) % 12
				|| dt == (3 + m * 2) % 12
				|| dt == (11 + m * 2) % 12
				|| dt == (9 + m * 2) % 12) {
				result[i - 1] = -1;
			} else {
				result[i - 1] = 0;
			}
		}

		return result;
	}

	public static int getLunarDateStatus(int date, int month, int year, boolean isLeap) {
		int[] dateTitle = getLunarDateTitle(date, month, year, isLeap);
		if (dateTitle == null) {
			return INVALID_RESULT;
		}

		int m = (month - 1) % 6;
		int dt = dateTitle[1];

		if (dt == m * 2
			|| dt == m * 2 + 1
			|| dt == (5 + m * 2) % 12
			|| dt == (7 + m * 2) % 12) {
			return 1;
		} else if (dt == (6 + m * 2) % 12
			|| dt == (3 + m * 2) % 12
			|| dt == (11 + m * 2) % 12
			|| dt == (9 + m * 2) % 12) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public static int[] getLunarHourStatusOnDate(int date, int month, int year, boolean isLeap) {
		int[] dateTitle = getLunarDateTitle(date, month, year, isLeap);
		if (dateTitle == null) {
			return null;
		}

		int d = (dateTitle[1] + 10) % 6;
		int[] result = new int[12];
		for (int i = 0; i < 12; i++) {
			int h = (i + 12 - (d * 2)) % 12;

			if (h == 0
				|| h == 1
				|| h == 4
				|| h == 5
				|| h == 7
				|| h == 10) {
				result[i] = 1;
			} else {
				result[i] = 0;
			}
		}

		return result;
	}

	/**
	 * Đổi ngày âm sang ngày dương.
	 *
	 * @param date	Giá trị ngày trong tháng.
	 * @param month	Giá trị tháng.
	 * @param year	Giá trị năm.
	 * @param isLeap	Ngày/tháng/năm nhuân hay không.
	 * @return Mảng chứa thông tin về ngày dương lịch {date, month, year}, trả
	 * về null nếu như đầu vào không hợp lệ.
	 */
	public static int[] convertLunar2Solar(int date, int month, int year, boolean isLeap) {
		if (!isValidLunarDate(date, month, year, isLeap)) {
			return null;
		}

		return DateHelper.convertLunar2Solar(date, month, year, isLeap ? 1 : 0, TZ);
	}

	/**
	 * Đổi ngày dương sang ngày âm
	 *
	 * @param date	Giá trị ngày trong tháng.
	 * @param month	Giá trị tháng.
	 * @param year	Giá trị năm.
	 * @return	Mảng chứa ngày âm lịch {date, month, year, (int)isLeap}, trả về
	 * null nếu như đầu vào không hợp lệ.
	 */
	public static int[] convertSolar2Lunar(int date, int month, int year) {
		if (!isValidSolarDate(date, month, year)) {
			return null;
		}

		return DateHelper.convertSolar2Lunar(date, month, year, TZ);
	}

	/**
	 * Kiểm tra nếu như đầu vào là ngày tháng năm dương lịch hợp lệ.
	 *
	 * @param date	Giá trị ngày.
	 * @param month	Giá trị tháng. (bỏ qua nếu không dương)
	 * @param year	Giá trị năm. (bỏ qua nếu không dương)
	 * @return	Trả về true nếu như ngày tháng năm là hợp lệ, sai trả về false.
	 */
	private static boolean isValidSolarDate(int date, int month, int year) {
		if (year > 0) {
			if (month < 1 || month > 12) {
				return false;
			} else {
				if (date < 1 || date > countSolarDates(month, year)) {
					return false;
				}
			}
		} else {
			if (month > 0) {
				if (date < 1 || date > countSolarDates(month, year)) {
					return false;
				}
			} else {
				if (date < 1 || date > 31) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Kiểm tra nếu như đầu vào là ngày tháng năm âm lịch hợp lệ.
	 *
	 * @param date	Giá trị ngày.
	 * @param month	Giá trị tháng. (bỏ qua nếu tháng và năm cùng không dương)
	 * @param year	Giá trị năm. (bỏ qua nếu tháng và năm cùng không dương)
	 * @param isLeap Là tháng/năm nhuân hay không.
	 * @return	Trả về true nếu như ngày tháng năm là hợp lệ, sai trả về false.
	 */
	private static boolean isValidLunarDate(int date, int month, int year, boolean isLeap) {
		if (year > 0 && month > 0) {
			if (month < 1 || month > 12) {
				return false;
			} else {
				if (isLeap) {
					if (isLunarLeapYear(year) != month) {
						return false;
					} else if (date < 1 || date > countLunarLeapDates(year)) {
						return false;
					}
					// return true;
				} else {
					if (date < 1 || date > countLunarDates(month, year)) {
						return false;
					}
				}
			}
		} else {
			if (date < 1 || date > 30) {
				return false;
			}
		}

		return true;
	}
}