package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Comparator;

import static utils.JsonReader.getBookTimePattern;

/**
 * Created by taras on 7/17/18.
 */
public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    public static final String DE = "DE";
    public static final String EN = "EN";
    public static final String DE_MOBILE = "DE_MOBILE";
    public static final String EN_MOBILE = "EN_MOBILE";


    public static void sleep(double seconds){
        try {
            Thread.sleep((int)(seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getScreenshotDateTime(){
        return getDateTime("yyyy_MM_dd_HH_mm_ss");
    }

    public static String getCurrentDateTime(int plusHours){
        return  getDateTime(getBookTimePattern(), plusHours);
    }

    public static String getDateTime(String timePattern){
        return getDateTime(timePattern, 0);
    }

    public static String getDateTime(String timePattern, int plusHours){
        LocalDateTime localTime = LocalDateTime.now();
        localTime = localTime.plusHours(plusHours);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timePattern);
        return localTime.format(formatter);
    }

    public static void createFolder(String folderName) {
        createFolder(folderName, true);
    }

    public static void createFolder(String folderName, boolean deleteBeforeCreate) {
        Path dirsPath = Paths.get(folderName);

        if (deleteBeforeCreate) {
            deleteDirs(dirsPath);
        }
        try {
            Files.createDirectories(dirsPath);
            LOG.info(folderName + " folder created");
        } catch (IOException e) {
            LOG.info("Exception while creating folder: " + folderName);
        }
    }

    private static void deleteDirs(Path path) {
        try {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (NoSuchFileException nsfe) {
            LOG.info("Path << " + path + " >> not found");
        } catch (IOException e) {
            LOG.info("Exception while deleting folder: " + path);
        }
    }

    public static void takeScreenshot(ITestResult result, String folderName, WebDriver d) {
        if(result.getStatus() == ITestResult.FAILURE ||
                result.getStatus() == ITestResult.SKIP) {
            try {
                takeScreenshotWithWebdriver(result, folderName, d);
                LOG.info("Screenshot taken");
            } catch (Exception e) {
                LOG.info("Exception while taking screenshot!");
                e.printStackTrace();
            }
        }
    }

    private static void takeScreenshotWithRobot(ITestResult result, String folderName) throws AWTException, IOException {
        BufferedImage image = new Robot()
                .createScreenCapture(new Rectangle(Toolkit
                        .getDefaultToolkit()
                        .getScreenSize()));
        ImageIO.write(image, "png", getScreenshotName(result, folderName));
    }

    private static void takeScreenshotWithWebdriver(ITestResult result, String folderName, WebDriver d) throws IOException {
        File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, getScreenshotName(result, folderName));
    }

    private static File getScreenshotName(ITestResult result, String folderName) {
        return new File(folderName
                + result.getName()
                + "_"
                + getScreenshotDateTime()
                + "_scr.png");
    }

    public static String hashPassword(String email, String pass, String secret){
        return new String(Base64.getEncoder().encode((email + "%s%" + pass + "%s%" + secret).getBytes()));
    }

    private static String getPassword(String hash){
        return new String(Base64.getDecoder().decode(hash.getBytes())).split("%s%")[1];
    }

    private static String getEmail(String hash){
        return new String(Base64.getDecoder().decode(hash.getBytes())).split("%s%")[0];
    }

    public static String getUserEmail(String credentials){
        return getEmail(credentials);
    }

    public static String getUserPassword(String credentials){
        return getPassword(credentials);
    }

    public static  String getUiTestEnvironment() {
        return getTestEnvironment("ui");
    }

    public static  String getRestApiTestEnvironment() {
        return getTestEnvironment("rest");
    }

    public static  String getTestEnvironment(String testEnv) {
        String env;

        try {
            LOG.info("Set ENV for : " + testEnv + " test");
            env = getEnvFromJenkins(testEnv);

            if (env == null) {
                throw new RuntimeException("ENV is not set by Jenkins, using JSON file");
            }
            LOG.info("ENV is set by Jenkins : " + env);
        } catch (Exception ex){
            env = getEnvFromJsonFile(testEnv);
            LOG.info("ENV is set by Json property file: " + env);
        }
        return env;
    }

    private static String getEnvFromJsonFile(String testEnv) {
        LOG.info("ENV variable is set by JSON file for : " + testEnv);
        if(testEnv.equalsIgnoreCase("ui")) {
            return JsonReader.getString("env").toLowerCase();
        }
        if(testEnv.equalsIgnoreCase("rest")){
            return JsonReader.getString("api_call_url", "artifacts/properties/api_calls.json").toLowerCase();
        }
        return null;
    }

    private static String getEnvFromJenkins(String testEnv) {
        String result = System.getProperty("ENV");

        if(testEnv.equalsIgnoreCase("ui")){
            return result;
        }

        if(testEnv.equalsIgnoreCase("rest")){
            if(result.equals(null)){
                return null;
            }
            return "https://" + result;
        }

        return null;
    }

    private static String getBrowserFromJenkins() {
        return System.getProperty("Browser");
    }

    private static String getMobileDeviceFromJenkins() {
        return System.getProperty("Deivce");
    }

    private static String getBrowserFromJson() {
        return JsonReader.getString("browser");
    }

    public static String getMobileDeviceFromJson(){
        return JsonReader.getString("device");
    }

    public static String getBrowser(){
        if(null != getBrowserFromJenkins()){
            LOG.info("Browser is set by Jenkins: " + getBrowserFromJenkins());
            return getBrowserFromJenkins();
        }

        LOG.info("Browser is set by JSON: " + getBrowserFromJson());
        return getBrowserFromJson();
    }

    public static String getMobileDevice(){
        if(null != getMobileDeviceFromJenkins()){
            LOG.info("Device is set by Jenkins: " + getMobileDeviceFromJenkins());
            return getMobileDeviceFromJenkins();
        }

        LOG.info("Device is set by JSON: " + getMobileDeviceFromJson());
        return getMobileDeviceFromJson();
    }

    public static String getLanguage() {
        if (!getMobileDevice().isEmpty()){
            if(getUiTestEnvironment().toLowerCase().contains("_de")) return DE_MOBILE;
            if(getUiTestEnvironment().toLowerCase().contains("_en")) return EN_MOBILE;
        }

        if(getUiTestEnvironment().toLowerCase().contains("_de")) return DE;
        if(getUiTestEnvironment().toLowerCase().contains("_en")) return EN;

        throw new RuntimeException("Language is not set in property file!!!");
    }

}
