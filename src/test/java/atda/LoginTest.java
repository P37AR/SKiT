package atda;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import po.LoginPage;
import po.ProductPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = getDriver();
    }

    @Test
    public void filledUserNameAndPassword() {
        driver.get("https://store.steampowered.com/login/?redir=&redir_ssl=1&snr=1_4_4__global-header");
        final WebElement userNameField=driver.findElement(By.id("input_username"));
        final WebElement passwordField=driver.findElement(By.id("input_password"));
        final WebElement loginButton=driver.findElement(By.id("login_btn_signin"));

        userNameField.sendKeys("testUsername");
        passwordField.sendKeys("testPassword");
        loginButton.click();
    }

    @Test
    public void emptyUserName() {
        driver.get("https://store.steampowered.com/login/?redir=&redir_ssl=1&snr=1_4_4__global-header");
        final WebElement userNameField=driver.findElement(By.id("input_username"));
        final WebElement loginButton=driver.findElement(By.id("login_btn_signin"));
        final WebElement errorMessage=driver.findElement(By.id("error_display"));

        userNameField.sendKeys("");
        loginButton.click();

        assertEquals(errorMessage, "Username is required");
    }


    @Test
    public void shouldOpen() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        assertTrue(loginPage.isLoaded());
    }

    @Test
    public void shouldLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        assertTrue(loginPage.isLoaded());
        loginPage.login("testUsername", "testPassword");
        assertTrue(new ProductPage(driver).isLoaded());

        final WebElement errorMessage=driver.findElement(By.id("error_display"));
        assertEquals(errorMessage, "Invalid UserName and Password");

    }

    @Test
    public void canNotLoginWithInvalidUserName() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        assertTrue(loginPage.isLoaded());
        loginPage.login("testUsername1", "secret");
        String errorMessage = loginPage.getErrorMessage();
        assertEquals(errorMessage, "Test");

    }

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users\\user\\IdeaProjects\\Domasna3\\chromedriver");
        return new ChromeDriver();
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }

}