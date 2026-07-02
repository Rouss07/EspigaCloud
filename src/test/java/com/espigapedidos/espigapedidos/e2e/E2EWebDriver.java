package com.espigapedidos.espigapedidos.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

final class E2EWebDriver {

    private E2EWebDriver() {
    }

    static WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1280,800");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl != null && !remoteUrl.isBlank()) {
            try {
                return new RemoteWebDriver(URI.create(remoteUrl).toURL(), options);
            } catch (MalformedURLException e) {
                throw new IllegalStateException("SELENIUM_REMOTE_URL invalida: " + remoteUrl, e);
            }
        }

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    static String appUrl(int port) {
        String baseUrl = System.getenv("E2E_APP_BASE_URL");
        if (baseUrl == null || baseUrl.isBlank()) {
            return "http://localhost:" + port;
        }
        return baseUrl.replace("{port}", String.valueOf(port));
    }
}
