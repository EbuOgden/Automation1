import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Automation1 {

  static final String alphaNumericDictionary = "abcdefghijklmnopqrstuvwxyz0123456789";
  static public WebDriver driver;

  public static void main(String[] args) throws InterruptedException {
    System.setProperty("webdriver.chrome.driver", "/Users/ebu/Downloads/chromedriver");
    driver = new ChromeDriver();

    String expectedTitle = "Welcome to Duotify!";
    String expectedSignedURL = "http://qa-duotify.us-east-2.elasticbeanstalk.com/browse.php?";
    String expectedLoggedOutURL = "http://qa-duotify.us-east-2.elasticbeanstalk.com/register.php";
    String username = generateUsername(5);
    String password = generateUsername(8);

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    driver.get(expectedLoggedOutURL);

//    Thread.sleep(1000);
    Assert.assertEquals(expectedTitle, driver.getTitle());
    clickElementById("hideLogin");

    sendKeysElementById("username", username);
    sendKeysElementById("firstName", "Michael");
    sendKeysElementById("lastName", "Jackson");
    sendKeysElementById("email", username+"@gmail.com");
    sendKeysElementById("email2", username+"@gmail.com");
    sendKeysElementById("password", password);
    sendKeysElementById("password2", password);

    clickElementByName("registerButton");

    Assert.assertEquals(expectedSignedURL, driver.getCurrentUrl());
    WebElement firstLastName = driver.findElement(By.id("nameFirstAndLast"));

    Assert.assertEquals(firstLastName.getText(), "Michael Jackson");

    firstLastName.click();

    Assert.assertEquals(driver.findElement(By.className("userInfo")).getText(), "Michael Jackson");

    clickElementById("rafael");
    Thread.sleep(250);

    Assert.assertEquals(expectedLoggedOutURL, driver.getCurrentUrl());

    sendKeysElementById("loginUsername", username);
    sendKeysElementById("loginPassword", password);

    clickElementByName("loginButton");

    Thread.sleep(250);

    String context = driver.getPageSource();
    Assert.assertTrue(context.contains("You Might Also Like"));

    clickElementById("nameFirstAndLast");
    clickElementById("rafael");
    driver.quit();
  }

  public static void clickElementById(String element){
    driver.findElement(By.id(element)).click();
  }

  public static void clickElementByName(String element){
    driver.findElement(By.name(element)).click();
  }

  public static void sendKeysElementById(String element, String keys){
    driver.findElement(By.id(element)).sendKeys(keys);
  }

  public static String generateUsername(int length){
    return generateRandNumberWithLength(length);
  }

  public static int randNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static String generateRandNumberWithLength(int length) {
    String number = "";

    for (int i = 0; i < length; i++) {
      number += alphaNumericDictionary.charAt(randNumber(0, alphaNumericDictionary.length() - 1));
    }

    return number;
  }
}
