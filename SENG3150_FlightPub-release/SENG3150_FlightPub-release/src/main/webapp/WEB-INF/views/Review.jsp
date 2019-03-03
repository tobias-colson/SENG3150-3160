<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review</title>
</head>
<body>

<div id="container">

	<jsp:include page="Navbar.jsp" />
	
	<div id="body">
		<div class="reviewform">
			<form action="Search.jsp">
				<h2>Leave Some Feedback</h2><br>
				<div class="group">
					<span>Please rate your flight out of 5</span><br><br>
					
					<input id="1star" type="radio" name="star" value="1star" checked>1
			  		<input id="2star" type="radio" name="star" value="2star">2
			  		<input id="3star" type="radio" name="star" value="3star">3
			  		<input id="4star" type="radio" name="star" value="4star">4
			  		<input id="5star" type="radio" name="star" value="5star">5
				</div><br><br>
				
				<div class="group">
					<label for="depart">Additional Feedback</label><br>
					<textarea name="message" rows="15" cols="60"></textarea> 
				</div><br>
				<input type="submit" name="submitfeedback" value="Submit Feedback">
			</form>
		</div>
	</div>
	
	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>	
</div>

</body>
</html>