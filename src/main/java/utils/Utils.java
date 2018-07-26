package utils;

import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Created by taras on 7/17/18.
 */
public class Utils {

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
        try {
            Files.createDirectories(dirsPath);
            System.out.println("Screenshot folder created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenshot(ITestResult result, String folderName) {
        if(result.getStatus() == ITestResult.FAILURE ||
                result.getStatus() == ITestResult.SKIP) {
            try {
                takeScreenshotWithRobot(result, folderName);
                System.out.println("Screenshot taken");
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot!");
                e.printStackTrace();
            }
        }
    }

    private static void takeScreenshotWithRobot(ITestResult result, String folderName) throws AWTException, IOException {
        BufferedImage image = new Robot()
                .createScreenCapture(new Rectangle(Toolkit
                        .getDefaultToolkit()
                        .getScreenSize()));
        ImageIO.write(image, "png", new File(folderName
                + result.getName()
                + "_"
                + getTime()
                + "_scr.png"));
    }

    private static String hashPassword(String email, String pass, String secret){
        return new String(Base64.getEncoder().encode((email + "%s%" + pass + "%s%" + secret).getBytes()));
    }

    private static String getPassword(String hash){
        return new String(Base64.getDecoder().decode(hash.getBytes())).split("%s%")[1];
    }

    private static String getEmail(String hash){
        return new String(Base64.getDecoder().decode(hash.getBytes())).split("%s%")[0];
    }

    public static String getTestUserEmail(){
        return getEmail(JsonReader.getPropertyFileValue("test_user_credentials"));
    }

    public static String getTestUserPassword(){
        return getPassword(JsonReader.getPropertyFileValue("test_user_credentials"));
    }

}
