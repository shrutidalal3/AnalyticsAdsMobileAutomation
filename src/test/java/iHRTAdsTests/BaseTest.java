package iHRTAdsTests;

import iHRTAds.AdsDriverSetUp;
import iHRTAds.iHRTLocatorsAds;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class BaseTest {



     AppiumDriver driver;
    AppiumDriverLocalService services;
     static Logger log = Logger.getLogger(AndroidInlineAds.class.getName());
     iHRTLocatorsAds locs ;
    SoftAssert softAssert = new SoftAssert();

    @BeforeTest
    public void start() throws TimeoutException, IOException, URISyntaxException {
        services = AdsDriverSetUp.AppiumServerStart();
        driver = AdsDriverSetUp.getDriver("Android", services);


    }

    @AfterTest
    public void quit() throws IOException, InterruptedException {

        services.stop();
        //driver.quit();

    }

}
