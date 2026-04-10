package com.learnaqa.pages.base;

import com.learnaqa.driver.Driver;
import com.learnaqa.helper.BrowserManager;
import com.learnaqa.helper.WebElementActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    protected Actions actions;
    protected WebElementActions webElementActions;
    protected BrowserManager browserManager;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
        this.webElementActions = new WebElementActions(driver, actions);
        this.browserManager = new BrowserManager(driver);
    }
}
