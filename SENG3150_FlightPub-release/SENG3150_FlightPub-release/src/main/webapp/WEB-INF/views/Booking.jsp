<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking</title>
</head>
<body>

<div id="container">

	<jsp:include page="Navbar.jsp" />
	
	<div id="body">
		<div id ="flightreview">
			<h2>Flight Summary</h2>
			<c:choose>
			<c:when test ="${returnflight == false}">
				<div class="booking">
					<span id="summaryheading">Your flight</span><br><br>
					<span>${depflight.depdate} - ${depflight.arr_date}</span><br>
					<span>${depflight.airlineName}</span><br>
					<span>Departing from: ${depflight.departure_airport}</span><br>
					<span>Arrival at: ${depflight.arrival_airport}</span><br>
					<span>Stopovers: ${depflight.stop_overs}</span><br>
					<span>${depflight.dep_time} - ${depflight.arr_time}</span>
					<span>${depflight.duration}h</span><br>
				</div><br>
				<div class="totalcost">
					<span id="summaryheading">Total Cost: $${depflight.cost + retflight.cost}</span>
				</div><br>
                <input type="button" class="goforward" onclick="location.href='/confirmBooking';" value="Confirm Booking"/><br>
			</c:when>
			<c:otherwise>
                <div class="booking">
					<span id="summaryheading">Departing</span><br><br>
					<span>${depflight.depdate}</span><br>
					<span>${depflight.airlineName}</span><br>
					<span>Departing from: ${depflight.departure_airport}</span><br>
					<span>Arrival at: ${depflight.arrival_airport}</span><br>
					<span>Stopovers: ${depflight.stop_overs}</span><br>
					<span>${depflight.dep_time} - ${depflight.arr_time}</span>
					<span>${depflight.duration}h</span><br>
                </div><br>

				<div class="booking">
					<span id="summaryheading">Return</span><br><br>
					<span>${retflight.depdate}</span><br>
					<span>${retflight.airlineName}</span><br>
					<span>Departing from: ${retflight.departure_airport}</span><br>
					<span>Arrival at: ${retflight.arrival_airport}</span><br>
					<span>Stopovers: ${retflight.stop_overs}</span><br>
					<span>${retflight.dep_time} - ${retflight.arr_time}</span>
					<span>${retflight.duration}h</span><br>
				</div><br>
                <div class="totalcost">
                    <span id="summaryheading">Total Cost: $${depflight.cost + retflight.cost}</span>
                </div><br>

                <input type="button" class="goforward" onclick="location.href='/confirmBooking';" value="Confirm Booking"/><br>
			</c:otherwise>
			</c:choose>

		</div>
	</div>
	
	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>
</div>

</body>
</html>
