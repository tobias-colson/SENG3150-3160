package seng3150.group4;

/*
 * Created By: Benjamin Collins
 * Data Last Modified: 7/09/2018
 * Purpose: Contains all controller methods for user management including
 *      * login
 *      * register
 *      * logout
 *      * flight history
 *      * change details
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import seng3150.group4.dao.IUserDao;
import seng3150.group4.entity.FlightHistoryEntity;
import seng3150.group4.entity.UserEntity;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class UserManagementController {

    @Autowired
    IUserDao userDao;

    private static EntityManager em;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "Login";
    }

    @RequestMapping(value = "/login/authenticate", method = RequestMethod.POST)
    public ModelAndView authenticate(HttpServletRequest request, ModelMap model)
    {
        String hashedPassword = hashPassword(request.getParameter("password"));
        UserEntity existingUser = userDao.getUserByUserName(request.getParameter("username"));
        if (existingUser == null)
        {
            model.put("error", "There is no account under that username.");
        }
        else
        {
            if (hashedPassword.equals(existingUser.getPasswordHash()))
            {
                request.getSession().setAttribute("user", existingUser);
                return new ModelAndView("Search", model);
            }
            else
            {
                model.put("error", "Password is incorrect.");
            }
        }
        return new ModelAndView("Login", model);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        return "Login";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView account(HttpSession session, ModelMap model)
    {
        if (session.getAttribute("user") == null)
        {
            model.put("error", "You are not logged in.");
            return new ModelAndView("Login", model);
        }
        return new ModelAndView("Account", model);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register()
    {
        return "Register";
    }

    @RequestMapping(value = "/register/confirm", method = RequestMethod.POST)
    public ModelAndView registerConfirm(HttpServletRequest request, ModelMap model)
    {
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (!password.equals(password2))
        {
            // user has not typed the same password twice
            model.put("error", "The passwords entered do not match.");
            return new ModelAndView("Register", model);
        }

        UserEntity existingUser = userDao.getUserByUserName(request.getParameter("username"));

        if (existingUser != null)
        { // user with that username or email already exists
            model.put("error", "That username has already been taken.");
            return new ModelAndView("Register", model);
        }

        String hashedPassword = hashPassword(request.getParameter("password"));

        userDao.createNewUser(request.getParameter("username")
                            ,request.getParameter("title")
                            ,request.getParameter("firstname")
                            ,request.getParameter("lastname")
                            ,request.getParameter("phonenum")
                            ,request.getParameter("dob")
                            ,request.getParameter("email")
                            ,hashedPassword);

        return new ModelAndView("Login", model);
    }

    @RequestMapping(value = "/changedetails", method = RequestMethod.POST)
    public ModelAndView changeDetails(HttpSession session, ModelMap model)
    {
        if (session.getAttribute("user") == null)
        {
            model.put("error", "You are not logged in.");
            return new ModelAndView("Login", model);
        }
        return new ModelAndView("ChangeDetails", model);
    }

    @RequestMapping(value = "/changedetails/confirm", method = RequestMethod.POST)
    public ModelAndView changeDetailsConfirm(HttpServletRequest request, ModelMap model) {
        UserEntity currentUser = (UserEntity) request.getSession().getAttribute("user");

        String password = (request.getParameter("password").equals("")) ? "" : request.getParameter("password");
        String password2 = (request.getParameter("password2").equals("")) ? "" : request.getParameter("password2");

        if (!password.equals("") && !password2.equals(""))
        {
            if (!password.equals(password2))
            {
                // user has not typed the same password twice
                model.put("error", "The passwords entered do not match.");
                return new ModelAndView("Account", model);
            }
        }
        // XOR: if one is empty and the other is not empty
        if ((password.equals("") || password2.equals("")) && (!(password.equals("")) || !(password2.equals(""))))
        {
            model.put("error", "You must enter the password in both fields.");
            return new ModelAndView("Account", model);
        }

        String hashedPassword = (password.equals("") && password2.equals("")) ? currentUser.getPasswordHash()
                : hashPassword(request.getParameter("password"));

        userDao.updateUser(currentUser
                ,request.getParameter("title")
                ,request.getParameter("firstname")
                ,request.getParameter("lastname")
                ,request.getParameter("phonenum")
                ,request.getParameter("dob")
                ,request.getParameter("email")
                ,hashedPassword);

        return new ModelAndView("Account", model);
    }

    @RequestMapping(value = "/account/flighthistory", method = RequestMethod.GET)
    public ModelAndView flightHistory(HttpSession session, ModelMap model)
    {
        UserEntity currentUser = (UserEntity) session.getAttribute("user");
        List<FlightHistoryEntity> flights = userDao.getFlightHistory(currentUser);
        model.put("flights", flights);

        return new ModelAndView("FlightHistory", model);
    }

    // Returns the hashed variation of the user's password for storage
    private String hashPassword(String password) {
        String hashedPassword = null;
        try
        {
            MessageDigest mD = MessageDigest.getInstance("MD5");
            mD.update(password.getBytes());
            byte[] pBytes = mD.digest();
            StringBuilder sB = new StringBuilder();
            for (byte pByte : pBytes)
            {
                sB.append(Integer.toString((pByte & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sB.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}
