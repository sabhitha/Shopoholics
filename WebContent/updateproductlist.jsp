<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id = request.getParameter("id");
String name = request.getParameter("nm");
String desc = request.getParameter("desc");
int stock = Integer.parseInt(request.getParameter("stock"));
int price = Integer.parseInt(request.getParameter("price"));
int offer = Integer.parseInt(request.getParameter("offer"));

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopaholics</title>
</head>

<body>
<form action="UpdateLController">
Product id:<input type = "text" name = "id" value = "<%=id%>" readonly><br>
Product Name:<input type = "text" name = "name" value = "<%=name %>"><br>
Description:<input type = "textarea" name = "desc" value = "<%=desc %>"><br>
Stock:<input type = "text" name = "stock" value = "<%=stock %>">pieces<br>
Price:<input type = "text" name = "price" value = "<%=price %>">Rs<br>
Offer:<input type = "text" name = "offer" value = "<%=offer %>">%<br>
<input type = "submit" value = "UpdateContact">
</form>
</body>
</html>