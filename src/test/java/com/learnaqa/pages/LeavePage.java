package com.learnaqa.pages;

import com.learnaqa.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LeavePage extends BasePage {
    public LeavePage() {
        super();
    }

    @FindBy(xpath = "//span[text()='Leave']/parent::a")
    public WebElement leaveButton;

    @FindBy(xpath = "//h6[contains(@class,'oxd-topbar-header-breadcrumb-module')]")
    public WebElement leaveHeader;

    public void clickLeave(){
        webElementActions.click(leaveButton);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("leave"));
    }

    public  boolean isLeaveHeaderVisible(){
        return webElementActions.isDisplayed(leaveHeader);
    }
}
