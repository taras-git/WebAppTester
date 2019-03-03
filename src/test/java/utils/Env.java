package utils;

public enum Env {
    PROD_EN("https://www.funda.nl/"),
    PROD_DE("https://www.funda.nl/"),

    STG_DE("https://www.funda.nl/"),
    STG_EN("https://www.funda.nl/");

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
