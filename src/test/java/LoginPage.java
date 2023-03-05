import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


import java.time.Duration;

public class LoginPage {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeMethod
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Parameters({"username", "password"})
    public void signUp(String username, String password) {
        driver.findElement(By.cssSelector("[id=\"signin2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"sign-username\"]"))).sendKeys(username);
        driver.findElement(By.cssSelector("[id=\"sign-password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//button[text() = \"Sign up\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent()).accept();
    }


    @Test
    @Parameters({"username", "password", "selector", "text"})
      public void logIn(String username, String password, String selector, String text) {
          driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
          wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys(username);
          driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys(password);
          driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
          wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
          Assert.assertEquals(driver.findElement(By.cssSelector(selector)).getText(), text);
      }

    @Test
    @Parameters({"username", "password", "text"})
    public void invalidLogIn (String username, String password, String text) {
        driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
        wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys(username);
        driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys(password);
        driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
        wdwait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(driver.switchTo().alert().getText(), text);
        driver.switchTo().alert().accept();
    }


      @Test
      @Parameters({"username", "password"})
      public void logOut (String username, String password) {
          driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
          wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys(username);
          driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys(password);
          driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
          wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"logout2\"]"))).click();
      }

      @Test
      @Parameters({"username", "password", "selector", "text"})
      public void logInAndVerifyIfUserIsNotLoggedOutAfterRefreshAndBackAndForwardPage (String username, String password, String selector, String text) {
          driver.findElement(By.cssSelector("[id=\"login2\"]")).click();
          wdwait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id=\"loginusername\"]"))).sendKeys(username);
          driver.findElement(By.cssSelector("[id=\"loginpassword\"]")).sendKeys(password);
          driver.findElement(By.xpath("//button[text() = \"Log in\"]")).click();
          wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
          Assert.assertEquals(driver.findElement(By.cssSelector(selector)).getText(), text);
          driver.navigate().refresh();
          wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class=\"nav-item active\"]/a"))).click();
          driver.navigate().back();
          driver.navigate().back();
          driver.navigate().forward();
          wdwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
          Assert.assertEquals(driver.findElement(By.cssSelector(selector)).getText(), text);
      }


}
