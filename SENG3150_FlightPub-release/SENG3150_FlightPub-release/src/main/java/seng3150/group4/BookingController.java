package seng3150.group4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import seng3150.group4.entity.*;
import org.springframework.ui.ModelMap;
import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.text.*;

@Controller
public class BookingController {

    private static EntityManager em = null;

	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public String booking(@RequestParam String airlineName, @RequestParam String airlineCode, @RequestParam String flightNumber, @RequestParam String departure_airport,
                          @RequestParam String arrival_airport, @RequestParam int duration, @RequestParam int stop_overs, @RequestParam double cost,
                          @RequestParam String depdate, @RequestParam String class_type, @RequestParam String plane_code, @RequestParam String dep_time,
                          @RequestParam String arr_time, @RequestParam String arr_date, @RequestParam String departureTime,
						  @RequestParam String stopover_airport, @RequestParam String stopover_arrtime, @RequestParam String stopover_deptime,

                          @RequestParam String airlineName2, @RequestParam String airlineCode2, @RequestParam String flightNumber2, @RequestParam String departure_airport2,
                          @RequestParam String arrival_airport2, @RequestParam int duration2, @RequestParam int stop_overs2, @RequestParam double cost2,
                          @RequestParam String depdate2, @RequestParam String class_type2, @RequestParam String plane_code2, @RequestParam String dep_time2,
                          @RequestParam String arr_time2, @RequestParam String arr_date2, @RequestParam String departureTime2,
						  @RequestParam String stopover_airport2, @RequestParam String stopover_arrtime2, @RequestParam String stopover_deptime2,

						  ModelMap model, HttpSession session) {


		Date departureTimeAsDate = null;

		// TODO: Pre-parse the date using SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		//2018-08-31 04:35:00.0
		//
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S", Locale.ENGLISH);
		try { departureTimeAsDate =  df.parse(departureTime); } catch (ParseException e) { e.printStackTrace(); }

        Flight retflight = new Flight(flightNumber, airlineName, airlineCode, departure_airport, arrival_airport, duration, stop_overs, cost, depdate, class_type, plane_code, dep_time, arr_date, arr_time, stopover_airport, stopover_arrtime, stopover_deptime, departureTimeAsDate);

		Date departureTimeAsDate2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		try { departureTimeAsDate2 =  sdf.parse(departureTime2); } catch (ParseException e) { e.printStackTrace(); }
		// Date.format accepts a date and returns a String, so it must be parsed again
		try { departureTimeAsDate2 = df.parse(df.format(departureTimeAsDate2)); } catch (ParseException e) { e.printStackTrace(); }

        Flight depflight = new Flight(flightNumber2, airlineName2, airlineCode2, departure_airport2, arrival_airport2, duration2, stop_overs2, cost2, depdate2, class_type2, plane_code2, dep_time2, arr_date2, arr_time2, stopover_airport2, stopover_arrtime2, stopover_deptime2, departureTimeAsDate2);
        model.put("retflight", retflight);
        model.put("depflight", depflight);

        // List is used for scalability, as for multi-leg flights there may be more than 1 flight per direction
		// NOTE: Even though this is a list, Payment.jsp only grabs the first from each list
        List<Flight> departureFlights = new ArrayList<>();
        List<Flight> returnFlights = new ArrayList<>();

        departureFlights.add(depflight);
        returnFlights.add(retflight);

        session.setAttribute("returnFlights", returnFlights);
        session.setAttribute("departureFlights", departureFlights);

		return "Booking";
	}

	@RequestMapping(value = "/bookingsingle", method = RequestMethod.POST)
	public String bookingSingle(@RequestParam String airlineName, @RequestParam String airlineCode, @RequestParam String flightNumber, @RequestParam String departure_airport,
						  @RequestParam String arrival_airport, @RequestParam int duration, @RequestParam int stop_overs, @RequestParam double cost,
						  @RequestParam String depdate, @RequestParam String class_type, @RequestParam String plane_code, @RequestParam String dep_time,
						  @RequestParam String arr_time, @RequestParam String arr_date, @RequestParam String departureTime,
						  @RequestParam String stopover_airport, @RequestParam String stopover_arrtime, @RequestParam String stopover_deptime,
						  ModelMap model, HttpSession session) {

		Date departureTimeAsDate = null;

		//2018-08-31 04:35:00.0
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S", Locale.ENGLISH);
		try { departureTimeAsDate =  df.parse(departureTime); } catch (ParseException e) { e.printStackTrace(); }

		Flight depflight = new Flight(flightNumber, airlineName, airlineCode, departure_airport, arrival_airport, duration, stop_overs, cost, depdate, class_type, plane_code, dep_time, arr_date, arr_time, stopover_airport, stopover_arrtime, stopover_deptime, departureTimeAsDate);
		model.put("depflight", depflight);
		boolean returnflight = false;
		model.put("returnflight", returnflight);

		// List is used for scalability, as for multi-leg flights there may be more than 1 flight per direction
		List<Flight> departureFlights = new ArrayList<>();
		List<Flight> returnFlights = new ArrayList<>();

		departureFlights.add(depflight);
		/* returnFlights.size() == 0 */

		session.setAttribute("returnFlights", returnFlights);
		session.setAttribute("departureFlights", departureFlights);

		return "Booking";
	}

	@RequestMapping(value = "/confirmBooking", method = RequestMethod.GET)
	public String confirmBooking(HttpSession session, ModelMap model) {
		if (session.getAttribute("user") == null)
		{
			model.put("error", "You must be logged in to make a booking. Please log in or register using the link above.");
			session.removeAttribute("departureFlights");
			session.removeAttribute("returnFlights");
			return "Search";
		}
		return "ConfirmBooking";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String payment(ModelMap model, HttpSession session) {
		return "Payment";
	}

	@RequestMapping(value = "/payment/confirm", method = RequestMethod.GET)
	public ModelAndView paymentConfirm(ModelMap model, HttpSession session) {
		if (em == null) {
			em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").
					createEntityManager();
		}

		if (session.getAttribute("departureFlights") != null
				&& session.getAttribute("user") != null) {
			UserEntity user = (UserEntity) session.getAttribute("user");
			Flight departureFlight = ((List<Flight>) session.getAttribute("departureFlights")).get(0);

			if (((List<Flight>) session.getAttribute("returnFlights")).size() > 0) {
				Flight returnFlight = ((List<Flight>) session.getAttribute("returnFlights")).get(0);
				em.getTransaction().begin();

				FlightHistoryId newFlightHistoryId = new FlightHistoryId(user.getId(), returnFlight.getAirlineCode(),
						returnFlight.getFlightNumber(), returnFlight.getDepartureTime());
				FlightHistoryEntity newFlightHistory = new FlightHistoryEntity(newFlightHistoryId);

				em.persist(newFlightHistory);
				em.getTransaction().commit();
			}
			em.getTransaction().begin();

			FlightHistoryId newFlightHistoryId = new FlightHistoryId(user.getId(), departureFlight.getAirlineCode(),
					departureFlight.getFlightNumber(), departureFlight.getDepartureTime());
			FlightHistoryEntity newFlightHistory = new FlightHistoryEntity(newFlightHistoryId);

			em.persist(newFlightHistory);
			em.getTransaction().commit();
		}

		// Clear session of flights and return home
		session.removeAttribute("departureFlights");
		session.removeAttribute("returnFlights");
		return new ModelAndView("redirect:/");
	}
}
