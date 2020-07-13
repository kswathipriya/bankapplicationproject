<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
			<form action="user">
				Enter account number-<input type="text" name="accno"/><br>
				Enter original password-<input type="text" name="pass"/><br>
				Enter new password-<input type="text" name="newpass"/><br>
				Confirm new password-<input type="text" name="cnf_newpass"/><br>

				<input type="submit" name="userbtn" value="change_password">
			</form>
</body>
</html>