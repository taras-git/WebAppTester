package utils;

/**
 * Created by taras on 7/17/18.
 */
public class Sleeps {

    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
