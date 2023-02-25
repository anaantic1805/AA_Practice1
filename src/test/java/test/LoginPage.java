package test;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginPage {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeMethod
    public void init (){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();

    }
    @AfterMethod
    public void tearDown (){
        driver.quit();
    }

    @Test
    public void signUp() {
        driver.findElement(By.cssSelector("[id=\"signin2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"sign-username\"]"))).sendKeys("nekimail@mail.com");
        driver.findElement(By.cssSelector("[id=\"sign-password\"]")).sendKeys("KakoTako");
        driver.findElement(By.xpath("//button[text() = \"Sign up\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    @Test
    public void logInWithValidPassAndUsername (){
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("nekimail@mail.com");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("KakoTako");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();


    }




}
