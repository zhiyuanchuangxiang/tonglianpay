package com.zycx.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-29 下午7:54:01 
 * 类说明 
 */
public class StringUtils {
	
    public static int getLength(String value) {
		int length = 0;
		
		char[] chars = value.toCharArray();
    	for (int i=0; i<chars.length; i++) {
			if (((int) chars[i]) > 0x80) {
				length += 2;
			} else {
				length += 1;
			}
		}
		
		return length;
    }
    
    
	public static boolean isMatches(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static String encodeCharset(String charSet) throws Exception {
		return new String(charSet.getBytes("GBK"), "ISO8859_1");
	}

}
