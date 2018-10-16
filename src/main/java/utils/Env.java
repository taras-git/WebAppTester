package utils;

public enum Env {
    PROD_EN("https://www.app2drive.com/en/"),
    PROD_DE("https://www.app2drive.com/"),
    WWW3_EN("https://www3.app2drive.com/"),
    WWW3_DE("https://www3.app2drive.com/de/"),
    INTERA_EN("https://app2drive.intera.de/app2drive/de-en/app2drive.nsf#/rental-data"),
    INTERA_DE("https://app2drive.intera.de/app2drive/dev/de/index.nsf/c/fahrzeug_finden_und_buchen#/rental-data");

    private final String url;

    Env(String url) {
        this.url = url;
    }

    public String getUrl() {
        if (null == url){
            throw new RuntimeException("Please check if URL is correctly added to enum Env.java");
        }
        return url;
    }
}
