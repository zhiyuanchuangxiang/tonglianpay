package com.zycx.frame.util.parser;

import org.apache.log4j.Logger;
import org.jdom2.Element;

import com.zycx.frame.util.StringUtils;



public class Validate {
	
	protected static Logger log = Logger.getLogger(Validate.class);
	
	/**
	 * check length
	 * @param value
	 * @param length
	 * @param desc
	 * @return String
	 */
	public static String checkLength(String value, int length, String desc) {
		if (!"".equals(value) && StringUtils.getLength(value) != length) {
			return desc + "长度必须为" + length + ";";
		}
		return "";
	}
	
	/**
	 * check min length
	 * @param value
	 * @param length
	 * @param desc
	 * @return String
	 */
	public static String checkMinLength(String value, int length, String desc) {
		if (!"".equals(value) && StringUtils.getLength(value) < length) {
			return desc + "最小长度不能低于" + length + ";";
		}
		return "";
	}
	
	/**
	 * check max length
	 * @param value
	 * @param length
	 * @param desc
	 * @return String
	 */
	public static String checkMaxLength(String value, int length, String desc) {
		if (!"".equals(value) && StringUtils.getLength(value) > length) {
			return desc + "最大长度不能超过" + length + ";";
		}
		return "";
	}
	
	/**
	 * check text
	 * @param value
	 * @param desc
	 * @return String
	 */
	public static String checkText(String value, String desc) {
		if (value == null || "".equals(value)) {
			return desc + "不能为空;";
		}
		return "";
	}
	
	/**
	 * check numeric
	 * @param value
	 * @param format
	 * @param desc
	 * @return String
	 */
	public static String checkNumeric(String value, String format, String desc) {
		String expression = "[+-]?\\d+";
		String checkdesc = "必须为整数";
		if (format != null && format.indexOf(".") != -1) {
			expression = "[+-]?\\d+(\\.\\d{1," + (format.length() - format.indexOf(".") - 1) + "})?";
			checkdesc = "必须为数字格式(" + format + ")";
		}
		if (!"".equals(value) && !StringUtils.isMatches(value, expression)) {
			return desc + checkdesc + ";";
		}
		return "";
	}
	
	/**
	 * check date
	 * @param value
	 * @param format
	 * @param desc
	 * @return String
	 */
	public static String checkDate(String value, String format, String desc) {
		String expression = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})";
		
		if ("yyyy-MM-dd".equals(format)) {
			expression = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})";
		} else if ("yyyy-MM-dd HH:mm".equals(format)) {
			expression = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2}) (\\d{1,2}):(\\d{1,2})";
		} else if ("yyyy-MM-dd HH:mm:ss".equals(format)) {
			expression = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})";
		} else if ("HH:mm:ss".equals(format)) {
			expression = "(\\d{1,2})(:)?(\\d{1,2})\\2(\\d{1,2})";
		} else if ("yyyy".equals(format)) {
			expression = "(\\d{1,4})";
		} else if ("yyyy-MM".equals(format)) {
			expression = "(\\d{1,4})(-|\\/)(\\d{1,2})";
		} else if ("HH".equals(format)) {
			expression = "(\\d{1,2})";
		} else if ("HH:mm".equals(format)) {
			expression = "(\\d{1,2})(:)?(\\d{1,2})";
		} else if ("yyyy-MM-dd HH".equals(format)) {
			expression = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2}) (\\d{1,2})";
		}
		
		if (!"".equals(value) && !StringUtils.isMatches(value, expression)) {
			return desc + "必须为时间格式(" + format + ");";
		}
		
		return "";
	}
	
	/**
	 * check data source
	 * @param value
	 * @param datasrc
	 * @param desc
	 * @return String
	 */

	/**
	 * verify cell
	 * @param bd
	 * @param cell
	 * @param value
	 * @return String
	 * @throws Exception
	 */
	public static String verifyCell(Element cell, String value) throws Exception {
		StringBuffer error = new StringBuffer();
		
		String type = cell.getAttributeValue("type");
		String desc = cell.getAttributeValue("desc");
		String nullable = cell.getAttributeValue("nullable");
		String equsize = cell.getAttributeValue("equsize");
		String minsize = cell.getAttributeValue("minsize");
		String maxsize = cell.getAttributeValue("maxsize");
		String format = cell.getAttributeValue("format");
		String datasrc = cell.getAttributeValue("datasrc");
		
		if (nullable != null && "no".equals(nullable)) {
			error.append(checkText(value, desc));
		}
		if (equsize != null) {
			error.append(checkLength(value, Integer.parseInt(equsize), desc));
		}
		if (minsize != null) {
			error.append(checkMinLength(value, Integer.parseInt(minsize), desc));
		}
		if (maxsize != null) {
			error.append(checkMaxLength(value, Integer.parseInt(maxsize), desc));
		}
		if (ExcelParser.CELL_TYPE_DATETIME.equals(type)) {
			if (format != null) {
				error.append(checkDate(value, format, desc));
			}
		}
		if (ExcelParser.CELL_TYPE_NUMERIC.equals(type)) {
			error.append(checkNumeric(value, format, desc));
		}
	
		
		return error.toString();
	}
	

}