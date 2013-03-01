<%@page import="unknown.website.listener.ContextListener"%>
<%@page import="unknown.website.manage.business.MaUserBusiness"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title abc</title>
</head>
<body>
	<%
		out.print(MaUserBusiness.a1);
		out.print("<br />");
		out.print(ContextListener.getWebsite());
		out.print("<br />");
		out.print(ContextListener.getWebSiteDirectory());
		out.print("<br />");
	%>abc
</body>
</html>