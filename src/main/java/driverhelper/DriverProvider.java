package driverhelper;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 7/11/2017.
 */

@org.springframework.context.annotation.Configuration
public class DriverProvider {

    private WebDriver webDriver = null;
    private long testTimeoutMilliSeconds;

    @Bean
    public WebDriver getWebDriver() {

        testTimeoutMilliSeconds = Long.parseLong(System.getProperty("test.timeout.milliseconds"));

        if (webDriver == null) {
            String browserType = System.getProperty("browser");
            //if browser type is not provided, we default to firefox
            if(browserType == null) {
                browserType = "firefox";
            }
            createWebDriver(browserType);
            webDriver.manage().timeouts().pageLoadTimeout(testTimeoutMilliSeconds, TimeUnit.MILLISECONDS);
            webDriver.manage().timeouts().setScriptTimeout(testTimeoutMilliSeconds, TimeUnit.MILLISECONDS);
            webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            setUpSelenide();
        }
        return webDriver;
    }

    private void createWebDriver(String browserType) {

        if(browserType.equalsIgnoreCase("firefox")) {
            FirefoxDriverManager.getInstance().setup();
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            webDriver =  new FirefoxDriver(capabilities);
        }
        else if(browserType.equalsIgnoreCase("chrome")) {
            ChromeDriverManager.getInstance().setup();
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            webDriver = new ChromeDriver(capabilities);
        }

        else if(browserType.equalsIgnoreCase("ie")) {
            InternetExplorerDriverManager.getInstance().setup();
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            webDriver = new InternetExplorerDriver(capabilities);
        }
        else {
            // if we get a browserType which we do not recognize, default to firefox
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            webDriver = new FirefoxDriver(capabilities);
        }
    }

    private void setUpSelenide() {
        WebDriverRunner.setWebDriver(webDriver);
        Configuration.screenshots = false;
        Configuration.timeout = testTimeoutMilliSeconds;
        Configuration.collectionsTimeout = testTimeoutMilliSeconds;
        Configuration.pageLoadStrategy = System.getProperty("selenide.pageloadstrategy");
    }
}
