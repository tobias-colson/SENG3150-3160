<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Account Details</title>
</head>
<body>

<div id="container">

	<jsp:include page="Navbar.jsp" />

	<div id="body">

		<div id="personaldetails">
			<h1>Register New Account</h1>
			<form action="/changedetails/confirm" method="post">
				<div class="group">
					<span class="formheading">Enter Your Details</span><br>
					<label for="title">Title</label>
					<select id="title" name="title">
						<option value="MR">Mr</option>
						<option value="MS">Ms</option>
						<option value="MRS">Mrs</option>
						<option value="MISS">Miss</option>
						<option value="MASTER">Master</option>
						<option value="DR">Dr</option>
						<option value="PROF">Prof</option>
					</select>
				</div>
				<div class="group">
					<label for="fname">First Name</label>
					<input type="text" id="fname" name="firstname">
				</div>
				<div class="group">
					<label for="lname">Last Name</label>
					<input type="text" id="lname" name="lastname">
				</div>
				<div class="group">
					<label for="fname">Phone Number</label>
					<input type="text" id="phonenum" name="phonenum">
				</div>
				<div class="group">
					<label for="dob">Date of Birth</label>
					<input type="date" id="dob" name="dob">
				</div><br><br>

				<div class="group">
					<span class="formheading">Email and Password</span><br><br>
					<label for="email">Email Address</label>
					<input type="text" id="email" name="email"><br>
				</div><br>

				<div class="group">
					<label for="password">Password</label>
					<input type="password" id="password" name="password">
					<label for="password2">Confirm Password</label>
					<input type="password" id="password2" name="password2">
				</div><br>

				<div class="group">
					<input type="submit" name ="updatedetails" value="Complete Update">
				</div>
			</form>
		</div>
	</div>

	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>
</div>

</body>

</html>