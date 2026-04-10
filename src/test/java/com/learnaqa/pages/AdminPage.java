package com.learnaqa.pages;

import com.learnaqa.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage extends BasePage {
    public AdminPage() {
        super();
    }

    @FindBy(xpath = "//span[text()='Admin']/parent::a")
    public WebElement adminButton;

    @FindBy(xpath = "//h6[contains(@class,'oxd-topbar-header-breadcrumb-level')]")
    public WebElement userManagementHeader;

    public void clickAdmin() {
        webElementActions.click(adminButton);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("admin"));
    }

    public boolean isUserManagementVisible() {
        return webElementActions.isDisplayed(userManagementHeader);
    }
}
