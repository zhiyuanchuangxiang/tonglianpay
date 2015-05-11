package com.zycx.frame.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-22 下午11:52:39 
 * 类说明 
 */
public class DaoUtil {
	
	public static String getCountSql(String s) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT COUNT(1) COUNT_NUM FROM (");
		sbf.append(s);
		sbf.append(")");
		String sf = sbf.toString();
		StringBuffer sb = new StringBuffer();
		String[] delims = { " ", "\t", "\n", "\r", "\f", ",", "(+)", "||",
				"**", "<=", ">=", "!=", "<>", ":=", "<", ">", "=", "+", "-",
				"*", "/", "(", ")" };
		List tokens = tokenize(s, delims);
		String token;

		if ((tokens.size() < 4)
				|| (tokens.get(0).toString().compareToIgnoreCase("SELECT") != 0)
				|| (tokens.get(1).toString().compareToIgnoreCase("DISTINCT") == 0)) {
			return sf;
		}

		int i = 0;
		int len = tokens.size();
		int subLevel = 0;
		int state = 0;
		int idxFrom = -1;
		int idxWhere = -1;
		int idxGroup = -1;
		int idxOrder = -1;
		int idxUnion = -1;
		while (i < len) {
			token = (String) tokens.get(i);
			if (token.equals("(")) {
				subLevel++;
			} else if (token.equals(")")) {
				subLevel--;
			} else if ((state == 0) && (subLevel == 0)
					&& (token.compareToIgnoreCase("FROM") == 0)) {
				idxFrom = i;
				state = 1;
			} else if ((state == 1) && (subLevel == 0)
					&& (token.compareToIgnoreCase("WHERE") == 0)) {
				idxWhere = i;
			} else if ((state == 1) && (subLevel == 0)
					&& (token.compareToIgnoreCase("ORDER") == 0)) {
				idxOrder = i;
			} else if ((state == 1) && (subLevel == 0)
					&& (token.compareToIgnoreCase("GROUP") == 0)) {
				idxGroup = i;
			} else if ((state == 1) && (subLevel == 0)
					&& (token.compareToIgnoreCase("UNION") == 0)) {
				idxUnion = i;
			} else if ((state == 0) && token.equals("?")) {
				// like such case:
				// SELECT ? stat_month,user_id,......
				// fallback
				return sf;
			}
			i++;
		}
		if ((idxFrom < 0) || (idxGroup > 0) || (idxUnion > 0)) {
			return sf;
		}
		sb.append("SELECT ");
		for (i = 0; i < idxFrom; i++) {
			token = (String) tokens.get(i);
			if (token.startsWith("/*")) {
				sb.append(" ");
				sb.append(token);
			}
		}
		sb.append(" COUNT(1) COUNT_NUM ");
		for (i = idxFrom; i < len; i++) {
			token = (String) tokens.get(i);
			if (!token.startsWith("(")) sb.append(" ");
			sb.append(token);
		}
		return sb.toString();
	}

	
	static List tokenize(String s, String[] delims) {
		List ls = new ArrayList();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbRemark = new StringBuffer();
		StringBuffer sbQuote = new StringBuffer();
		boolean quote = false;
		boolean remark = false;
		int len = s.length();
		int i = 0;
		String delim = "";
		while (i < len) {
			char c = s.charAt(i);
			if (quote) {
				if (c == '\'') {
					if (i + 1 < len) {
						char cn = s.charAt(i + 1);
						if (cn == '\'') {
							sbQuote.append(c);
							sbQuote.append(cn);
							i++;
						} else {
							sbQuote.append(c);
							checkAddString(ls, sbQuote);
							sbQuote = new StringBuffer();
							quote = false;
						}
					} else {
						sbQuote.append(c);
						checkAddString(ls, sbQuote);
						sbQuote = new StringBuffer();
						quote = false;
					}
				} else {
					sbQuote.append(c);
				}
			} else {
				if (remark) {
					if (c == '*') {
						if (i + 1 < len) {
							char cn = s.charAt(i + 1);
							if (cn == '/') {
								sbRemark.append(c);
								sbRemark.append(cn);
								checkAddString(ls, sbRemark);
								sbRemark = new StringBuffer();
								remark = false;
								i++;
							} else {
								sbRemark.append(c);
							}
						} else {
							sbRemark.append(c);
						}
					} else {
						sbRemark.append(c);
					}
				} else {
					if (c == '\'') {
						checkAddString(ls, sb);
						sb = new StringBuffer();
						sbQuote.append(c);
						quote = true;
					} else if ((c == '/') && (i + 1 < len)
							&& (s.charAt(i + 1) == '*')) {
						checkAddString(ls, sb);
						sb = new StringBuffer();
						sbRemark.append("/*");
						remark = true;
						i++;
					} else {
						int sep = 0;
						for (int j = 0; j < delims.length; j++) {
							if (s.substring(i).startsWith(delims[j])) {
								sep = delims[j].length();
								delim = delims[j];
								break;
							}
						}
						if (sep > 0) {
							checkAddString(ls, sb);
							checkAddString(ls, delim);
							sb = new StringBuffer();
							i += sep - 1;
						} else {
							sb.append(c);
						}
					}
				}
			}
			i++;
		}
		checkAddString(ls, sb);
		if (quote) {
			throw new RuntimeException("quoted string not properly terminated");
		}
		if (remark) {
			throw new RuntimeException("remark not properly terminated");
		}
		return ls;
	}
	
	static void checkAddString(List ls, Object s) {
		if (s.toString().trim().length() > 0) {
			ls.add(s.toString().trim());
		}
	}
	
	
	public static String getPagingSql(String sql, Map<String,Object> param, int start, int end) throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("select * from (select row_.*, rownum rownum_ from (");
		str.append(sql);
		str.append(") row_ where rownum <= :MAX_NUM) where rownum_ > :MIN_NUM");
				
		param.put("MIN_NUM", String.valueOf(start));
		param.put("MAX_NUM", String.valueOf(end));
		
		return str.toString();
	}
	
	public static void main(String[] args)
	{
		System.out.println(DaoUtil.getCountSql("SELECT * FROM DUAL WHERE TEST=:TEST"));
	}

}
