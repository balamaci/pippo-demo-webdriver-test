package ro.fortsoft.pippo.demo.integration.rule;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author sbalamaci
 */
public class TakeScreenshotOnFailedTaskRule extends TestWatcher {

    private WebDriver driver;
    private String screenshotPath;


    public TakeScreenshotOnFailedTaskRule() {
        screenshotPath = System.getProperty("webdriver.screenshot.path");
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String testClass = description.getTestClass().getSimpleName();

        File destFile = getDestinationFile(testClass);
        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private File getDestinationFile(String testClass) {
        Path path = Paths.get(screenshotPath);
        createDirectoryIfNotExists(path);

        String fileName = testClass + ".png";
        path = path.resolve(fileName);
        return path.toFile();
    }

    private void createDirectoryIfNotExists(Path path) {
        boolean pathExists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);

        if(! pathExists) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
