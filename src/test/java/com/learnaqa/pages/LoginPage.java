package com.learnaqa.pages;

import com.learnaqa.driver.Driver;
import com.learnaqa.pages.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage extends BasePage{

    public LoginPage() {
        super();
    }

    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement loginButton;

    @FindBy(css = ".oxd-alert-content-text")
    private WebElement errorMessage;

    @FindBy(css = ".oxd-input-field-error-message")
    private List<WebElement> requiredErrors;

    public String getErrorMessage() {
        try {
            return webElementActions.getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public String getUsernameError() {
        try {
            return webElementActions.getText(requiredErrors.get(0));
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordError() {
        try {
            return webElementActions.getText(requiredErrors.get(1));
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Login with username: {0}")
    public void login(String username, String password){
        webElementActions.sendKeys(usernameField,username)
                        .sendKeys(passwordField,password)
                                .click(loginButton);
    }

    public void waitForLoginToComplete() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("dashboard"));
    }
}
