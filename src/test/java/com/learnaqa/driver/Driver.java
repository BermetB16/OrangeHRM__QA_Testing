package com.learnaqa.driver;

import org.openqa.selenium.WebDriver;

public class Driver {
    // ThreadLocal хранит отдельную копию WebDriver для каждого потока
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(ChromeWebDriver.loadFromDriver());
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}