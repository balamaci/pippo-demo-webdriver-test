package ro.fortsoft.pippo.demo.integration;

import com.google.common.base.Function;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ro.fortsoft.pippo.demo.integration.util.UrlUtil;

/**
 * @author Serban Balamaci
 */
public class TestCrudNgDemoApp extends BaseWebdriverTest {

    @Test public void
    forAuthorizedUserLoginSuccessful() {
        WebDriver driver = getDriver();
        System.out.println("Base url " + getBaseUrl());
        driver.get(getBaseUrl() + "login");


        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return webDriver.findElement(By.name("username")) != null;
            }
        });

        fillUserLoginData(driver);

        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return webDriver.getTitle().equals("Contacts");
            }
        });
    }

    private void fillUserLoginData(WebDriver driver) {
        String username = "admin";
        WebElement txtUsername = driver.findElement(By.name("username"));
        txtUsername.sendKeys(username);

        String password = "password";
        WebElement txtPassword = driver.findElement(By.name("password"));
        txtPassword.sendKeys(password);

        WebElement btnLogin = driver.findElement(By.name("login"));
        btnLogin.click();
    }
}
