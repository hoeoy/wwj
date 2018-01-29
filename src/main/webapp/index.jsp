<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆页面</title>
</head>
<body>

<form action="/Api/Goods/uploadImg" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="Submit"/>

</form>

<form action="/Api/Goods/insertUser" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/> <input type="submit" value="Submit"/></form>

<form action="/Api/MealAllowance/ULE" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/> <input type="submit" value="Submit"/></form>
<form action="/Api/staff/ULE" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/> <input type="submit" value="Submit"/></form>
</body>
</html>