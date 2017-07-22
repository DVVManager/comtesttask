import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import driverhelper.DriverProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;


import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Administrator on 7/11/2017.
 */
@ContextConfiguration(locations = {"classpath*:bean.xml"})
public class TestBase extends AbstractTestNGSpringContextTests  implements ITestListener {

    public static Logger logger = Logger.getLogger(TestBase.class);

    public HomePage loadHomePage(){

        return page(HomePage.class).get();
    }

    @BeforeMethod
    public void beforeTestMethod(Method method){
        logger.info("|Going to start '"+ method.getName().toUpperCase()+"' method |" );
    }

    @AfterMethod
    public void afterTestMethod(Method method){
        logger.info("|Finishing '"+ method.getName().toUpperCase()+"' method |" );
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("| Starting Test "+iTestResult.getMethod().getMethodName()+" |");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info("| Test Passed |");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        try {
            screenshot();
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("| Test fail was mot captured |");
        }
        logger.warn("| Test Failed |");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.warn("| Test Skipped |");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logger.info("| Test partly failed }");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info(" Started :");
        DriverProvider.getProvider().initWebDriver();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info(" Finished :");
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        Screenshots.takeScreenShot("SCREEN_"+ RandomStringUtils.randomAlphabetic(5));
        File screenshot = Screenshots.getLastScreenshot();
        return Files.toByteArray(screenshot);
    }
}
