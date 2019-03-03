<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html lang="en">
<head></head>
    <body>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <div id="navheader">
                <a href="/"><img src="/images/Flightpub.png" height="75"></a>
                <a href="/" class="home">Home</a>
                <a href="/account" id="account">Account</a>
                <a href="/logout" id="logout">Logout</a>
            </div>
        </c:when>
        <c:otherwise>
            <div id="navheader">
                <a href="/"><img src="/images/Flightpub.png" height="75"></a>
                <a href="/" class="home">Home</a>
                <a href="/login" id="login">Login/Register</a>
            </div>
        </c:otherwise>
    </c:choose>
    </body>
</html>

