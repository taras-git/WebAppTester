package utils;

public enum Env {
    PROD_EN("https://www.app2drive.com/en/"),
    PROD_DE("https://www.app2drive.com/"),
    WWW3_EN("https://www3.app2drive.com/en/"),
    WWW3_DE("https://www3.app2drive.com/"),
    DOMINO_EN("https://account.app2drive.tech/"),
    DOMINO_DE("https://account.app2drive.tech/"),
    DOMINO_IP_EN("http://10.77.3.32/"),
    DOMINO_IP_DE("http://10.77.3.32/"),
    STG_EN("http://rental.app2drive.tech/");

    private final String url;

    Env(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static String getUrl(String key) {
        return Env.valueOf(key).getUrl();
    }

}
