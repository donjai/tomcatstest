<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Good Number</title>
<style>
label{
	width:150px;
}
</style>
</head>
<body>
<form action="result.jsp" method="POST" >

<label>Prefix : </label><input type="text" value="8กถ" name="prefix" /> <input type="checkbox" value="1" name="ignore_prefix" checked /> Ignore Prefix<br/>
<label>Postfix : </label><input type="text" value="" name="postfix" /><br/>
<label>Start Number : </label><input type="number" value="1000" name="start_num" /><br/>
<label>End Number : </label><input type="number" value="9999" name="end_num" /><br/>
<label>Good Number Of Birthday : </label><select name="good_number" >
<option value="2,3,4,7,5,8" >เกิดวันอาทิตย์</option>
<option value="3,4,7,5,8,6" selected >เกิดวันจันทร์</option>
<option value="4,7,5,8,6,1" >เกิดวันอังคาร</option>
<option value="7,5,8,6,1,2" >เกิดวันพุธกลางวัน</option>
<option value="8,6,1,2,3,4" >เกิดวันพฤหัส</option>
<option value="1,2,3,4,7,5" >เกิดวันศุกร์</option>
<option value="5,8,6,1,2,3" >เกิดวันเสาร์</option>
<option value="6,1,2,3,4,7" >เกิดวันพุธกลางคืน</option>
</select><br/>

<label >Level : </label><select name="level">
<option value="-1" > All </option>
<option value="0" > Bad </option>
<option value="1" > Good </option>
<option value="2" selected > Very Good </option>
</select>

<input type="submit" value=" ค้นหา " />
</form>
</body>
</html>