<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="${pageContext.request.contextPath}/js/flightpub.js" type="application/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/flightpub.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home</title>
</head>
<body>

<div id="container">

    <jsp:include page="Navbar.jsp" />

    <div id="body">
        <div id="personaldetails">
            <p class="error">${error}</p>
            <h1>Account Details</h1>
            <p>${sessionScope.user.title} ${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
            <p>DOB: ${sessionScope.user.DOB}</p>
            <p>Username: ${sessionScope.user.username}</p>
            <c:if test="${sessionScope.user.phoneNumber != null}">
                <p>Phone Number: ${sessionScope.user.phoneNumber}</p>
            </c:if>
            <p>Email: ${sessionScope.user.email}</p>
            <form action="/changedetails" method="post">
                <input type="submit" name ="changedetails" value="Change Details">
            </form>
            <br>
            <form action="/account/flighthistory" method="get">
                <input type="submit" class="flight-history" value="View Flight History" />
            </form>
        </div>
    </div>

    <div id="footer">
        <p class="copyright">Copyright FlightPub® 2018</p>
    </div>
</div>

</body>
</html>
