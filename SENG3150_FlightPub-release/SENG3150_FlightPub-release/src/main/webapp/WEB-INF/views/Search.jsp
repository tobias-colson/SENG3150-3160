<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<script src="/js/flightpub.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Home</title>
</head>
<body>

<div id="container">

	<jsp:include page="Navbar.jsp" />
	
	<div id="body">

		<c:if test="${noflightsfound == true}">
			<div id="searchform">
				<h2>Sorry, No Flights Were Returned From Your Search</h2>
			</div>
		</c:if>

		<div id="searchform">
			<form name="searchform" action="/specificsearch" onsubmit="return validateSpecificSearch();">
				<c:if test="${not empty error}">
					<p class="error">${error}</p>
				</c:if>
				<h2>Find A Flight</h2><br>
				<div class="group">
					<label for="return" class="radio">Return</label>
					<input id="return" type="radio" name="triptype" value="return" onclick="showReturnDate()" checked>
					<label for="oneway" class="radio">One-Way</label>
			  		<input id="oneway" type="radio" name="triptype" value="single" onclick="hideReturnDate()">
				</div><br>
				
				<div class="group">
					<label for="depart">Departing From</label>
					<input id="depart" type="text" name="departure" placeholder="e.g. Vienna" REQUIRED>
				</div>
				
				<div class="group">
					<label for="arrival">Arriving At</label>
					<input id="arrival" type="text" name="arrival" placeholder="e.g. Madrid" REQUIRED>
				</div><br>
				
				<div class="group">
					<label for="depdate">Departure Date</label>
					<input id="depdate" type="date" name="depdate" onclick="minimumDate()" REQUIRED>
				</div>
				
				<div class="group">
				<div id="returning">
					<label for="retdate">Return Date</label>
					<input id="retdate" type="date" name="retdate" onclick="minimumDate()">
				</div>
				</div><br>
				
				<div class="group">
					<label for="class_type">Class</label>
					<select id="class_type" name="class_type">
						<option value="economy">Economy</option>
						<option value="business">Business</option>
						<option value="firstclass">First Class</option>
					</select><br>
				</div>
				
				<div class="group">
					<label for="passengers">Passengers</label>
					<select id="passengers" name="passengers">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
					</select>
				</div><br>
				<input type="submit" name="search" value="Search">
			</form>
		</div>
		
		<div id="assistedform">
			<form name="assistedform" action="/assistedsearch" onsubmit="return validateAssistedSearch();">
				<h2>Let Us Recommend A Flight</h2><br>
				<div class="group">
					<label for="return2" class="radio">Return</label>
					<input id="return2" type="radio" name="triptype" value="return" onclick="showReturnDate2()" checked>
					<label for="oneway2" class="radio">One-Way</label>
			  		<input id="oneway2" type="radio" name="triptype" value="single" onclick="hideReturnDate2()">
				</div><br>
				
				<div class="group">
					<label for="depart2">Departing From</label>
					<input id="depart2" type="text" name="departure" placeholder="e.g. Los Angeles" REQUIRED>
				</div>
				
				<div class="group">
					<label for="country">Choose A Country</label>
					<select id="country" name="country">
						<option value="AUS">Australia</option>
						<option value="AUT">Austria</option>
						<option value="BRA">Brazil</option>
						<option value="CHN">China</option>
						<option value="CAN">Canada</option>
						<option value="FIN">Finland</option>
						<option value="FRA">France</option>
						<option value="ITA">Italy</option>
						<option value="JPN">Japan</option>
						<option value="MYS">Malaysia</option>
						<option value="NLD">Netherlands</option>
						<option value="QTA">Qatar</option>
						<option value="SGP">Singapore</option>
						<option value="ZAF">South Africa</option>
						<option value="ESP">Spain</option>
						<option value="THA">Thailand</option>
						<option value="ARE">United Arab Emirates</option>
						<option value="GBR">United Kingdom</option>
						<option value="USA">United States</option>
					</select>
				</div><br>

				<h4>Select Between a Range of Departure Dates</h4>
				<div class="group">
					<label for="depdate2">Start Departure Date</label>
					<input id="depdate2" type="date" name="depdate" onclick="minimumDate()" REQUIRED>
				</div>

				<div class="group">
					<label for="depdateend">End Departure Date</label>
					<input id="depdateend" type="date" name="depdateend" onclick="minimumDate()" REQUIRED>
				</div>

				<div id="returning2">
					<h4>Select Between a Range of Return Dates</h4>

					<div class="group">
						<label for="returndate2">Start Return Date</label>
						<input id="returndate2" type="date" name="retdate" onclick="minimumDate()">
					</div>

					<div class="group">
						<label for="returndateend">End Return Date</label>
						<input id="returndateend" type="date" name="retdateend" onclick="minimumDate()">
					</div>
				</div>

				<div class="group">
					<label for="usercategory">Choose A Category</label>
					<select id="usercategory" name="usercategory">
						<option value="standard">Standard</option>
						<option value="highclass">High Class</option>
						<option value="business">Business</option>
						<option value="leisure">Leisure</option>
						<option value="budget">Budget</option>
						<option value="family">Family</option>
					</select>
				</div>
				
				<div class="group">
					<label for="budget">Your budget ($)</label>
					<input id="budget" type="number" step="0.01" name="budget" REQUIRED>
				</div>
				
				<div class="group">
					<label for="class2">Class</label>
					<select id="class2" name="class_type">
						<option value="economy">Economy</option>
						<option value="business">Business</option>
						<option value="firstclass">First Class</option>
					</select><br>
				</div>
				
				<div class="group">
					<label for="passengers2">Passengers</label>
					<select id="passengers2" name="passengers">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
					</select>
				</div><br>
				<input type="submit" name="search" value="Search">
			</form>
		</div>
	</div>

	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>	
</div>

</body>
</html>
