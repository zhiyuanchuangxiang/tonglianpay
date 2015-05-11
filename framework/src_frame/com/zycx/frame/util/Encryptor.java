package com.zycx.frame.util;
public class Encryptor {

	/**
	 * Method fnEncrypt. 加密的静态方法系统加密算法
	 * 
	 * @param InputPwd
	 *            第一加密参数六位长在本系统中为要加密的明码密码
	 * @param ParaPwd
	 *            第二加密参数九位长在本系统中为'00'+linkage
	 * @return String 加密后的密码返回值. 成功返回密码字符串.失败返回NULL. 员工加密 ： 加密因子使用“00linkage”
	 *         移动用户186密码加密 ： 加密因子为USER_ID后9位
	 */

	public static String fnEncrypt(String InputPwd, String ParaPwd) {
		/* 定义初始化变量 */
		long bytes = 6, retlen = 6;
		long randSeed1 = 23456;
		long randSeed2 = 31212;
		long randSeed3 = 1029;
		long modSeed = 32768;
		long mm[] = new long[101];
		char tempChar = '\0';
		char tempChar1 = '\0';
		char tempChar2 = '\0';
		String cmm = "", pwd = "";
		String result = "";

		/* 加密过程 */
		if (InputPwd == null || InputPwd.length() <= 0) {
			return null;
		}
		if (ParaPwd == null || ParaPwd.length() <= 0) {
			return null;
		}
		pwd = InputPwd + ParaPwd;

		long longtmp = 0;
		int length = pwd.length();
		for (int i = 1; i <= length; i++) {
			tempChar = pwd.charAt(i - 1);
			longtmp = (long) tempChar;
			randSeed1 = (randSeed1 + longtmp * i) % modSeed;
			randSeed2 = ((randSeed2 + longtmp * (length - i)) % (modSeed / 4)) * 2;
			randSeed3 = ((randSeed3 + longtmp * longtmp) % (modSeed / 4)) * 2 + 1;
		}

		InputPwd = pwd;
		if (bytes > 10) {
			bytes = 10;
		}
		length = InputPwd.length(); // 输入密码的长度.
		/* 保证输入密码位bytes位 */
		if (length > bytes) {
			// InputPwd = InputPwd.substring(0, (int) bytes);
		} else {
			for (length = length + 1; length <= bytes; length++) {
				randSeed1 = (randSeed1 * randSeed2 + randSeed3) % modSeed;
				longtmp = randSeed1 % 126;
				if (longtmp < 33) {
					longtmp = 'A' + longtmp;
				}
				InputPwd += (char) longtmp;
			}
		}

		length = ParaPwd.length(); // 第二密参的长度
		/* 保证第二密参的长度也为bytes位 */
		if (length > bytes) {
			// ParaPwd = ParaPwd.substring(0, (int) bytes);
		} else {
			for (length = length + 1; length <= bytes; length++) {
				randSeed1 = (randSeed1 * randSeed2 + randSeed3) % modSeed;
				longtmp = randSeed1 % 126;
				if (longtmp < 33) {
					longtmp = 'A' + longtmp;
				}
				ParaPwd += (char) longtmp;
			}
		}

		/* mm数组的赋值 */
		longtmp = (randSeed1 * randSeed2 + randSeed3) % modSeed;
		for (int j = 1; j <= bytes; j++) {
			for (int k = 1; k <= bytes; k++) {
				tempChar1 = InputPwd.charAt(j - 1);
				tempChar2 = ParaPwd.charAt(j - 1);
				longtmp = (longtmp * randSeed1 + tempChar1 * tempChar2 * j)
						% modSeed;
				if (longtmp >= modSeed / 2) {
					randSeed1 = (randSeed1 * randSeed2 + randSeed3) % modSeed;
					mm[(int) (randSeed1 % (bytes * bytes))] = randSeed1;
				} else {
					randSeed1 = (randSeed1 * (randSeed3 + 1) + randSeed2 + 1)
							% modSeed;
					mm[(int) (randSeed1 % (bytes * bytes))] = randSeed1;
				}
			}
		}

		for (int k = (int) (bytes * bytes); k >= 1; k--) {
			if (k > 1) {
				mm[k - 1] += (mm[k] / 256);
			}
		}

		for (int k = 1; k <= bytes * bytes; k++) {
			randSeed1 = (randSeed1 * randSeed1) % modSeed;
			if (mm[k] == 0) {
				mm[k] = randSeed1;
			}
		}

		/* 核心加密算法 */
		for (int k = 1; k <= bytes; k++) {
			for (int i = 1; i <= bytes; i++) {
				tempChar1 = InputPwd.charAt(i - 1);
				tempChar2 = ParaPwd.charAt(k - 1);
				longtmp = (mm[(int) (i + (k - 1) * bytes)] * (int) tempChar2 * (int) tempChar1) % 62;
				if (longtmp < 10) {
					cmm += (char) (longtmp + (int) '0');
				} else {
					if (longtmp < 36) {
						cmm += (char) (longtmp - 10 + (int) 'a');
					} else {
						if (longtmp < 62) {
							cmm += (char) (longtmp - 36 + (int) 'A');
						} else {
							cmm += "_";
						}
					}

				}

			}

		}

		result = cmm.substring(0, (int) retlen);
		return result;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println(Encryptor.fnEncrypt("123456", "00linkage"));
		
	}
}