package com.tawasul.web.util;

import java.util.Random;

public class SystemUtil {

	public static String generateOtp(int n) {

		Random random = new Random();
		int min = (int) Math.pow(10, n - 1);
		int max = (int) Math.pow(10, n) - 1;

		return String.valueOf(random.nextInt(max - min + 1) + min);

	}

}
