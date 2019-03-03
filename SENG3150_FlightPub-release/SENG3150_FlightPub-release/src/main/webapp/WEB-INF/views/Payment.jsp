<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/css/flightpub.css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Home</title>
</head>
<body>

<div id="container">

	<jsp:include page="Navbar.jsp" />

	<div id="body">
		<c:choose>
			<c:when test="${param.success == null}">
				<c:choose>
					<c:when test="${param.payment == 'creditcard'}">
						<div id="successful">
							<form name="cardForm" action="/payment/confirm">
								<div class="group">
									<label for="cardNumber">Card number</label>
									<input id="cardNumber" type="text" name="cardNumber" REQUIRED>
									<label for="nameOnCard">Name on card</label>
									<input id="nameOnCard" type="text" name="nameOnCard" REQUIRED>
									<label for="securityCode">Security code</label>
									<input id="securityCode" type="text" name="securityCode" REQUIRED>
									<br>
									<input class="card-payment-submit" type="submit" name="submit" value="Submit">
								</div>
								<input type="hidden" name="success" value="success">
							</form>
						</div>

					</c:when>
					<c:when test="${param.payment == 'paypal'}">
						<div id="paypal-button-container"></div>
						<script src="https://www.paypalobjects.com/api/checkout.js"></script>
						<!-- PayPal API usage -->
						<script>
                            paypal.Button.render({
                                // Configure environment
                                env: 'sandbox',
                                client: {
                                    sandbox: 'Act5g2LTHiixs5cloBdmmUhdbeXngjgduDZLOpDOj6lF7xJm4LV7WyKufiyo_FO_ZoI_cZnuNJT-0prc',
                                    production: 'demo_production_client_id'
                                },
                                // Customize button (optional)
                                locale: 'en_AU',
                                style: {
                                    size: 'small',
                                    color: 'gold',
                                    shape: 'pill',
                                },
                                // Set up a payment
                                payment: function (data, actions) {
                                    return actions.payment.create({
                                        transactions: [{
                                            amount: {
                                                total: '${sessionScope.departureFlights[0].cost + sessionScope.returnFlights[0].cost}',
                                                currency: 'AUD'
                                            },
                                            description: 'Total cost of your flights.',
                                            custom: '90048630024435',
                                            payment_options: {
                                                allowed_payment_method: 'INSTANT_FUNDING_SOURCE'
                                            },
                                            soft_descriptor: 'ECHI5786786',
                                            item_list: {
                                                items: [
                                                    {
                                                        name: 'Departure Flight ${sessionScope.departureFlights[0].flightNumber}',
                                                        description: 'Departure Flight to ${sessionScope.departureFlights[0].arrival_airport}',
                                                        quantity: '1',
                                                        price: ${sessionScope.departureFlights[0].cost},
                                                        currency: 'AUD'
                                                    }
												<c:choose>
                                                    <c:when test="${empty sessionScope.returnFlights}" />
													<c:otherwise>
														,
														{
															name: 'Return Flight ${sessionScope.returnFlights[0].flightNumber}',
															description: 'Return Flight to ${sessionScope.returnFlights[0].arrival_airport}',
															quantity: '1',
															price: ${sessionScope.returnFlights[0].cost},
															currency: 'AUD'
														}
														</c:otherwise>
												</c:choose>
                                                ],
                                                shipping_address: {
                                                    recipient_name: '${param.firstname.concat(" ").concat(param.lastname)}',
                                                    line1: '${param.address}',
                                                    city: '${param.city}',
                                                    country_code: 'AU',
                                                    postal_code: '${param.postcode}',
                                                    phone: '0421123456',
                                                    state: '${param.state}'
                                                }
                                            }
                                        }]
                                    });
                                },
                                // Execute the payment
                                onAuthorize: function (data, actions) {
                                    return actions.payment.get().then(function(data) {
                                        document.querySelector('#paypal-button-container').style.display = 'none';
                                        return actions.payment.execute().then(function () {
                                            document.getElementById("test").innerHTML =
                                                '<div id="successful">' +
													'<h1 id="success">Payment Successful!</h1><br>' +
													'<p>Your booking details will be emailed to you shortly!</p><br>' +
													'<form name="paypalConfirm" action="/payment/confirm">' +
														'<input type="submit" class="home" value="Go Home">' +
													'</form>' +
                                                '</div>'
                                        });
                                    });
                                }
                            }, '#paypal-button-container');
						</script>
						<div id="test"></div>
					</c:when>
				</c:choose>
			</c:when>

			<%--<c:when test="${param.payment == 'paypal'}">
				<div id="paypal-button-container"></div>
				<script src="https://www.paypalobjects.com/api/checkout.js"></script>
				<script>
                    paypal.Button.render({
                        // Configure environment
                        env: 'sandbox',
                        client: {
                            sandbox: 'Act5g2LTHiixs5cloBdmmUhdbeXngjgduDZLOpDOj6lF7xJm4LV7WyKufiyo_FO_ZoI_cZnuNJT-0prc',
                            production: 'demo_production_client_id'
                        },
                        // Customize button (optional)
                        locale: 'en_AU',
                        style: {
                            size: 'small',
                            color: 'gold',
                            shape: 'pill',
                        },
                        // Set up a payment
                        payment: function (data, actions) {
                            return actions.payment.create({
                                transactions: [{
                                    amount: {
                                        total: '${sessionScope.departureFlights[0].cost + sessionScope.returnFlights[0].cost}',
                                        currency: 'AUD'
                                    },
                                    description: 'Total cost of your flights.',
                                    custom: '90048630024435',
                                    payment_options: {
                                        allowed_payment_method: 'INSTANT_FUNDING_SOURCE'
                                    },
                                    soft_descriptor: 'ECHI5786786',
                                    item_list: {
                                        items: [
                                            {
                                                name: 'Departure Flight ${sessionScope.deparetureFlight.flightId}',
                                                description: 'Departure Flight to ${sessionScope.deparetureFlight.destinationCode}',
                                                quantity: '1',
                                                price: ${sessionScope.deparetureFlight.cost}, I have no idea how the PriceEntity interacts, this cost value doesn't exist
                                                currency: 'AUD'
                                            },
                                            {
                                                name: 'Return Flight ${sessionScope.returnFlights[0].flightId}',
                                                description: 'Return Flight to ${sessionScope.returnFlights[0].destinationCode}',
                                                quantity: '1',
                                                price: ${sessionScope.returnFlights[0].cost},
                                                currency: 'AUD'
                                            }
                                        ],
                                        shipping_address: {
                                            recipient_name: '${param.firstname.concat(" ").concat(param.lastname)}',
                                            line1: '${param.address}',
                                            line2: 'Unit #34',
                                            city: '${param.city}',
                                            country_code: 'AU',
                                            postal_code: '${param.postcode}',
                                            phone: '0421123456',
                                            state: '${param.state}'
                                        }
                                    }
                                }]
                            });
                        },
                        // Execute the payment
                        onAuthorize: function (data, actions) {
                            return actions.payment.get().then(function(data) {
                                // Display the payment details and a confirmation button
                                var shipping = data.payer.payer_info.shipping_address;
                                document.querySelector('#paypal-button-container').style.display = 'none';
                                return actions.payment.execute().then(function () {
                                    document.getElementById("test").innerHTML =
                                        '<div id="successful">' +
                                        '<h1 id="success">Payment Successful!</h1><br>' +
                                        '<p>Your booking details will be emailed to you shortly!</p><br>' +
                                        '<input type="button" class="home" onclick="myFunction();" value="Go Home">' +
                                        '</div>'
                                });
                            });
                        }
                    }, '#paypal-button-container');
                    function myFunction(){
                        return location.href='/search';
                    }
				</script>
				<div id="test"></div>
			</c:when>--%>

			<c:otherwise>
				<div id="successful">
					<h1 id="success">Payment Successful!</h1><br>
					<p>Your booking details will be emailed to you shortly!</p><br>
					<input type="button" class="home" onclick="location.href='/search';" value="Go Home">
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<div id="footer">
		<p class="copyright">Copyright FlightPub® 2018</p>
	</div>
</div>

</body>
</html>
