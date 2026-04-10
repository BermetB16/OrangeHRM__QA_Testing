package com.learnaqa.utils;

import com.learnaqa.driver.Driver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

            // 1. Сохраняем на диск (как раньше)
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String testName = result.getName();
            String fileName = testName + "_" + timestamp + ".png";

            Path dir = Paths.get("screenshots");
            Files.createDirectories(dir);
            Files.write(dir.resolve(fileName), screenshot);
            System.out.println("Screenshot saved: screenshots/" + fileName);

            // 2. Добавляем в Allure отчёт
            Allure.addAttachment("Screenshot on failure: " + testName,
                    new ByteArrayInputStream(screenshot));

        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}