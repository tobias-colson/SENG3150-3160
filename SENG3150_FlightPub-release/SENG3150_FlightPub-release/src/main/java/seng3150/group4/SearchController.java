package seng3150.group4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import seng3150.group4.entity.*;
import org.springframework.ui.ModelMap;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SearchController {

	private static EntityManager em = null;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "Search";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search() {return "Search"; }

	@RequestMapping(value = "/specificsearch")
	public String specific_search(@RequestParam String departure, @RequestParam String arrival,
								  @RequestParam String class_type, @RequestParam int passengers,
								  @RequestParam String triptype, @RequestParam String depdate, @RequestParam String retdate, ModelMap model) {

		Flight[] depflights = getSearchResults(departure, arrival, class_type, passengers, triptype, depdate);

		model.put("depflights", depflights);

		if (depflights == null || depflights.length == 0) {

			model.put("noflightsfound", true);

			return "Search";
		}

		// Required for Sorting and Filtering
		model.put("departure", departure);
		model.put("arrival", arrival);
		model.put("class_typeSearch", class_type);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdate);
		model.put("retdate", retdate);
		model.put("budget", 0);
		model.put("depdateend", depdate);
		model.put("sortoption", "none");

		String usercategory = "standard";
		model.put("usercategory", usercategory);

		if (triptype.equals("single")) {

			model.put("specific", true);

			return "Results";
		}
		else {
			Flight depflight = new Flight("", "", "", "", "", 0,
					0, 0, "", "", "", "",
					"", "", "", "", "", new Date());

			model.put("depflight", depflight);

			boolean departing = true;
			boolean assisted = false;

			model.put("departing", departing);
			model.put("assisted", assisted);

			model.put("retdateend", retdate);

			return "Return";
		}
	}

	@RequestMapping(value = "/returning", method = RequestMethod.POST)
	public String returning(@RequestParam String airlineName, @RequestParam String airlineCode, @RequestParam String flightNumber, @RequestParam String departure_airport,
							@RequestParam String arrival_airport, @RequestParam int duration, @RequestParam int stop_overs, @RequestParam double cost,
							@RequestParam String depdate, @RequestParam String class_type, @RequestParam String plane_code, @RequestParam String dep_time,
							@RequestParam String arr_time, @RequestParam String arr_date, @RequestParam String departureTime,
							@RequestParam String stopover_airport, @RequestParam String stopover_arrtime, @RequestParam String stopover_deptime,

							@RequestParam String departure, @RequestParam String arrival,
							@RequestParam String class_typeSearch, @RequestParam int passengers,
							@RequestParam String triptype, @RequestParam String depdateSearch, @RequestParam String retdate, @RequestParam String retdateend,

							@RequestParam boolean assisted, @RequestParam String usercategory,

							ModelMap model) {


		Date departureTimeAsDate = null;

		//2018-08-31 04:35:00.0
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S", Locale.ENGLISH);
		try { departureTimeAsDate =  df.parse(departureTime); } catch (ParseException e) { } // Occassionally throws this


		Flight depflight = new Flight(flightNumber, airlineName, airlineCode, departure_airport, arrival_airport, duration,
				stop_overs, cost, depdate, class_type, plane_code, dep_time,
				arr_date, arr_time, stopover_airport, stopover_arrtime, stopover_deptime, departureTimeAsDate);

		model.put("depflight", depflight);


		model.put("assisted", assisted);
		model.put("usercategory", usercategory);

		model.put("departure", departure);
		model.put("arrival", arrival);
		model.put("class_typeSearch", class_type);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdate);
		model.put("retdate", retdate);
		model.put("budget", 0);
		model.put("sortoption", "none");

		boolean departing = false;
		model.put("departing", departing);

		if (assisted) {

			List<Flight> retFlights = getRetFlights(arrival_airport, departure, class_typeSearch, passengers, triptype, retdate, retdateend);

			model.put("depdateend", depdate);
			model.put("retdateend", retdateend);

			retFlights = sort_by_user_category(retFlights, "standard");
			model.put("retflights", retFlights);

			if(retFlights == null || retFlights.size() == 0)
				model.put("noflightsfound", true);


			return "Return";

		} else {
			model.put("depdateend", depdate);
			model.put("retdateend", retdate);

			Flight[] retFlights = getSearchResults(arrival, departure, class_typeSearch, passengers, triptype, retdate);
			model.put("retflights", retFlights);

			if(retFlights == null || retFlights.length == 0)
				model.put("noflightsfound", true);


			return "Return";

		}
	}

	public List<Flight> getAssistedSearchResults (int budget, String departure, String country,
												  String class_type, int passengers,
												  String triptype, String depdate, String depdateend, String retdate) {


		if (em == null)
		{
			em = Persistence.createEntityManagerFactory("FlightPubPU").
					createEntityManager();
		}

		Query query =  em.createQuery("SELECT d FROM DestinationsEntity d WHERE d.countryCode3=:countryCode");
		query.setParameter("countryCode", country);
		List<DestinationsEntity> depAirports = query.getResultList();

		Flight[] flights;

		List<Flight> depflights = new ArrayList<>();


		List<String> dates = getDates(depdate, depdateend);

		for (int k = 0; k < dates.size(); k++) {

			for (int i=0; i < depAirports.size(); i++) {

				flights = getSearchResults(departure, depAirports.get(i).getAirport(), class_type, passengers, triptype, dates.get(k));

				if (flights != null) {
					for (int j = 0; j < flights.length; j++) {

						if (flights[j].getCost() < budget)
							depflights.add(flights[j]);
					}
				}
			}
		}

		return depflights;
	}

	private List<Flight> getRetFlights(String arrival_airport, String departure, String class_typeSearch, int passengers, String triptype, String retdate, String retdateend) {

		List<String> dates = getDates(retdate, retdateend);

		Flight[] flights;
		List<Flight> retFlights = new ArrayList<>();

		for (int i = 0; i < dates.size(); i++) {
			flights = getSearchResults(arrival_airport, departure, class_typeSearch, passengers, triptype, dates.get(i));
			if (flights != null) {
				for (int j=0; j < flights.length; j++) {
					retFlights.add(flights[j]);
				}
			}
		}
		return retFlights;

	}

	private List<String> getDates (String depdate, String depdateend) {

		List<String> dates = new ArrayList<>();

		int startday = Integer.parseInt(depdate.substring(8));
		int startmth = Integer.parseInt(depdate.substring(5, 7));
		int startyr = Integer.parseInt(depdate.substring(0, 4));

		int endday = Integer.parseInt(depdateend.substring(8));
		int endmth = Integer.parseInt(depdateend.substring(5, 7));
		int endyr = Integer.parseInt(depdateend.substring(0, 4));

		int currentday = startday;
		int currentmth = startmth;
		int currentyr = startyr;


		if (currentday == endday && currentmth == endmth && currentyr == endyr) {

			dates.add(depdate);
			return dates;
		}

		String date = "";

		int count = 0;

		while ( ! (currentday == endday && currentmth == endmth && currentyr == endyr)) {

			// add date
			String mthzero = "";
			String dayzero = "";

			if (currentmth < 10)  mthzero = "0";
			if (currentday < 10)  dayzero = "0";

			date = currentyr + "-" + mthzero + "" + currentmth + "-" + dayzero + "" + currentday;
			dates.add(date);

			currentday++;

			if (currentday > 31) {
				currentmth++;
				currentday = 1;

				if (currentmth >= 13) {

					currentyr++;
					currentmth = 1;

				}
			}
			count++;
			if (count > 100) {

				System.out.println("Date range exceeded");
				break;
			}

		}

		dates.add(depdateend);

		return dates;
	}

	public Flight[] getSearchResults ( String departure, String arrival,
									   String class_type, int passengers,
									   String triptype, String depdate ) {

		String depcode = null;
		String arrcode = null;

		if (em == null)
		{
			em = Persistence.createEntityManagerFactory("FlightPubPU").
					createEntityManager();
		}

		// Get Departure Code
		Query query =  em.createQuery("SELECT d FROM DestinationsEntity d WHERE d.airport=:departure");
		query.setParameter("departure", departure);
		List<DestinationsEntity> depAirports = query.getResultList();


		// Get Arrival Code
		query =  em.createQuery("SELECT d FROM DestinationsEntity d WHERE d.airport=:arrival");
		query.setParameter("arrival", arrival);
		List<DestinationsEntity> arrAirports = query.getResultList();

		// Get Departure Flights

		if (depAirports.size() > 0 && arrAirports.size() > 0) {

			depcode = depAirports.get(0).getDestinationCode();
			arrcode = arrAirports.get(0).getDestinationCode();


			// Get List of FlightEnities matching parameters

			List<FlightEntity> depflightE = getFlights(depcode, arrcode, depdate);

			// Create Flight Objects
			// 			- Essentially a FlightEntity but with codes replaced with names and a few added attributes

			if (depflightE.size() > 0) {

				// Create Flight objects

				Flight[] flights = new Flight[depflightE.size()];
				for (int i = 0; i < depflightE.size(); i++) {
					flights[i] = build_flight_from_entity(depflightE.get(i), departure, arrival, class_type);
				}

				return flights;

			}
		}
		return null;

	}

	@RequestMapping(value = "/assistedsearch")
	public String assisted_search(@RequestParam String departure, @RequestParam String country,
								  @RequestParam String class_type, @RequestParam int passengers,
								  @RequestParam String triptype, @RequestParam String depdate, @RequestParam String depdateend,
								  @RequestParam String retdate, @RequestParam String retdateend, @RequestParam String usercategory, @RequestParam int budget, ModelMap model) {

		// Perform assisted search

		List<Flight> depflights = getAssistedSearchResults(budget, departure, country, class_type, passengers, triptype, depdate, depdateend, retdate);

		depflights = sort_by_user_category(depflights, usercategory);

		model.put("depflights", depflights);


		if (depflights == null || depflights.size() == 0) {

			model.put("noflightsfound", true);

			return "Search";
		}

		model.put("departure", departure);
		model.put("arrival", country);
		model.put("class_typeSearch", class_type);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdate);
		model.put("retdate", retdate);
		model.put("budget", budget);
		model.put("depdateend", depdateend);
		model.put("usercategory", usercategory);
		model.put("sortoption", "none");


		if (triptype.equals("single")) {

			model.put("specific", false);

			return "Results";
		}
		else {


			Flight depflight = new Flight("", "", "", "", "", 0,
					0, 0, "", "", "", "",
					"", "", "", "", "", new Date());

			model.put("depflight", depflight);


			boolean departing = true;
			boolean assisted = true;

			model.put("departing", departing);
			model.put("retdateend", retdateend);
			model.put("assisted", assisted);

			return "Return";
		}

	}


	private List<FlightEntity> getFlights (String depcode, String arrcode, String depdate) {

		Query query =  em.createQuery("SELECT f  FROM FlightEntity f WHERE f.departureCode = :depcode "
				+ "AND f.destinationCode = :arrcode AND SUBSTR(f.id.departureTime, 1, 10) = :depdate");

		//AND SUBSTR(f.departureTime, 1, 10) = :depdate
		query.setParameter("depcode", depcode);
		query.setParameter("arrcode", arrcode);
		query.setParameter("depdate", depdate);
		query.setMaxResults(50);		// TEMPORARY FOR TESTING
		List<FlightEntity> flightsE = query.getResultList();

		return flightsE;
	}

	public Flight build_flight_from_entity (FlightEntity flight_entity, String departure, String arrival,
											String class_type) {

		// Get Flight attributes

		String flightNo = flight_entity.getId().getFlightNumber();
		int duration = flight_entity.getDuration();

		int stopOvers = 0; ////// figure this out later

		Date departureTime = flight_entity.getId().getDepartureTime();

		String depdate = flight_entity.getId().getDepartureTime().toString().substring(0, 10);
		String plane_code = flight_entity.getPlaneCode();
		String dep_time = flight_entity.getId().getDepartureTime().toString().substring(11, 19);
		String arr_date = flight_entity.getArrivalTime().toString().substring(0, 10);
		String arr_time = flight_entity.getArrivalTime().toString().substring(11, 19);


		String stopover_airport_code = flight_entity.getStopOverCode();
		String stopover_airport = null;
		String stopover_arrtime = null;
		String stopover_deptime = null;
		if (stopover_airport_code != null) {

			stopOvers = 1;

			// Get Stopover Airport
			Query query =  em.createQuery("SELECT d FROM DestinationsEntity d WHERE d.destinationCode=:departure");
			query.setParameter("departure", stopover_airport_code);
			List<DestinationsEntity> depAirports = query.getResultList();

			stopover_airport = depAirports.get(0).getAirport();

			stopover_arrtime = flight_entity.getArrivalTimeStopOver().toString().substring(11, 19);
			stopover_deptime = flight_entity.getDepartureTimeStopOver().toString().substring(11, 19);

		}


		// Get Airline String

		String airline = "Unknown";

		String airlineCode = flight_entity.getId().getAirlineCode();
		Query query =  em.createQuery("SELECT a FROM AirlineEntity a WHERE a.airlineCode=:airlinecode");
		query.setParameter("airlinecode", airlineCode);
		List<AirlineEntity> airlines = query.getResultList();
		if (airlines.size() > 0)
			airline = airlines.get(0).getAirlineName();


		// Get Cost

		//This isn't necessarily going to be the correct one as there's multiple cost's per flight.
		//I'm just getting the first one for now as getting correct is complicated

		double cost = 0;
		query =  em.createQuery("SELECT p FROM PriceEntity p WHERE p.id.airlineCode=:airlinecode AND p.id.flightNumber=:flightno");
		query.setParameter("airlinecode", flight_entity.getId().getAirlineCode());
		query.setParameter("flightno", flight_entity.getId().getFlightNumber());
		List<PriceEntity> prices = query.getResultList();
		if (prices.size() > 0)
			cost = prices.get(0).getPrice().doubleValue();

		// Create Flight

		Flight flight = new Flight(flightNo, airline, airlineCode, departure, arrival, duration, stopOvers, cost, depdate, class_type, plane_code, dep_time, arr_date, arr_time, stopover_airport, stopover_arrtime, stopover_deptime, departureTime);

		return flight;
	}

	@RequestMapping(value = "/sortreturn", method = RequestMethod.POST)
	public String sortreturn(@RequestParam String departure, @RequestParam String arrival,
							 @RequestParam String class_typeSearch, @RequestParam int passengers,
							 @RequestParam String triptype, @RequestParam String depdateSearch, @RequestParam String retdate,
							 @RequestParam int budget, @RequestParam String depdateend,
							 @RequestParam boolean departing, @RequestParam boolean assisted,
							 @RequestParam String sorting_method, @RequestParam String usercategory, @RequestParam String retdateend,

							 @RequestParam String airlineName, @RequestParam String airlineCode, @RequestParam String flightNumber, @RequestParam String departure_airport,
							 @RequestParam String arrival_airport, @RequestParam int duration, @RequestParam int stop_overs, @RequestParam double cost,
							 @RequestParam String depdate, @RequestParam String class_type, @RequestParam String plane_code, @RequestParam String dep_time,
							 @RequestParam String arr_time, @RequestParam String arr_date, @RequestParam String departureTime,
							 @RequestParam String stopover_airport, @RequestParam String stopover_arrtime, @RequestParam String stopover_deptime,

							 ModelMap model) {


		Flight[] flights;

		flights = getFlightListReturning(departing, assisted, departure, arrival, class_typeSearch, passengers, triptype, retdate, retdateend, depdateSearch, depdateend, arrival_airport, budget);

		// Sort according to parameter
		flights = sortFlights(sorting_method, flights);

		if (departing) {

			// Place Sorted list into Model
			model.put("depflights", flights);

		} else {

			// Place Sorted list into Model
			model.put("retflights", flights);
		}


		// Put in Departing Flight
		Date departureTimeAsDate = null;

		//2018-08-31 04:35:00.0
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S", Locale.ENGLISH);
		try { departureTimeAsDate =  df.parse(departureTime); } catch (ParseException e) { }//// Occasionally throws this


		Flight depflight = new Flight(flightNumber, airlineName, airlineCode, departure_airport, arrival_airport, duration,
				stop_overs, cost, depdate, class_type, plane_code, dep_time,
				arr_date, arr_time, stopover_airport, stopover_arrtime, stopover_deptime, departureTimeAsDate);

		model.put("depflight", depflight);

		// Place all other parameters into model

		model.put("departure", departure);
		model.put("arrival", arrival);
		model.put("class_typeSearch", class_typeSearch);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdateSearch);
		model.put("retdate", retdate);
		model.put("retdateend", retdateend);
		model.put("assisted", assisted);
		model.put("sortoption", sorting_method);

		model.put("usercategory", usercategory);
		model.put("departing", departing);

		model.put("budget", budget);
		model.put("depdateend", depdateend);

		// Return to Results Page
		return "Return";

	}

	private Flight[] getFlightListReturning (boolean departing, boolean assisted, String departure, String arrival, String class_typeSearch, int passengers,
											 String triptype, String retdate, String retdateend, String depdateSearch, String depdateend, String arrival_airport, int budget) {

		Flight[] flights;

		if (departing) {

			// Perform Search
			if (! assisted)
				flights = getSearchResults(departure, arrival, class_typeSearch, passengers, triptype, depdateSearch);
			else {
				List<Flight> flightList = getAssistedSearchResults(budget, departure, arrival, class_typeSearch, passengers, triptype, depdateSearch, depdateend, retdate);
				flights = new Flight[flightList.size()];

				for (int i = 0; i < flightList.size(); i++) {
					flights[i] = flightList.get(i);
				}
			}


		} else {

			// Perform Search
			if (! assisted)
				flights = getSearchResults(arrival, departure, class_typeSearch, passengers, triptype, retdate);
			else {

				List<Flight> flightList = getRetFlights(arrival_airport, departure, class_typeSearch, passengers, triptype, retdate, retdateend);

				flights = new Flight[flightList.size()];
				for (int i = 0; i < flightList.size(); i++) {
					flights[i] = flightList.get(i);
				}
			}

		}

		return flights;

	}


	@RequestMapping(value = "/sortoneway", method = RequestMethod.POST)
	public String sortoneway(@RequestParam String departure, @RequestParam String arrival,
							 @RequestParam String class_typeSearch, @RequestParam int passengers,
							 @RequestParam String triptype, @RequestParam String depdateSearch, @RequestParam String retdate,
							 @RequestParam int budget, @RequestParam String depdateend,
							 @RequestParam String sorting_method, @RequestParam boolean specific, ModelMap model) {


		Flight[] flights = getFlightList (departure, arrival, class_typeSearch, passengers, triptype, depdateSearch, budget, depdateend, retdate, specific);

		// Sort according to parameter
		flights = sortFlights(sorting_method, flights);

		// Place Sorted list into Model
		model.put("depflights", flights);

		// Place all other parameters into model

		model.put("departure", departure);
		model.put("arrival", arrival);
		model.put("class_typeSearch", class_typeSearch);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdateSearch);
		model.put("retdate", retdate);
		model.put("specific", specific);
		model.put("sortoption", sorting_method);

		model.put("budget", budget);
		model.put("depdateend", depdateend);

		// Return to Results Page
		return "Results";

	}

	private Flight[] getFlightList (String departure, String arrival, String class_typeSearch, int passengers, String triptype, String depdateSearch, int budget, String depdateend, String retdate, boolean specific) {


		Flight[] flights;

		// Perform Search
		if (specific)
			flights = getSearchResults(departure, arrival, class_typeSearch, passengers, triptype, depdateSearch);
		else {
			List<Flight> flightList = getAssistedSearchResults(budget, departure, arrival, class_typeSearch, passengers, triptype, depdateSearch, depdateend, retdate);
			flights = new Flight[flightList.size()];

			for (int i = 0; i < flightList.size(); i++) {
				flights[i] = flightList.get(i);
			}
		}

		return flights;
	}

	private Flight[] sortFlights (String sorting_method, Flight[] flights) {


		if (sorting_method.equals("mincost")){
			flights = sortlist_mincost(flights);
		}

		else if (sorting_method.equals("maxcost")){

			flights = sortlist_maxcost(flights);
		}

		else if (sorting_method.equals("minduration")){

			flights = sortlist_minduration(flights);
		}

		else if (sorting_method.equals("maxduration")){

			flights = sortlist_maxduration(flights);
		}

		return flights;

	}

	public Flight[] sortlist_maxcost (Flight[] list) {

		for (int i = 0; i < list.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < list.length; j++)
				if (list[j].getCost() > list[index].getCost())
					index = j;

			Flight lowestcost = list[index];
			list[index] = list[i];
			list[i] = lowestcost;
		}

		return list;
	}

	public Flight[] sortlist_mincost (Flight[] list) {

		for (int i = 0; i < list.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < list.length; j++)
				if (list[j].getCost() < list[index].getCost())
					index = j;

			Flight lowestcost = list[index];
			list[index] = list[i];
			list[i] = lowestcost;
		}

		return list;
	}

	public Flight[] sortlist_minduration (Flight[] list) {

		for (int i = 0; i < list.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < list.length; j++)
				if (list[j].getDuration() < list[index].getDuration())
					index = j;

			Flight lowestdur = list[index];
			list[index] = list[i];
			list[i] = lowestdur;
		}

		return list;
	}

	public Flight[] sortlist_maxduration (Flight[] list) {

		for (int i = 0; i < list.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < list.length; j++)
				if (list[j].getDuration() > list[index].getDuration())
					index = j;

			Flight lowestdur = list[index];
			list[index] = list[i];
			list[i] = lowestdur;
		}

		return list;
	}

	@RequestMapping(value = "/filteroneway", method = RequestMethod.POST)
	public String filteroneway(@RequestParam boolean direct, @RequestParam boolean onestop, @RequestParam boolean twostops,
							   @RequestParam boolean depmorning, @RequestParam boolean depafternoon, @RequestParam boolean depevening, @RequestParam boolean deplate,
							   @RequestParam boolean arrmorning, @RequestParam boolean arrafternoon, @RequestParam boolean arrevening, @RequestParam boolean arrlate,

							   @RequestParam String departure, @RequestParam String arrival,
							   @RequestParam String class_typeSearch, @RequestParam int passengers,
							   @RequestParam String triptype, @RequestParam String depdateSearch, @RequestParam String retdate,
							   @RequestParam int budget, @RequestParam String depdateend, @RequestParam boolean specific,

							   ModelMap model) {

		// Get Flight List

		Flight[] flights = getFlightList (departure, arrival, class_typeSearch, passengers, triptype, depdateSearch, budget, depdateend, retdate, specific);


		// Filter list

		List<Flight> flightList = filterList (flights, direct, onestop, twostops, depmorning, depafternoon, depevening, deplate, arrmorning, arrafternoon, arrevening, arrlate);

		if(flightList == null || flightList.size() == 0)
			model.put("noflightsfound", true);

		// Put data back in

		model.put("departure", departure);
		model.put("arrival", arrival);
		model.put("class_typeSearch", class_typeSearch);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdateSearch);
		model.put("retdate", retdate);
		model.put("specific", specific);


		model.put("sortoption", "none");
		model.put("budget", budget);
		model.put("depdateend", depdateend);

		model.put("depflights", flightList);
		return "Results";

	}

	@RequestMapping(value = "/filterreturn", method = RequestMethod.POST)
	public String filterreturn(@RequestParam boolean direct, @RequestParam boolean onestop, @RequestParam boolean twostops,
							   @RequestParam boolean depmorning, @RequestParam boolean depafternoon, @RequestParam boolean depevening, @RequestParam boolean deplate,
							   @RequestParam boolean arrmorning, @RequestParam boolean arrafternoon, @RequestParam boolean arrevening, @RequestParam boolean arrlate,

							   @RequestParam String departure, @RequestParam String arrival,
							   @RequestParam String class_typeSearch, @RequestParam int passengers,
							   @RequestParam String triptype, @RequestParam String depdateSearch, @RequestParam String retdate,
							   @RequestParam int budget, @RequestParam String depdateend,
							   @RequestParam boolean departing, @RequestParam boolean assisted,
							   @RequestParam String usercategory, @RequestParam String retdateend,

							   @RequestParam String airlineName, @RequestParam String airlineCode, @RequestParam String flightNumber, @RequestParam String departure_airport,
							   @RequestParam String arrival_airport, @RequestParam int duration, @RequestParam int stop_overs, @RequestParam double cost,
							   @RequestParam String depdate, @RequestParam String class_type, @RequestParam String plane_code, @RequestParam String dep_time,
							   @RequestParam String arr_time, @RequestParam String arr_date, @RequestParam String departureTime,
							   @RequestParam String stopover_airport, @RequestParam String stopover_arrtime, @RequestParam String stopover_deptime,

							   ModelMap model) {

		Flight[] flights;

		flights = getFlightListReturning(departing, assisted, departure, arrival, class_typeSearch, passengers, triptype, retdate, retdateend, depdateSearch, depdateend, arrival_airport, budget);

		List<Flight> flightList = filterList (flights, direct, onestop, twostops, depmorning, depafternoon, depevening, deplate, arrmorning, arrafternoon, arrevening, arrlate);

		if(flightList == null || flightList.size() == 0)
			model.put("noflightsfound", true);

		if (departing) {

			// Place list into Model
			model.put("depflights", flightList);

		} else {

			// Place list into Model
			model.put("retflights", flightList);
		}


		// Put in Departing Flight
		Date departureTimeAsDate = null;

		//2018-08-31 04:35:00.0
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S", Locale.ENGLISH);
		try { departureTimeAsDate =  df.parse(departureTime); } catch (ParseException e) {  } // Occasionally throws this

		Flight depflight = new Flight(flightNumber, airlineName, airlineCode, departure_airport, arrival_airport, duration,
				stop_overs, cost, depdate, class_type, plane_code, dep_time,
				arr_date, arr_time, stopover_airport, stopover_arrtime, stopover_deptime, departureTimeAsDate);

		model.put("depflight", depflight);


		// Place all other parameters into model

		model.put("departure", departure);
		model.put("arrival", arrival);
		model.put("class_typeSearch", class_typeSearch);
		model.put("passengers", passengers);
		model.put("triptype", triptype);
		model.put("depdateSearch", depdateSearch);
		model.put("retdate", retdate);
		model.put("retdateend", retdateend);
		model.put("assisted", assisted);

		model.put("usercategory", usercategory);
		model.put("departing", departing);
		model.put("sortoption", "none");


		model.put("budget", budget);
		model.put("depdateend", depdateend);

		// Return to Results Page
		return "Return";

	}

	private List<Flight> filterList (Flight[] flights, boolean direct, boolean onestop, boolean twostops, boolean depmorning, boolean depafternoon, boolean depevening, boolean deplate,
									 boolean arrmorning, boolean arrafternoon, boolean arrevening, boolean arrlate) {

		List<Flight> flightList = new ArrayList<>();

		int dephour, arrhour;

		boolean add = true;

		if (flights == null) return flightList;

		for (int i = 0; i < flights.length; i++) {

			// FIlter Stop Overs
			if (! direct) {
				if (flights[i].getStop_overs() == 0) {
					add = false;
					continue;
				}
			}
			if (! onestop) {
				if (flights[i].getStop_overs() == 1) {
					add = false;
					continue;
				}
			}
			if (! twostops) {
				if (flights[i].getStop_overs() == 2) {
					add = false;
					continue;
				}
			}

			// Time looks like this: 21:05:00

			dephour = Integer.parseInt(flights[i].getDep_time().substring(0, 2));

			// Filter Departure Time
			if ( ! depmorning) {
				if (dephour >=5 && dephour < 12) {
					add = false;
					continue;
				}
			}
			if (!depafternoon) {
				if (dephour >=12 && dephour < 18) {
					add = false;
					continue;
				}
			}
			if (! depevening) {
				if (dephour >=18) {
					add = false;
					continue;
				}
			}
			if (! deplate) {
				if (dephour < 5) {
					add = false;
					continue;
				}
			}

			// Filter Arrival Time

			arrhour = Integer.parseInt(flights[i].getArr_time().substring(0, 2));

			if (! arrmorning) {
				if (arrhour >=5 && arrhour < 12) {
					add = false;
					continue;
				}
			}
			if (! arrafternoon) {
				if (arrhour >=12 && arrhour < 18) {
					add = false;
					continue;
				}
			}
			if (! arrevening) {
				if (arrhour >=18) {
					add = false;
					continue;
				}
			}
			if (! arrlate) {
				if (arrhour < 5) {
					add = false;
					continue;
				}
			}

			if (add) flightList.add(flights[i]);

		}
		return flightList;
	}

	private Flight[] filterlist (boolean none, boolean one, boolean two, Flight[] list) {

		// Check or uncheck list items

		for (int i = 0; i < list.length; i++) {

			list[i].setDisplay(true);

			if (!none) {
				if (list[i].getStop_overs() == 0) list[i].setDisplay(false);
			}
			if (!one) {
				if (list[i].getStop_overs() == 1) list[i].setDisplay(false);
			}
			if (!two) {
				if (list[i].getStop_overs() == 2) list[i].setDisplay(false);
			}
		}
		return list;
	}


	private List<Flight> sort_by_user_category(List<Flight> list, String usercategory) {


		if (usercategory.equals("standard")){

			list = user_category_sort(list, 1, 2, 3);
		}

		else if (usercategory.equals("highclass")){

			list = user_category_sort(list, 3, 1, 2);
		}

		else if (usercategory.equals("business")){

			list = user_category_sort(list, 3, 2, 1);
		}

		else if (usercategory.equals("leisure")){

			list = user_category_sort(list, 2, 1, 3);
		}

		else if (usercategory.equals("budget")){

			list = user_category_sort(list, 1, 3, 2);
		}

		else if (usercategory.equals("family")){

			list = user_category_sort(list, 2, 3, 1);

		}

		return list;

	}

	private List<Flight> user_category_sort(List<Flight> list, int costval, int ratingval, int stopval) {


		// Score is based on cost vs rating vs stops priority
		// Each gets a value 1, 2, or 3

		// Cost/Rating/Stop Scores are reduced to within 0-5 range
		// Final score = (costscore * costval) + (ratingscore * ratingval) + (stopscore * stopval)

		// Lowest score flight gets highest priority in list


		if (list.size() == 0) return list;

		double[] scores = new double[list.size()];

		double costscore, ratingscore, stopscore;

		double maxprice = list.get(0).getCost();

		// Find max price in list

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCost() > maxprice) maxprice = list.get(i).getCost();
		}

		// Used for reducing all cost values into 0-5 range
		double divVal = maxprice / 5;

		for (int i = 0; i < list.size(); i++) {

			costscore = list.get(i).getCost()/divVal;   // sets all cost in 0-5 range
			ratingscore = 2.5;    					// Hard coded for now

			if (list.get(i).getStop_overs() > 5) stopscore = 5;
			else stopscore = list.get(i).getStop_overs();

			scores[i] = (costscore * costval) + (ratingscore * ratingval) + (stopscore * stopval);
		}

		for (int i = 0; i < list.size() - 1; i++) {
			int index = i;
			for (int j = i + 1; j < list.size(); j++)
				if (scores[j] < scores[index])
					index = j;

			Flight lowestFlightScore = list.get(index);
			double lowestScore = scores[index];

			list.set(index,list.get(i)); /// potential trouble maker

			scores[index] = scores[i];

			list.set(i, lowestFlightScore);
			scores[i] = lowestScore;
		}

		return list;
	}

}
