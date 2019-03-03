<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<div id="container">
	
	<div id="navheader">
		<a href="/"><img src="/images/Flightpub.png" alt="Flightpub" height="75"></a>
		<a href="/" class="home">Home</a>
	</div>
	
	<div id="body">
		<div class="loginform">
			<form action="/login/authenticate" method="post">
				<h2>Login</h2><br>

				<p class="error">${error}</p>

				<div class="group">
					<label for="username">Username</label>
					<input id="username" type="text" name="username" REQUIRED>
				</div><br>
				
				<div class="group">
					<label for="password">Password</label>
					<input id="password" type="password" name="password" REQUIRED>
				</div><br>
				<input type="submit" name="login" value="Login">
			</form>
			<a id="register" href="/register">Register An Account</a>
		</div>
	</div>

	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>	
</div>

</body>
</html>