package ro.fortsoft.pippo.demo.integration;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;

/**
 * @author Serban Balamaci
 */
public abstract class BaseWebdriverTest {

    private WebDriver driver;

    private static PhantomJSDriverService phantomJSDriverService;

    protected static String serverUrl;
    protected static String appContext;

    @BeforeClass
    public static void init() throws Exception {
        serverUrl = "http://" + System.getProperty("container.host");
        appContext = System.getProperty("webapp.deploy.context");

        phantomJSDriverService = PhantomJSDriverService.createDefaultService();
        phantomJSDriverService.start();
    }

    @Before
    public void before() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, Boolean.TRUE);

        LoggingPreferences prefs = new LoggingPreferences();
        prefs.enable(LogType.BROWSER, Level.WARNING);
        prefs.enable(LogType.DRIVER, Level.INFO);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);

        driver = new PhantomJSDriver(phantomJSDriverService, capabilities);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    @AfterClass
    public static void globalTearDown() {
        if (phantomJSDriverService != null) {
            phantomJSDriverService.stop();
        }
    }
}
