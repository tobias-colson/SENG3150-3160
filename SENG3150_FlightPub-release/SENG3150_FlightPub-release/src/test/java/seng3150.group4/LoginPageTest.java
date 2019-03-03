package seng3150.group4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Test;

public class LoginPageTest
{
    private WebDriver webDriver;

    @Before
    public void setUp()
    {
        System.setProperty("webdriver.gecko.driver", "C:\\tools\\selenium\\geckodriver.exe");
        webDriver = new FirefoxDriver();
    }

    @Test
    public void loginFormTest()
    {
        webDriver = new FirefoxDriver();
        webDriver.get("http://localhost:8080/login");
        webDriver.findElement(By.id("username")).sendKeys("bcollins");
        webDriver.findElement(By.id("password")).sendKeys("password");
        webDriver.findElement(By.name("login")).click();
    }

    @After
    public void tearDown()
    {
        webDriver.quit();
    }
}
