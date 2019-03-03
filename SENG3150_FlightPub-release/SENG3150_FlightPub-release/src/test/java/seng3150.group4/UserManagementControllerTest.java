package seng3150.group4;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import seng3150.group4.dao.IUserDao;
import seng3150.group4.entity.FlightHistoryEntity;
import seng3150.group4.entity.FlightHistoryId;
import seng3150.group4.entity.UserEntity;
import seng3150.group4.test.config.TestBeanConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class UserManagementControllerTest
{
    @Mock
    IUserDao userDao;

    @InjectMocks
    private UserManagementController userManagementController;

    @Test
    public void loginTest()
    {
        String result = userManagementController.login();
        assertThat(result, is("Login"));
    }

    // Success Test
    @Test
    public void authenticateTest()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");
        ue.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99");

        when(userDao.getUserByUserName(any())).thenReturn(ue);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        when(mockedRequest.getSession()).thenReturn(session);
        when(mockedRequest.getParameter("username")).thenReturn("bcollins");
        when(mockedRequest.getParameter("password")).thenReturn("password");

        ModelAndView result = userManagementController.authenticate(mockedRequest, new ModelMap());

        // successful execution and no error message
        assertThat(result.getModelMap().get("error"), is(nullValue()));
        assertThat(result.getViewName(), is("Search"));
    }

    // No Account Found Test
    @Test
    public void authenticateTest2()
    {
        when(userDao.getUserByUserName(any())).thenReturn(null);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        when(mockedRequest.getParameter("username")).thenReturn("kenobi");
        when(mockedRequest.getParameter("password")).thenReturn("password");

        ModelAndView result = userManagementController.authenticate(mockedRequest, new ModelMap());

        // No account found
        assertThat(result.getModelMap().get("error"), is("There is no account under that username."));
        assertThat(result.getViewName(), is("Login"));
    }

    // Incorrect Password Test
    @Test
    public void authenticateTest3()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");
        ue.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99");

        when(userDao.getUserByUserName(any())).thenReturn(ue);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        when(mockedRequest.getParameter("username")).thenReturn("bcollins");
        when(mockedRequest.getParameter("password")).thenReturn("wrongpassword");

        ModelAndView result = userManagementController.authenticate(mockedRequest, new ModelMap());

        // Incorrect password
        assertThat(result.getModelMap().get("error"), is("Password is incorrect."));
        assertThat(result.getViewName(), is("Login"));
    }

    // Success Test
    @Test
    public void logoutTest()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("testUser");

        HttpSession session = Mockito.mock(HttpSession.class);
        session.setAttribute("user", ue);

        String result = userManagementController.logout(session);

        assertThat(session.getAttribute("user"), is(nullValue()));
        assertThat(result, is("Login"));
    }

    // Success Test
    @Test
    public void accountTest()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");

        HttpSession session = Mockito.mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(ue);

        ModelAndView result = userManagementController.account(session, new ModelMap());

        assertThat(result.getModelMap().get("error"), is(nullValue()));
        assertThat(result.getViewName(), is("Account"));
    }

    // No Account Test
    @Test
    public void accountTest2()
    {
        HttpSession session = Mockito.mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);

        ModelAndView result = userManagementController.account(session, new ModelMap());

        assertThat(result.getModelMap().get("error"), is("You are not logged in."));
        assertThat(result.getViewName(), is("Login"));
    }

    @Test
    public void registerTest()
    {
        String result = userManagementController.register();
        assertThat(result, is("Register"));
    }

    // Success Test
    @Test
    public void registerConfirmTest()
    {
        when(userDao.getUserByUserName(any())).thenReturn(null);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        when(mockedRequest.getParameter("password")).thenReturn("password");
        when(mockedRequest.getParameter("password2")).thenReturn("password");
        when(mockedRequest.getParameter("username")).thenReturn("bcollins");
        when(mockedRequest.getParameter("email")).thenReturn("bcollins@email.com");
        when(mockedRequest.getParameter("title")).thenReturn("MR");
        when(mockedRequest.getParameter("firstname")).thenReturn("Ben");
        when(mockedRequest.getParameter("lastname")).thenReturn("Collins");
        when(mockedRequest.getParameter("phonenum")).thenReturn("0411111111");
        when(mockedRequest.getParameter("dob")).thenReturn("01-01-2001");

        ModelAndView result = userManagementController.registerConfirm(mockedRequest, new ModelMap());

        // successful execution and no error message
        assertThat(result.getModelMap().get("error"), is(nullValue()));
        assertThat(result.getViewName(), is("Login"));
    }

    // Password Mismatch Test
    @Test
    public void registerConfirmTest2()
    {

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        when(mockedRequest.getParameter("password")).thenReturn("password");
        when(mockedRequest.getParameter("password2")).thenReturn("differentPW");

        ModelAndView result = userManagementController.registerConfirm(mockedRequest, new ModelMap());

        assertThat(result.getModelMap().get("error"), is("The passwords entered do not match."));
        assertThat(result.getViewName(), is("Register"));
    }

    // Username Taken Test
    @Test
    public void registerConfirmTest3()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");
        when(userDao.getUserByUserName(any())).thenReturn(ue);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        when(mockedRequest.getParameter("password")).thenReturn("password");
        when(mockedRequest.getParameter("password2")).thenReturn("password");
        when(mockedRequest.getParameter("username")).thenReturn("bcollins");

        ModelAndView result = userManagementController.registerConfirm(mockedRequest, new ModelMap());

        assertThat(result.getModelMap().get("error"), is("That username has already been taken."));
        assertThat(result.getViewName(), is("Register"));
    }

    // Success Test
    @Test
    public void changeDetailsTest()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");

        HttpSession session = Mockito.mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(ue);

        ModelAndView result = userManagementController.changeDetails(session, new ModelMap());

        assertThat(result.getModelMap().get("error"), is(nullValue()));
        assertThat(result.getViewName(), is("ChangeDetails"));
    }

    // Not Logged In Test
    @Test
    public void changeDetailsTest2()
    {
        HttpSession session = Mockito.mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);

        ModelAndView result = userManagementController.changeDetails(session, new ModelMap());

        assertThat(result.getModelMap().get("error"), is("You are not logged in."));
        assertThat(result.getViewName(), is("Login"));
    }

    // Success Test
    @Test
    public void changeDetailsConfirmTest()
    {
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("bcollins");
        existingUser.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99");

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        when(mockedRequest.getSession()).thenReturn(session);
        session.setAttribute("user", existingUser);

        when(mockedRequest.getParameter("password")).thenReturn("newpassword");
        when(mockedRequest.getParameter("password2")).thenReturn("newpassword");
        when(mockedRequest.getParameter("title")).thenReturn("MR");
        when(mockedRequest.getParameter("firstname")).thenReturn("Ben");
        when(mockedRequest.getParameter("lastname")).thenReturn("Collins");
        when(mockedRequest.getParameter("phonenum")).thenReturn("0411111111");
        when(mockedRequest.getParameter("dob")).thenReturn("01-01-2001");
        when(mockedRequest.getParameter("email")).thenReturn("bcollins@email.com");

        ModelAndView result = userManagementController.changeDetailsConfirm(mockedRequest, new ModelMap());

        // successful execution and no error message
        assertThat(result.getModelMap().get("error"), is(nullValue()));
        assertThat(result.getViewName(), is("Account"));
    }

    // Password Mismatch Test
    @Test
    public void changeDetailsConfirmTest2()
    {
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("bcollins");
        existingUser.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99");

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        when(mockedRequest.getSession()).thenReturn(session);
        session.setAttribute("user", existingUser);

        when(mockedRequest.getParameter("password")).thenReturn("newpassword");
        when(mockedRequest.getParameter("password2")).thenReturn("diffpassword");

        ModelAndView result = userManagementController.changeDetailsConfirm(mockedRequest, new ModelMap());

        assertThat(result.getModelMap().get("error"), is("The passwords entered do not match."));
        assertThat(result.getViewName(), is("Account"));
    }

    // Empty Password Field Test
    @Test
    public void changeDetailsConfirmTest3()
    {
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("bcollins");
        existingUser.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99");

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        when(mockedRequest.getSession()).thenReturn(session);
        session.setAttribute("user", existingUser);

        when(mockedRequest.getParameter("password")).thenReturn("");
        when(mockedRequest.getParameter("password2")).thenReturn("newpassword");

        ModelAndView result = userManagementController.changeDetailsConfirm(mockedRequest, new ModelMap());

        assertThat(result.getModelMap().get("error"), is("You must enter the password in both fields."));
        assertThat(result.getViewName(), is("Account"));
    }

    // Past History Test
    @Test
    public void flightHistoryTest()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");

        FlightHistoryId fhid = new FlightHistoryId();
        fhid.setUserID(1);
        fhid.setAirlineCode("OF");
        fhid.setFlightNumber("OF108");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S", Locale.ENGLISH);
        try{ fhid.setDepartureTime(df.parse("2018-09-18 23:50:00")); } catch (ParseException e) { }

        FlightHistoryEntity fhe = new FlightHistoryEntity();
        fhe.setId(fhid);

        List<FlightHistoryEntity> flightHistory = new ArrayList<>();
        flightHistory.add(fhe);

        HttpSession session = Mockito.mock(HttpSession.class);

        when(session.getAttribute("user")).thenReturn(ue);
        when(userDao.getFlightHistory(any())).thenReturn(flightHistory);

        ModelAndView result = userManagementController.flightHistory(session, new ModelMap());

        List<FlightHistoryEntity> modelFlights = (List<FlightHistoryEntity>) result.getModelMap().get("flights");
        // There may be no flights in history, but the list should never be null
        assertThat(modelFlights, is(notNullValue()));
        assertThat(modelFlights.size(), is(1));
        assertThat(result.getViewName(), is("FlightHistory"));
    }

    // No History Test
    @Test
    public void flightHistoryTest2()
    {
        UserEntity ue = new UserEntity();
        ue.setUsername("bcollins");

        List<FlightHistoryEntity> flightHistory = new ArrayList<>();

        HttpSession session = Mockito.mock(HttpSession.class);

        when(session.getAttribute("user")).thenReturn(ue);
        when(userDao.getFlightHistory(any())).thenReturn(flightHistory);

        ModelAndView result = userManagementController.flightHistory(session, new ModelMap());

        List<FlightHistoryEntity> modelFlights = (List<FlightHistoryEntity>) result.getModelMap().get("flights");
        // There may be no flights in history, but the list should never be null
        assertThat(modelFlights, is(notNullValue()));
        assertThat(modelFlights.size(), is(0));
        assertThat(result.getViewName(), is("FlightHistory"));
    }

    @Test
    public void hashPasswordTest() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException
    {
        String input = "password";

        Method method = UserManagementController.class.getDeclaredMethod("hashPassword", String.class);
        method.setAccessible(true);
        String output = (String) method.invoke(userManagementController, input);

        assertThat(output, notNullValue());
        assertThat(output, is("5f4dcc3b5aa765d61d8327deb882cf99"));
    }

    @Test
    public void hashPasswordTest2() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException
    {
        String input = "newpassword";

        Method method = UserManagementController.class.getDeclaredMethod("hashPassword", String.class);
        method.setAccessible(true);
        String output = (String) method.invoke(userManagementController, input);

        assertThat(output, notNullValue());
        assertThat(output, is("5e9d11a14ad1c8dd77e98ef9b53fd1ba"));
    }
}