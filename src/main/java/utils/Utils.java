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

/**
 * Created by taras on 7/17/18.
 */
public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    public static final String DE = "DE";
    public static final String EN = "EN";

    public static void sleep(double seconds){
        try {
            Thread.sleep((int)(seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }

    public static void createFolder(String folderName) {
        Path dirsPath = Paths.get(folderName);

        deleteOldScreenshots(dirsPath);

        try {
            Files.createDirectories(dirsPath);
            LOG.info(folderName + " folder created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteOldScreenshots(Path path) {
        try {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (NoSuchFileException nsfe) {
            LOG.info("Path << " + path + " >> not found");
        } catch (IOException e) {
            e.printStackTrace();
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
                + getTime()
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

    public static  String getEnvironment() {
        String env;
        try {
            LOG.info("Setting ENV variable");
            env = System.getProperty("ENVIRONMENT");

            if (env == null) throw new RuntimeException("ENV variable is not set by Jenkins, using JSON file");
            LOG.info("ENV variable is set by Jenkins choice parameter: " + env);
        } catch (Exception e){
            env = JsonReader.getString("env").toLowerCase();
            LOG.info("ENV variable is set by Json property file: " + env);
        }
        return env;
    }

    public static String getLanguage() {
        if(getEnvironment().toLowerCase().contains("_de")) return DE;
        if(getEnvironment().toLowerCase().contains("_en")) return EN;

        throw new RuntimeException("Language is not set in property file!!!");
    }

}
