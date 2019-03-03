package seng3150.group4;

import org.hibernate.PropertyValueException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import seng3150.group4.entity.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReviewController {

	private static EntityManager em = null;

	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public String review(HttpServletRequest request, ModelMap model){
		if(em == null) {
			em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").createEntityManager();
		}

		em.getTransaction().begin();
		AppReviewEntity appReview = new AppReviewEntity();
		appReview.setRating(new BigDecimal(request.getParameter("ratingReview")));
		appReview.setComment(request.getParameter("textReview"));
		em.persist(appReview);
		em.getTransaction().commit();

		String referer = request.getHeader("Referer");
		return "redirect:"+ referer;
	}

	@RequestMapping(value = "/account/flighthistory/review", method = RequestMethod.POST)
	public String flightHistoryReview(@RequestParam BigDecimal ratingReview, @RequestParam String flightNumber
									 ,@RequestParam String textReview, HttpSession session, HttpServletRequest request) {
		if (em == null)
		{
			em = (EntityManager) Persistence.createEntityManagerFactory("FlightPubPU").
					createEntityManager();
		}

		em.getTransaction().begin();

		UserEntity currentUser = (UserEntity) session.getAttribute("user");

		FlightReviewId flightReviewId = new FlightReviewId();
		flightReviewId.setUserID(currentUser.getId());
		flightReviewId.setFlightNumber(flightNumber);

		FlightReviewEntity flightReview = new FlightReviewEntity();
		flightReview.setId(flightReviewId);
		flightReview.setRating(ratingReview);
		flightReview.setComments(textReview);
		em.persist(flightReview);

		em.getTransaction().commit();

		String referer = request.getHeader("Referer");
		return "redirect:"+ referer;
	}
}
