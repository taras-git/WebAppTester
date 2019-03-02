package utils;

public enum Env {
    PROD_EN("https://www.google.com/"),
    PROD_DE("https://www.google.com/"),

    STG_DE("https://www.google.com/"),
    STG_EN("https://www.google.com/");

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
