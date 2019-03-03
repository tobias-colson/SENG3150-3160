<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="/js/flightpub.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search</title>
</head>
<body>
<div id="container">

	<jsp:include page="Navbar.jsp" />

	<div class="filter">
		<h3>Filter Results</h3>
		<form name="filter" method="post" action="/filteroneway">
			<p class="filterheading">Stops</p>
			<%--There may be an issue with this on different servers. Some servers require the two (checkbox and hidden) to be written in reversed--%>
			<input type="checkbox" name="direct" value="true" checked> Direct<br>
			<input type="hidden" name="direct" value="false">

			<input type="checkbox" name="onestop" value="true" checked> One Stop<br>
			<input type="hidden" name="onestop" value="false">

			<input type="checkbox" name="twostops" value="true" checked> Two Stops<br>
			<input type="hidden" name="twostops" value="false">

			<%--<p class="filterheading">Airlines</p>--%>
			<%----%>
			<%--<input type="checkbox" name="airline" value="airline1" checked> Airline 1<br>--%>
			<%--<input type="hidden" name="direct" value="false">--%>
			<%----%>
			<%--<input type="checkbox" name="airline" value="airline2" checked> Airline 2<br>--%>
			<%--<input type="hidden" name="direct" value="false">--%>
			<%----%>
			<%--<input type="checkbox" name="airline" value="airline3" checked> Airline 3<br>--%>
			<%--<input type="hidden" name="direct" value="false">--%>

			<p class="filterheading">Departure Time</p>

			<input type="checkbox" name="depmorning" value="true" checked> Morning (5:00 am - 11:59 am)<br>
			<input type="hidden" name="depmorning" value="false">

			<input type="checkbox" name="depafternoon" value="true" checked> Afternoon (12:00 pm - 5:59 pm)<br>
			<input type="hidden" name="depafternoon" value="false">

			<input type="checkbox" name="depevening" value="true" checked> Evening (6:00 pm - 11:59 pm)<br>
			<input type="hidden" name="depevening" value="false">

			<input type="checkbox" name="deplate" value="true" checked> Late Night (12:00 am - 4:59 am)<br>
			<input type="hidden" name="deplate" value="false">

			<p class="filterheading">Arrival Time</p>

			<input type="checkbox" name="arrmorning" value="true" checked> Morning (5:00 am - 11:59 am)<br>
			<input type="hidden" name="arrmorning" value="false">

			<input type="checkbox" name="arrafternoon" value="true" checked> Afternoon (12:00 pm - 5:59 pm)<br>
			<input type="hidden" name="arrafternoon" value="false">

			<input type="checkbox" name="arrevening" value="true" checked> Evening (6:00 pm - 11:59 pm)<br>
			<input type="hidden" name="arrevening" value="false">

			<input type="checkbox" name="arrlate" value="true" checked> Late Night (12:00 am - 4:59 am)<br>
			<input type="hidden" name="arrlate" value="false">

			<input type="hidden" name="departure" value="${departure}"/>
			<input type="hidden" name="arrival" value="${arrival}"/>
			<input type="hidden" name="class_typeSearch" value="${class_typeSearch}"/>
			<input type="hidden" name="passengers" value="${passengers}"/>
			<input type="hidden" name="triptype" value="${triptype}"/>
			<input type="hidden" name="depdateSearch" value="${depdateSearch}"/>
			<input type="hidden" name="retdate" value="${retdate}"/>
			<input type="hidden" name="retdateend" value="${retdateend}"/>
			<input type="hidden" name="budget" value="${budget}"/>
			<input type="hidden" name="depdateend" value="${depdateend}"/>
			<input type="hidden" name="usercategory" value="${usercategory}"/>

			<input type="hidden" name="specific" value="${specific}"/>

			<input type="submit" id="update" value="Update Results">
		</form>

		<input type="submit" onclick="popUpReview()" id="popUpButton" value="Review Our Results">

		<div id="popUpModal" class="modal">
			<div class="modal-content">
				<span class="modal-close" onclick="closePopUpReview()">&times;</span>
				<div class="modal-text">
					<h2>Review</h2>
					<p>Please rate our flight results from 1 to 5</p>
					<form action="/review" method="post">
						<input type="radio" name="ratingReview" value="1"> 1 - Really needs improving<br>
						<input type="radio" name="ratingReview" value="2"> 2 - Needs a little improving<br>
						<input type="radio" name="ratingReview" value="3" checked> 3 - Not bad<br>
						<input type="radio" name="ratingReview" value="4"> 4 - Great<br>
						<input type="radio" name="ratingReview" value="5"> 5 - Perfect!<br>
						<br>
						<input type="text" name="textReview" id="review-modal-text" placeholder="Please feel free to provide us with any additional feedback :)"><br>
						<input type="submit" value="Submit">
					</form>
				</div>
			</div>
		</div>

	</div>


	<div id="body">
		<div id="flights">
			<h2 id="results">Select A Flight</h2>

			<form name="sorting" method="post" action="/sortoneway">
				<select onchange="this.form.submit()" name="sorting_method" id="sorting_method">
					<c:choose>
					<c:when test="${sortoption == 'none'}">
						<option value="none" selected="selected">Select Sorting Option</option>
					</c:when>
					<c:otherwise><option value="none">Select Sorting Option</option></c:otherwise>
					</c:choose>

					<c:choose>
					<c:when test="${sortoption == 'mincost'}">
						<option value="mincost" selected="selected">Lowest Price</option>
					</c:when>
					<c:otherwise>
						<option value="mincost">Lowest Price</option>
					</c:otherwise>
					</c:choose>

					<c:choose>
					<c:when test="${sortoption == 'maxcost'}">
						<option value="maxcost" selected="selected">Highest Price</option>
					</c:when>
					<c:otherwise>
						<option value="maxcost">Highest Price</option>
					</c:otherwise>
					</c:choose>

					<c:choose>
					<c:when test="${sortoption == 'minduration'}">
						<option value="minduration" selected="selected">Shortest Flight</option>
					</c:when>
					<c:otherwise>
						<option value="minduration">Shortest Flight</option>
					</c:otherwise>
					</c:choose>

					<c:choose>
					<c:when test="${sortoption == 'maxduration'}">
						<option value="maxduration" selected="selected">Longest Flight</option>
					</c:when>
					<c:otherwise>
						<option value="maxduration">Longest Flight</option>
					</c:otherwise>
					</c:choose>

				</select><br>

				<input type="hidden" name="departure" value="${departure}"/>
				<input type="hidden" name="arrival" value="${arrival}"/>
				<input type="hidden" name="class_typeSearch" value="${class_typeSearch}"/>
				<input type="hidden" name="passengers" value="${passengers}"/>
				<input type="hidden" name="triptype" value="${triptype}"/>
				<input type="hidden" name="depdateSearch" value="${depdateSearch}"/>
				<input type="hidden" name="retdate" value="${retdate}"/>
				<input type="hidden" name="retdateend" value="${retdateend}"/>
				<input type="hidden" name="budget" value="${budget}"/>
				<input type="hidden" name="depdateend" value="${depdateend}"/>

				<input type="hidden" name="specific" value="${specific}"/>

			</form>

			<c:if test="${noflightsfound == true}">
				<div id="searchform">
					<h2>Sorry, No Flights Were Returned From Your Search</h2>
					<input type="button" class="goback" onclick="location.href='/search';" value="Return to Search"/><br><br>

				</div>
			</c:if>

			<c:forEach var="flight" items="${depflights}" >
				<div class="details">
					<form name="details" method="post" action="/bookingsingle">
						<div class="resultHeader">
							<div class="airports">
									${flight.departure_airport} to ${flight.arrival_airport} <br> Departing: ${flight.depdate}
							</div>
							<img class="lilplane" src="/images/plane.png" height="40">
							<div class="airline">${flight.airlineName}</div>
							<br>
						</div>
						<div class="resultDetails">
							<div class="time">Departure: ${flight.dep_time} - (${flight.departure_airport})</div>
							<div class="time">Arrival: ${flight.arr_time} - (${flight.arrival_airport})</div>
							<div class="flightduration">Duration: ${flight.duration}min </div>
							<br>
							<div class="stops">Stop-overs: ${flight.stop_overs}</div>

							<c:if test="${flight.stop_overs == 1}">

								<div class="stopover_airport">Stop-over Airport: ${flight.stopover_airport}</div>
								<div class="stopover_arrtime">Stop-over Arrival Time: ${flight.stopover_arrtime}</div>
								<div class="stopover_deptime">Stop-over Departure Time: ${flight.stopover_deptime}</div>

							</c:if>
							<div class="selectFlight">
								<div class="cost">$${flight.cost}</div><br>


								<input type="hidden" name="airlineName" value="${flight.airlineName}"/>
								<input type="hidden" name="airlineCode" value="${flight.airlineCode}"/>
								<input type="hidden" name="flightNumber" value="${flight.flightNumber}"/>
								<input type="hidden" name="departure_airport" value="${flight.departure_airport}"/>
								<input type="hidden" name="arrival_airport" value="${flight.arrival_airport}"/>
								<input type="hidden" name="duration" value="${flight.duration}"/>
								<input type="hidden" name="stop_overs" value="${flight.stop_overs}"/>
								<input type="hidden" name="cost" value="${flight.cost}"/>
								<input type="hidden" name="depdate" value="${flight.depdate}"/>
								<input type="hidden" name="class_type" value="${flight.class_type}"/>
								<input type="hidden" name="plane_code" value="${flight.plane_code}"/>
								<input type="hidden" name="dep_time" value="${flight.dep_time}"/>
								<input type="hidden" name="arr_date" value="${flight.arr_date}"/>
								<input type="hidden" name="arr_time" value="${flight.arr_time}"/>
								<input type="hidden" name="stopover_airport" value="${flight.stopover_airport}"/>
								<input type="hidden" name="stopover_arrtime" value="${flight.stopover_arrtime}"/>
								<input type="hidden" name="stopover_deptime" value="${flight.stopover_deptime}"/>
								<input type="hidden" name="departureTime" value="${flight.departureTime}"/>
								<input type="hidden" name="display" value="${flight.display}"/>

									<%--Don't know what this is
                                <input type="hidden" name="depflight" value="${depflight.airlineName}"/>--%>

							</div>
						</div>

						<input type="submit" class="choose" name="flightselect" value="Select" />

					</form>
				</div>
			</c:forEach>
		</div>
	</div>

	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>
</div>
</body>
</html>
