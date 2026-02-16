'

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width , initial-scale= 1.0" >
<title>login</title>
<style>
	body{
		font-family: Arial,sans-serif;
		background-color:#f4f4f4;
		text-align:center;
		margin:50px;
	}
	div{
	background-color:#B8E2B0;
	padding:20px;
	margin:auto;
	width:300px;
	border-radius:20px;
	box-shadow:0px 4px 8px rgba(0,0,0,0.2);
	}
	label {
    font-weight: bold;
    color: #333;
    display: block;
    margin-top: 10px;
	}
	input[type="text"], input[type="password"] {
    width: 90%;
    padding: 10px;
    margin: 10px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
	}
	input[type="submit"]{
    background-color: #84B067;
    color: white;
    border: none;
    padding: 10px;
    width: 100%;
    cursor: pointer;
    border-radius: 4px;
    font-size: 16px;
	}

	input[type="submit"]:hover {
    background-color: #0056b3;
	}

</style>
</head>
<body >
<div id="one">
<form action="logd" method="post">

    <label>User Name:</label> <input type="text" name="username"><br>
    <label>Password:</label> <input type="password" name="password"><br>
    <input type="submit" value="Login">
	
</form>
<form action="Sign.html">
	<label>New User?</label><input type="submit" value="Register here !!">
</form>
</div>
	
</body>
</html>