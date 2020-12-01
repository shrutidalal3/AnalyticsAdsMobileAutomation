package iHRTAdsTests;


import iHRTAds.*;
import iHRTCommons.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AndroidInlineAds extends BaseTest{

    static Logger logger = Logger.getLogger(AndroidInlineAds.class.getName());


    @Test(enabled = false)
    void AndroidSetupTest() throws TimeoutException, IOException, URISyntaxException, InterruptedException {

        services = AdsDriverSetUp.AppiumServerStart();
        System.out.println("Appium server started");
        DesiredCapabilities options = new DesiredCapabilities();
        options.setCapability("deviceName", propertyManager.getInstance().getdevicename());

        options.setCapability("platformName", propertyManager.getInstance().getplatformName());
        options.setCapability("platformVersion", propertyManager.getInstance().getplatformVersion());
        options.setCapability("automationName", "UiAutomator2");
        options.setCapability("autoGrantPermissions", "true");
        options.setCapability("noReset","true");

        options.setCapability("app", propertyManager.getInstance().getAndroidapp());
        options.setCapability("uiautomator2ServerInstallTimeout", "60000");
        options.setCapability("abdExecTimeout", "60000");

        driver = new AndroidDriver<MobileElement>(services, options);
        System.out.println("Certificate set");
        Thread.sleep(10000);
        iHRTLogin.Login_iHRT(driver);
        iHRTLogin.emailLogin_iHRT(driver,propertyManager.getInstance().getUsernamelogin(),propertyManager.getInstance().getPasswordlogin());
        Thread.sleep(10000);
        //AdsCode.iHRTLogin.DismissFirstLoginAlert(driver);
        iHRTLogin.changesettings(driver);
        Thread.sleep(10000);
        iHRTLogin.SelectTestersOptions(driver);
        Thread.sleep(10000);
        iHRTLogin.DisableAdsFUX(driver);
        Thread.sleep(2000);
        iHRTLogin.changesettings(driver);
        iHRTLogin.changesettings(driver);
        Thread.sleep(10000);
        driver.getPageSource();
        //InterceptedMessage appiumIORequest = messages.stream().filter((m) -> m.requestURL.getPath().contains("events")).findFirst().get();
        // System.out.println(new String(appiumIORequest.requestBody, StandardCharsets.UTF_8));
        // AdsCode.VerifyAds.VerifyRequestEvents(new String(appiumIORequest.requestBody, StandardCharsets.UTF_8),"action","app_open");
        //JsonParser parser = new JsonParser();
        //JsonObject jsonobj = (JsonObject)parser.parse(new String(appiumIORequest.requestBody, StandardCharsets.UTF_8));
        //System.out.println(jsonobj);
        //System.out.println(jsonobj.get("action"));
    }

    @Test
    void YourLibraryInlineAds() throws TimeoutException, IOException, URISyntaxException, InterruptedException {

        services = AdsDriverSetUp.AppiumServerStart();
        AppiumDriver driver = AdsDriverSetUp.getDriver("Android", services);



        Thread.sleep(10000);
        driver.getPageSource();
        LogEntries StartLogs = driver.manage().logs().get("logcat");
        VerifyAds.VerifyInlineAdDisplayed(driver);
        for(LogEntry log : StartLogs){
            var message = log.getMessage();

            if(message.contains("Debug CONTENT")){
                if(message.contains("cust_params")) {
                    String cust_params = Utilities.getParamValue(message.substring(message.indexOf("uri") + 6),"cust_params");
                    if(cust_params.contains("hometab=yourlibrary")){
                        logger.info("Required Ads Request has cust_params: "+  cust_params);
                    }
                   //logger.log(info,"Printing Else.........Required Ads Request has cust_params: "+  cust_params);
                    else logger.info("Printing Else.........Required Ads Request has cust_params: "+  cust_params);




                }
            }





        }
    }

    @Test
    void LiveRadioInlineAds() throws TimeoutException, IOException, URISyntaxException, InterruptedException {
        services = AdsDriverSetUp.AppiumServerStart();
        AppiumDriver driver = AdsDriverSetUp.getDriver("Android", services);



        Thread.sleep(10000);
        driver.getPageSource();
        LogEntries StartLogs = driver.manage().logs().get("logcat");
        iHRTLogin.LiveRadioSelect(driver);

        Thread.sleep(30000);

        VerifyAds.VerifyInlineAdDisplayed(driver);
        Thread.sleep(10000);
        LogEntries secondCallToLogs = driver.manage().logs().get("logcat");
        Stream<LogEntry> str = Utilities.getAdsLogStreams(secondCallToLogs);
        for(LogEntry log : secondCallToLogs){
            var message = log.getMessage();

            if(message.contains("Debug CONTENT")){
                if(message.contains("cust_params")) {
                    String cust_params = Utilities.getParamValue(message.substring(message.indexOf("uri") + 6),"cust_params");
                    if(cust_params.contains("hometab=radio")){
                        logger.info("Required Ads Request has cust_params: "+  cust_params);
                    }
                    else logger.info("Printing Else.........Required Ads Request has cust_params: "+  cust_params);




                }
            }





        }


    }

    @Test
    void LiveProfileInlineAds() throws TimeoutException, IOException, URISyntaxException, InterruptedException {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),this);
        services = AdsDriverSetUp.AppiumServerStart();
        AppiumDriver driver = AdsDriverSetUp.getDriver("Android", services);
        Set<String> logtypes = driver.manage().logs().getAvailableLogTypes();
        String TestFile = "/Users/Shruti/Desktop/untitled folder/logcatlogs.txt";
        File FC = new File(TestFile);
        FC.createNewFile();
        PrintWriter pw = new PrintWriter(TestFile);
        logger.info("suported log types: " + logtypes.toString());
        //List<LogEntry> logs = driver.manage().logs().get("logcat").getAll();


        logger.info("Printing App Launch Logs..................");


        iHRTLogin.LiveRadioSelect(driver);
        LogEntries StartLogs = driver.manage().logs().get("logcat");

        iHRTLogin.radioSelector(driver);
        Thread.sleep(30000);

        LogEntries secondCallToLogs = driver.manage().logs().get("logcat");

        StreamSupport.stream(secondCallToLogs.spliterator(), false)
                .filter((I)->I.getMessage().contains("https://pubads.g.doubleclick.net/gampad/ads"))
        ;
        for(LogEntry log : secondCallToLogs){
            var message = log.getMessage();

            if(message.contains("Debug CONTENT")){
                if(message.contains("cust_params")) {
                    if(message.contains("ccrpos=8004"))
                        logger.info("Printing Headers: " + message.substring(message.indexOf("uri")+6));

                    List<NameValuePair> params = URLEncodedUtils.parse(new URI(message.substring(message.indexOf("uri")+6)), Charset.forName("UTF-8"));
                    for(NameValuePair param: params){
                        logger.info(param.getName() + ":"+param.getValue());
                        pw.println(param.getName() + ":"+param.getValue());
                    }

                }
            }





        }

        //var message =StreamSupport.stream(secondCallToLogs.spliterator(), false).filter((d)->d.getMessage().contains("pubads.g.doubleclick.net")).limit(10).findFirst().get();
        //System.out.println(message);

        logger.info("Printing only Live station Http logs:.............");

        logger.info(Utilities.getAdbLogCat());

        //Utilities.Wait(driver, locs.liveStation);
        //locs.liveStation.isDisplayed();
        //locs.liveStation.click();

        //long size = messages.stream().filter((m) -> m.requestURL.getHost().startsWith("us.api.iheart.com/api/v3/playback/livestation/reporting")).count();
        //InterceptedMessage appiumIORequest1 = messages.stream().filter((m) -> m.requestURL.getHost().startsWith("Us.api.iheart.com/api/v3/playback/livestation/reporting")).findFirst().get();
        //System.out.println(appiumIORequest1.requestURL);
        // List<NameValuePair> params = URLEncodedUtils.parse(new URI(appiumIORequest1.requestURL.toString()), Charset.forName("UTF-8"));
        //for (NameValuePair param : params) {
        // System.out.println(param.getName() + ":" + param.getValue());
        // }


    }

    @Test
    void PlaylistDirectoryInlineAds() throws TimeoutException, IOException, URISyntaxException, InterruptedException {
       locs = new iHRTLocatorsAds(driver);

        Thread.sleep(10000);
        driver.getPageSource();
        LogEntries StartLogs = driver.manage().logs().get("logcat");
        softAssert.assertTrue(locs.VerifyPlaylistTabisDisplayed());
        locs.clickPlaylistatb();
        logger.info("Playlist tab loaded");
       iHRTLogin.DismissTransistionAd(driver);
       softAssert.assertTrue(locs.VerifyInlineAdDisplayed());
        //VerifyAds.VerifyInlineAdDisplayed(driver);
        Thread.sleep(5000);
        LogEntries secondCallToLogs = driver.manage().logs().get("logcat");

        logger.info( Utilities.getAdsLogStreams(secondCallToLogs).toString());
        Utilities.getAdsLogStreams(secondCallToLogs).forEach(s->System.out.println(s));
        for(LogEntry log : secondCallToLogs) {
            var message = log.getMessage();

            if (message.contains("Debug CONTENT")) {
                if (message.contains("cust_params")) {
                    String cust_params = Utilities.getParamValue(message.substring(message.indexOf("uri") + 6), "cust_params");
                    softAssert.assertTrue(cust_params.contains("hometab=playlist"));
                    softAssert.assertTrue(cust_params.contains("ccrpos=8004"));
                    //Assert.assertTrue(cust_params.contains("hometab=playlists"));
                    {
                        logger.info("Required Ads Request has cust_params: " + cust_params);

                        if(cust_params.contains("ccrpos=8004")){
                            logger.info("Required Inline ad and ad request is displayed, with ccrpos = 8004 and hometab = playlists");
                            break;
                        }
                    } //else System.out.println("Printing Else.........Required Ads Request has cust_params: " + cust_params);


                }
            }

            softAssert.assertAll();
        }
    }

    @Test
    void PlaylistSectionInlineAds() throws TimeoutException, IOException, URISyntaxException, InterruptedException {

        locs = new iHRTLocatorsAds(driver);

        Thread.sleep(10000);
        driver.getPageSource();
        LogEntries StartLogs = driver.manage().logs().get("logcat");
        softAssert.assertTrue(locs.VerifyPlaylistTabisDisplayed());
        locs.clickPlaylistatb();
        log.info("Playlist tab loaded");
        iHRTLogin.DismissTransistionAd(driver);
    }

    @Test
    void PodcastDirectoryInlineAds() throws InterruptedException, URISyntaxException {
        locs = new iHRTLocatorsAds(driver);

        Thread.sleep(10000);
        driver.getPageSource();
        LogEntries StartLogs = driver.manage().logs().get("logcat");
        softAssert.assertTrue(locs.VerifyPodcastTabisDisplayed());
        locs.clickPodcasttab();
        log.info("Podcast tab loaded");
        iHRTLogin.DismissTransistionAd(driver);
        softAssert.assertTrue(locs.VerifyInlineAdDisplayed());
        //VerifyAds.VerifyInlineAdDisplayed(driver);
        Thread.sleep(5000);
        LogEntries secondCallToLogs = driver.manage().logs().get("logcat");
        for(LogEntry log : secondCallToLogs) {
            var message = log.getMessage();

            if (message.contains("Debug CONTENT")) {
                if (message.contains("cust_params")) {
                    String cust_params = Utilities.getParamValue(message.substring(message.indexOf("uri") + 6), "cust_params");
                    softAssert.assertTrue(cust_params.contains("hometab=podcast"));
                    softAssert.assertTrue(cust_params.contains("ccrpos=8004"));
                    //Assert.assertTrue(cust_params.contains("hometab=playlists"));
                    {
                        System.out.println("Required Ads Request has cust_params: " + cust_params);

                        if(cust_params.contains("ccrpos=8004")){
                            System.out.println("Required Inline ad and ad request is displayed, with ccrpos = 8004 and hometab = podcast");
                            break;
                        }
                    } //else System.out.println("Printing Else.........Required Ads Request has cust_params: " + cust_params);


                }
            }

            softAssert.assertAll();
        }
    }

    @Test
    void ArtistRadioInlineAd() throws InterruptedException, URISyntaxException {
        locs = new iHRTLocatorsAds(driver);

        Thread.sleep(10000);
        driver.getPageSource();
        LogEntries StartLogs = driver.manage().logs().get("logcat");
        iHRTLogin.LiveRadioSelect(driver);
        logger.info("navigated to live radio tab");
        iHRTLogin.DismissTransistionAd(driver);
        //iHRTLogin.scrolltoRecommendedArtist(driver);
        Utilities.scrolltoIntoView(driver,"Genres");
        Thread.sleep(10000);
        iHRTLogin.selectArtistRadio(driver);
        //locs.clickArtistRadio();
        Thread.sleep(10000);
        softAssert.assertTrue(locs.VerifyInlineAdDisplayed());
        Thread.sleep(10000);
        LogEntries secondCallToLogs = driver.manage().logs().get("logcat");
        for(LogEntry log : secondCallToLogs) {
            var message = log.getMessage();

            if (message.contains("Debug CONTENT")) {
                if (message.contains("cust_params")) {
                    if(message.contains("ccrpos=8004")){
                        String cust_params = Utilities.getParamValue(message.substring(message.indexOf("uri") + 6), "cust_params");
                        softAssert.assertTrue(cust_params.contains("hometab=artistprofile"));
                        softAssert.assertTrue(cust_params.contains("ccrpos=8004"));
                        //Assert.assertTrue(cust_params.contains("hometab=playlists"));
                        {
                            System.out.println("Required Ads Request has cust_params: " + cust_params);

                            if(cust_params.contains("ccrpos=8004")){
                                System.out.println("Required Inline ad and ad request is displayed, with ccrpos = 8004 and hometab = artistprofile");
                                break;
                            }

                    }


                    }
                    else System.out.println("Printing Else.........Required Ads Request has cust_params: " );


                }
            }

            softAssert.assertAll();
        }

    }

    @Test
    void SampleTest(){
        System.out.println("Sample Test");
        System.out.println(System.getProperty("user.dir") + "/src/global.properties");
        System.out.println(propertyManager.getInstance().getAndroidapp());
        log.info("Sample Test");
        log.info(System.getProperty("user.dir") + "/src/global.properties");
        log.info(propertyManager.getInstance().getAndroidapp());
        log.info(System.getProperty("user.home")+System.getProperty("file.separator")+"AnalyticsAdsMobileAutomation"+System.getProperty("file.separator"));
    }


}
