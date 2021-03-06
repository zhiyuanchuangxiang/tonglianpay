<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Album Details</title>
</head>
<body>
<div class="content"><b>Album Title :</b> <s:property
	value="title" /> <br>
	
<s:push value="artist">
	<b>Artist Name :</b>
	<s:property value="name" />
	<br>
	<b>Artist Bio :</b>
	<s:property value="bio" /> 
	<br>
</s:push> Song Details
<table class="songTable">
	<tr class="even">
		<td><b>Title</b></td>
		<td><b>Genre</b></td>
	</tr>
	<s:iterator value="songs" status="songStatus">
		<tr
			class="<s:if test="#songStatus.odd == true ">odd</s:if><s:else>even</s:else>">
			<td><s:property value="title" /></td>
			<td><s:property value="genre" /></td>
		</tr>
	</s:iterator>
</table>

<!-- 
<table class="songTable">
	<tr class="even">
		<td><b>Title</b></td>
		<td><b>Genre</b></td>
	</tr>
	<s:iterator value="mapList" id="column">
		<tr
			
			<td><s:property value="#column"/></td>
			<td></td>
		</tr>
	</s:iterator> 
</table>
-->

<!-- <s:iterator id="map" value="mapList" status="state">
	打印List
	<s:property value="mapList[#state.index]" /> <br>
	
	打印Map
	<s:iterator value="mapList[#state.index]">
		<s:property value="key" />: 
		<s:property value="value" />
		<br>
	</s:iterator>
</s:iterator> -->
 <s:iterator id="map" value="mapList" status="state"> 
 	<s:property value="#map.name"/>
 	<s:property value="#map.age"/>
   <!--  <s:iterator value="mapList[#state.index]" id="entry">    
           <s:property value="#entry"/>
        Key : <s:property value="value.name" />   
         value : <s:property value="value.age" />   
    </s:iterator> -->
</s:iterator> 
</div>
</body>
</html>
