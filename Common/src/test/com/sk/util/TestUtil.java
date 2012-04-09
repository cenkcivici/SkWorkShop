package com.sk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static String toStringFormat(Date date) {
		if (date != null) {
			return dateFormatter.format(date);
		} else {
			return null;
		}
	}

}
