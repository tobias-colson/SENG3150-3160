<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Confirm Booking</title>
</head>
<body>

<div id="container">

	<jsp:include page="Navbar.jsp" />

	<div id="body">

		<div id="personaldetails">
			<h1>Confirm Booking</h1>
			<form action="/payment">
				<span id="formheading">Your Invoice Details</span>
				<br>
				<div class="group">
					<label for="fname">First Name</label>
					<input type="text" id="fname" name="firstname" value="${sessionScope.user.firstName}" REQUIRED>
				</div>
				<div class="group">
					<label for="lname">Last Name</label>
					<input type="text" id="lname" name="lastname" value="${sessionScope.user.lastName}" REQUIRED>
				</div>
				<div class="group">
					<label for="dob">Date of Birth</label>
					<input type="date" id="dob" name="dob" value="${sessionScope.user.DOB}" REQUIRED>
				</div><br><br>

				<span id="formheading1">Address Details</span>
				<br>
				<div class="group">
					<label for="address">Address</label>
					<input type="text" id="address" name="address" REQUIRED>
				</div>
				<div class="group">
					<label for="city">City</label>
					<input type="text" id="city" name="city" REQUIRED>
				</div>
				<div class="group">
					<label for="postcode">Postcode</label>
					<input type="text" id="postcode" name="postcode" REQUIRED>
				</div>
				<div class="group">
					<label for="state">State/Territory</label>
					<select id="state" name="state" REQUIRED>
						<option value="nsw">NSW</option>
						<option value="qld">QLD</option>
						<option value="vic">VIC</option>
						<option value="sa">SA</option>
						<option value="wa">WA</option>
						<option value="tas">TAS</option>
						<option value="act">ACT</option>
						<option value="nt">NT</option>
					</select>
				</div><br><br>
				<div class="group">
					<span id="formheading2">Payment Type</span><br><br>
					<label for="payment">Credit Card</label>
					<input type="radio" id="payment" name="payment" value="creditcard" checked><br>
					<label for="payment2">Paypal</label>
					<input type="radio" id="payment2" name="payment" value="paypal">
				</div><br>
				<div class="group">
					<input type="submit" name ="paymentsubmit" value="Proceed To Payment">
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
