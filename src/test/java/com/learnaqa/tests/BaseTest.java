package com.learnaqa.tests;

import com.learnaqa.driver.Driver;
import com.learnaqa.utils.ScreenshotUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

@Listeners(ScreenshotUtils.class)
public class BaseTest {
    @AfterMethod
    public void tearDown(){
        Driver.quitDriver();
    }
}
