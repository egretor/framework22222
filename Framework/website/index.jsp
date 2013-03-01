<%@page import="unknown.website.listener.ContextListener"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<%=ContextListener.getWebsite()%>/resource/javascript/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		alert($.version);
	});
</script>
</head>
<body>
	<a href="<%=ContextListener.getWebsite()%>/manage/maUser.action">user</a>
</body>
</html>