package iHRTCommons;

import groovyjarjarantlr4.v4.runtime.misc.ObjectEqualityComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class propertyManager {

    private static propertyManager instance;
    private static final Object lock = new Object();
    private static String propertyFilePath = System.getProperty("user.dir") + "/src/global.properties";

    private static String Androidapp;
    private static String devicename;
    private static String deviceUDID;
    private static String platformName ;
    private static String platformVersion;
    private static String usernamelogin;
    private static String passwordlogin;
    private static String iOSdevicename;
    private static String iOSUDID;
    private static String iOSplatformVersion;


    public static propertyManager getInstance(){
        if(instance  == null){
            synchronized (lock){
                instance = new propertyManager();
                instance.loadData();
            }
        }
        return instance;
    }

    private void loadData(){
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream(propertyFilePath));
        }
        catch(Exception e){
            System.out.println("Configuration properties file cannot be found");
        }
        Androidapp = prop.getProperty("Androidapp");
        devicename = prop.getProperty("devicename");
        deviceUDID = prop.getProperty("deviceUDID");
        platformName = prop.getProperty("platformName");
        platformVersion = prop.getProperty("platformVersion");
        usernamelogin = prop.getProperty("usernamelogin");
        passwordlogin=prop.getProperty("passwordlogin");
        iOSdevicename=prop.getProperty("iOSdevicename");
        iOSUDID = prop.getProperty("iOSUDID");
        iOSplatformVersion = prop.getProperty("iOSplatformVersion");
    }

    public String getAndroidapp () {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot,"/src/main/java/");
        File app = new File(appDir,Androidapp);
        return app.getAbsolutePath();
    }

    public String getdevicename(){
        return devicename;
    }

    public String getdeviceUDID(){
        return deviceUDID;
    }

    public String getplatformName(){
        return platformName;
    }

    public String getplatformVersion(){
        return platformVersion;
    }

    public String getUsernamelogin(){ return usernamelogin;}

    public String getPasswordlogin(){return passwordlogin;}

    public String getiOSdevicename(){return iOSdevicename;}

    public String getiOSUDID(){return iOSUDID;}

    public String getiOSplatformVersion(){return iOSplatformVersion;}

}
