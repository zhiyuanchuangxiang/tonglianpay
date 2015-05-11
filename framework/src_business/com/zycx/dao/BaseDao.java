package com.zycx.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zycx.frame.dao.DaoUtil;
import com.zycx.frame.struts.Pagination;

/** 
 * @author  wangyc  E-mail: wheel115@163.com 
 * @version 创建时间：2015-4-15 上午12:30:19 
 * 类说明 
 */
public  class BaseDao
{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource)
    {
        if(jdbcTemplate == null || dataSource != jdbcTemplate.getDataSource())
        {
            jdbcTemplate = new JdbcTemplate(dataSource);
            namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }
    }
	
	protected List<Map<String,Object>> excuteQueryList(String sql,Map<String,Object> paramMap)
	{
		List<Map<String, Object>> result  =  this.namedParameterJdbcTemplate.queryForList(sql, paramMap);
		return result;
	}
	
	
	protected List<Map<String,Object>> excuteQueryList(String sql,Map<String,Object> paramMap,Pagination pg) throws Exception
	{
		if(pg == null)
		{
			return this.excuteQueryList(sql, paramMap);
		}
		if(paramMap == null)
		{
			paramMap = new HashMap<String,Object>();
		}
		int count = this.getCount(sql, paramMap);
		pg.setCount(count);
		int start = (pg.getPage()-1)*pg.getRowNum();
		int end   = start + pg.getRowNum();
		String pageSql = DaoUtil.getPagingSql(sql,paramMap,start,end);
		List<Map<String, Object>> result  =  this.namedParameterJdbcTemplate.queryForList(pageSql, paramMap);
		return result;
	}
	
	protected int getCount(String sql,Map<String,Object> paramMap)
	{
		String countSql = DaoUtil.getCountSql(sql);
		List<Map<String,Object>> countList = this.namedParameterJdbcTemplate.queryForList(countSql, paramMap);
		return Integer.parseInt(countList.get(0).get("COUNT_NUM").toString());
	}
	
	
	
	protected int excuteUpdate(String sql,Map<String,Object> paramMap)
	{
		return this.namedParameterJdbcTemplate.update(sql,paramMap);
	}
	
	protected int[] excuteBatchUpdate(String sql, Map<String, ?>[] batchValues)
	{
		return this.namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
	}
	
	protected int[] excuteBatchUpdate(String sql, List<Map<String,?>> valueList)
	{
		Map<String,?>[] batchValues = (Map<String, ?>[]) valueList.toArray();
		return this.namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
	}
	
}
