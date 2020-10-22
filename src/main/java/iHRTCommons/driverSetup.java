package iHRTCommons;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class driverSetup {


    /**
     * This method creates AppiumDriverLocalServices instance to start appium driver
     * @return AppiumDriverLocalService
     */
    public static AppiumDriverLocalService AppiumServerStart(){
        System.out.println("Into driver setup");
         AppiumDriverLocalService service;

        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();

        serviceBuilder.usingAnyFreePort();
        // /usr/local/bin/node
        serviceBuilder.usingDriverExecutable(new File("/usr/local/bin/node"));
        // /usr/local/bin/appium
        serviceBuilder.withAppiumJS(new File("/usr/local/bin/appium"));
        serviceBuilder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
        serviceBuilder.withArgument(GeneralServerFlag.SHELL);
        HashMap<String, String> environment = new HashMap();
        environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));


        service = AppiumDriverLocalService.buildService(serviceBuilder);
        service.start();
        return service;
    }

    public static void AppiumServerStop(){


    }

    /**
     * This method creates required Appium driver object and return with capabilites from global.properties
     * @param platformtype name of the platform for driver object, accpeted values are "iOS" and "Android"
     * @param service  Appium driver services
     * @return appium driver object
     * @throws IOException
     * @throws TimeoutException
     * @throws URISyntaxException
     */
    public static AppiumDriver getDriver(String platformtype, AppiumDriverLocalService service) throws IOException, TimeoutException, URISyntaxException {
        AppiumDriver driver;

if (platformtype== "Android") {
    DesiredCapabilities options = new DesiredCapabilities();
    options.setCapability("deviceName", propertyManager.getInstance().getdevicename());  //Pixel_XL_API_29
    options.setCapability("udid",propertyManager.getInstance().getdeviceUDID());
    //options.setCapability("avd","Pixel_XL_API_29");
    options.setCapability("platformName", propertyManager.getInstance().getplatformName());
    options.setCapability("platformVersion", propertyManager.getInstance().getplatformVersion());  //emulator: 10.0.0
    options.setCapability("automationName", "UiAutomator2");
    options.setCapability("autoGrantPermissions","true");
    options.setCapability("noReset","true");
    options.setCapability("autoAcceptAlerts","true");
    options.setCapability("app",propertyManager.getInstance().getAndroidapp());
    options.setCapability("uiautomator2ServerInstallTimeout", "60000");
    options.setCapability("abdExecTimeout", "60000");
    options.setCapability("clearDeviceLogsOnStart","true");



    driver = new AndroidDriver<MobileElement>(service, options);
    return driver;
}
else if(platformtype== "iOS"){
    DesiredCapabilities options = new DesiredCapabilities();

    options.setCapability("platformName", "iOS");
    options.setCapability("platformVersion", propertyManager.getInstance().getiOSplatformVersion());
    options.setCapability("deviceName", propertyManager.getInstance().getiOSdevicename());
    options.setCapability("udid",propertyManager.getInstance().getiOSUDID());
    //options.setCapability(CapabilityType.BROWSER_NAME,"safari");
    //options.setCapability("automationName", "XCUITest");
    options.setCapability("bundleId","com.clearchannel.iheartradio.enterprise");
    options.setCapability("startIWDP","true");
    options.setCapability("noReset","true");
    options.setCapability("useNewWDA","false");
    //options.setCapability("showIOSLog","true");
    //options.setCapability("app","/Users/Shruti/Desktop/untitled folder/iHRiOSAppCenter/iHeartRadio.ipa");
   options.setCapability("xcodeOrgId","BCN32U332R");
   options.setCapability("xcodeSigningId","iPhone Developer");
   options.setCapability("autoAcceptAlerts","true");
    options.setCapability("SHOW_XCODE_LOG","true");
   //options.setCapability("updatedWDABundleId","com.Shruti7505.WebDriverAgentRunner");
    driver = new IOSDriver<MobileElement>(service,options);
       return driver;
}
    return driver=null;

    }

}
