package ro.fortsoft;

import com.google.common.base.Function;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Serban Balamaci
 */
public class TestSystemCheckWD extends BaseWebdriverTest {

    public static final String HOMEPAGE = "pippo";

    @Test
    public void testSystemCheck() {
        WebDriver driver = getDriver();
        getDriver().get(serverUrl + "/" + HOMEPAGE + "/login");

        String source = getDriver().getPageSource();

        String username = "admin";
        WebElement txtUsername = driver.findElement(By.name("username"));
        txtUsername.sendKeys(username);

        String password = "admin";
        WebElement txtPassword = driver.findElement(By.name("password"));
        txtPassword.sendKeys(password);

        WebElement btnLogin = driver.findElement(By.id("login"));
        btnLogin.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        wait.until(new Function<WebDriver, Object>() {
            @Override
            public Object apply(WebDriver webDriver) {
                webDriver.getPageSource();
            }
        });
        System.out.println("Source " + source);
    }

}
