<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="/js/flightpub.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/flightpub.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FlightHistory</title>
</head>
<body>

<div id="container">

    <jsp:include page="Navbar.jsp" />
    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

    <div id="body">
        <div id ="flightreview">
            <h2>Your Flights</h2>
            <c:if test="${empty flights}">
                <p>You have no previous flights.</p>
            </c:if>
            <c:forEach var="flight" items="${flights}" >
                <div>
                    <span class="airlineCode">${flight.id.airlineCode}</span> -
                    <span class="flightNumber">${flight.id.flightNumber}</span> -
                    <span class="flightNumber">${flight.id.departureTime}</span>

                        <!-- An attempt to only display the review button if flight is in the past -->
                        <%--<c:if test="${currentDate} gt ${flight.id.departureTime}">
                            <input type="submit" onclick="popUpReview()" id="popUpButton" value="Review this Flight">
                        </c:if>--%>

                        <input type="submit" onclick="popUpReview()" id="popUpButton" value="Review this Flight">

                        <div id="popUpModal" class="modal">
                            <div class="modal-content">
                                <span class="modal-close" onclick="closePopUpReview()">&times;</span>
                                <div class="modal-text">
                                    <h2>Review Flight</h2>
                                    <p>Please rate this flight from 1 to 5</p>
                                    <form action="/account/flighthistory/review" method="POST">
                                        <input type="radio" name="ratingReview" value="1"> 1 - Really needs improving<br>
                                        <input type="radio" name="ratingReview" value="2"> 2 - Needs a little improving<br>
                                        <input type="radio" name="ratingReview" value="3" checked> 3 - Not bad<br>
                                        <input type="radio" name="ratingReview" value="4"> 4 - Great<br>
                                        <input type="radio" name="ratingReview" value="5"> 5 - Perfect!<br>
                                        <br>
                                        <input type="text" name="textReview" id="review-modal-text" placeholder="Please feel free to provide us with any additional feedback :)"><br>
                                        <input type="hidden" name="flightNumber" value="${flight.id.flightNumber}"/>
                                        <input type="submit" value="Submit">
                                    </form>
                                </div>
                            </div>
                        </div>
                </div>
            </c:forEach>
        </div>

        <div id="footer">
            <p class="copyright">Copyright FlightPub® 2018</p>
        </div>
    </div>

</body>
</html>
