<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
</head>
<body>
<div><input type="button" value=" Back " onclick="location.href='index.jsp';"  /></div>
<%com.donjai.Main main = new com.donjai.Main(request,response); 
List<String> result = main.getResult();


%>
<ul>
<%for(String rs : result){ %>
	<li><%=rs %></li>
<%} %>
</ul>
</body>
</html>