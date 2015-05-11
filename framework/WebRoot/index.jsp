<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String location = request.getRequestURL().toString();
if(location.endsWith("/")) location = location.substring(0, location.length() - 1);
location += "/login";
response.sendRedirect(location);
%>
