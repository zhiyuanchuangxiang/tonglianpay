<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../public/header.inc.jsp"%>
<html>
<head>
<s:head />
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<title>Album Details</title>
</head>
<body>
	<div>${title}</div>
	<div><s:property value="param.name" /></div>
	<div><s:property value="_ASSIGN_.test111" /></div>
	<span>el标签:</span><input  type="text" value="${_ASSIGN_.test111}"/><br/>
	<span>嵌套:<input  type="text" value="<s:property value="_ASSIGN_.test111" />"/><br/>
	<span>对象函数调用:<span>${_BASE_.hasPermit('test')} </span><br/>
	<span>访问静态方法:</span><span><s:property value="@com.zycx.frame.util.DPUtil@getCurrentSeconds()"/></span><br/>
	<span>访问静态常量:</span><span><s:property value="@com.zycx.frame.util.DPUtil@regexDouble"/></span><br/>
	<a href="<s:url action="Demo"></s:url>">url标签测试</a><br/>
	<a href="<s:url action="Member"></s:url>">1111111</a><br/>
	<span>EL:</span><span>${_ASSIGN_.test111}</span>
	<ul>
		<s:iterator value="_ASSIGN_.forumList" status="statu" id="item">  
		<li>
			<s:property value="#statu.index"/>
		    <s:property value="#item.FORUMID" />  
		    <s:property value="#item.FORUMNAME" />  
		    <s:property value="#item.FORUMDESC" />  
			
		</li>
		</s:iterator>  
	</ul>
	
	
	
</body>