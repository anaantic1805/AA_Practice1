package test;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.security.auth.Refreshable;
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
//  @AfterMethod
//   public void tearDown (){
//      driver.quit();
//  }

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
        String verifyTheUserIsLoggedIn = wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id=\"nameofuser\"]"))).getText();
        Assert.assertTrue(verifyTheUserIsLoggedIn.contains("Welcome "));
    }

    @Test
    public void logInWithInvalidUsername () {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("nekiinvalidmail@mail.com");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("KakoTako");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(driver.switchTo().alert().getText(), "User does not exist.");
        driver.switchTo().alert().accept();
    }

    @Test
    public void logInWithInvalidPassword () {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("nekimail@mail.com");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("KakoTakoInvalid");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(driver.switchTo().alert().getText(), "Wrong password.");
        driver.switchTo().alert().accept();
    }

    @Test
    public void logInWithInvalidUsernameAndPassword () {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("nekiInvalidmail@mail.com");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("KakoTakoInvalid2");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(driver.switchTo().alert().getText(), "User does not exist.");
        driver.switchTo().alert().accept();
    }

    @Test
    public void logInWithEmptyFieldsUsernameAndPassword () {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(driver.switchTo().alert().getText(), "Please fill out Username and Password.");
        driver.switchTo().alert().accept();
    }

    @Test
    public void logOut () throws InterruptedException {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("nekimail@mail.com");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("KakoTako");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"logout2\"]"))).click();

    }

    @Test
    public void logInAndVeryfIfUserIsNotLoggedOutAfterRefreshAndBackAndForwardPage () {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys("nekimail@mail.com");
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys("KakoTako");
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        String verifyTheUserIsLoggedIn = wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id=\"nameofuser\"]"))).getText();
        Assert.assertTrue(verifyTheUserIsLoggedIn.contains("Welcome "));
        driver.navigate().refresh();
        wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class=\"nav-item active\"]/a"))).click();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().forward();
        Assert.assertTrue(verifyTheUserIsLoggedIn.contains("Welcome "));
    }

    
}
