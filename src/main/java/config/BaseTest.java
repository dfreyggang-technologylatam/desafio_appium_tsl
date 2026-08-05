package config;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;



public class BaseTest {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        driver = new AndroidDriver(
                URI.create(CapabilitiesManager.getAppiumServerUrl()).toURL(),
                CapabilitiesManager.getAndroidOptions());
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
