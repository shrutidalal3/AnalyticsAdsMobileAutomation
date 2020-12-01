package iHRTCommons;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public  class Utilities {

 //static Class MitmproxyJava = Class.forName("MitmproxyJava");


    public Utilities() throws ClassNotFoundException {
    }

    /**
     * This method waits for visibility of element
     * @param driver
     * @param element
     */
    public static void Wait(AppiumDriver driver,WebElement element){

        new WebDriverWait(driver,5).until(ExpectedConditions.visibilityOf(element));


    }
    /**
     * This method waits till element is clickable
     * @param driver
     * @param element
     */
    public static void waitForVisibility(AppiumDriver driver,WebElement element) throws Error{
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

 public static String getAdbLogCat(){
     String log = "";
     String str;
     try
     {
         String myStringArray[]= {"logcat"};
         Process process = Runtime.getRuntime().exec(myStringArray);
         InputStream is = process.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);

         BufferedReader br = new BufferedReader(isr);
         str = br.readLine();
         while (str != null)
         {
             log += str;
             str = br.readLine();
         }
     }
     catch ( IOException e)
     {
     }
     return log;

 }

 public static void clearLog(){
        try{
            Process process = new ProcessBuilder()
                    .command("logcat","-c")
                    .redirectErrorStream(true)
                    .start();

        }
        catch (Exception e){}
 }

    /**
     * This method filters logcats logs for pubads request
     * @param logs logcats logs to filter
     * @return stream of filtered ads logs
     */
 public static Stream<LogEntry> getAdsLogStreams(LogEntries logs){
        return StreamSupport.stream(logs.spliterator(), false)
                .filter((I)->I.getMessage().contains("https://pubads.g.doubleclick.net/gampad/ads"))
                .filter((I)->I.getMessage().contains("Debug CONTENT"))
                .filter((I)->I.getMessage().contains("cust_params"))
                .filter((I)->I.getMessage().contains("ccrpos=8004"));




 }

    /**
     * This method filters log stream
     * @param log logcat logs to filter
     * @param logFilter expected filter to filter logs
     * @return stream of filtered logs
     */
 public static Stream<LogEntry>  getLogStreams(LogEntries log,String logFilter){
     return StreamSupport.stream(log.spliterator(), false)
             .filter((I)->I.getMessage().contains(logFilter));
 }

    public static String getParamValue(String link, String paramName) throws URISyntaxException {
        List<NameValuePair> queryParams= new URIBuilder(link).getQueryParams();
        String filteredValue="";
     try {

        filteredValue= queryParams.stream()
                 .filter(param -> param.getName().equalsIgnoreCase(paramName))
                 .map(NameValuePair::getValue)
                 .findFirst()
                 .orElse("");
     }
     catch (Exception e){
        filteredValue= queryParams.stream()
                 .filter(param -> param.getName().equalsIgnoreCase(paramName))
                 .map(NameValuePair::getValue)
                 .findFirst()
                 .orElse("");
     }
     return filteredValue;
    }

    /**
     * This method works for Android to scroll to the given text on the page
     * @param driverAgent1
     * @param text- expected Text for scrolling to
     */
    public static void scrolltoIntoView(AppiumDriver driverAgent1,String text){
        MobileElement element = (MobileElement) driverAgent1.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"+
                ".scrollIntoView(new UiSelector().text(\""+text+"\"))"));
    }




}
