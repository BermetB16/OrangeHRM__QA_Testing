package com.learnaqa.tests;

import com.learnaqa.driver.Driver;
import com.learnaqa.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        Driver.getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"); // или из конфигурации
        loginPage = new LoginPage();
    }

    // В классе LoginTest
    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() {
        return new Object[][] {
                {"Admin", "admin123", true},   // валидный админ
                {"John", "wrongPass", false},  // невалидный
                {"", "", false}                // пустые поля
        };
    }

    @Test(groups = {"smoke", "positive"},
            description = "Verify valid credentials login successfully")
    @Description("Valid admin credentials should redirect to dashboard")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLogin() {
        logger.info("Starting valid login test");
        loginPage.login("Admin", "admin123");
        loginPage.waitForLoginToComplete();
        logger.debug("Verifying dashboard URL");
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("dashboard"));
        logger.info("Valid login test passed");
    }

    @Test(groups = {"negative", "regression"},
            dataProvider = "invalidCredentials",
            description = "Verify invalid credentials show error message")
    @Description("Invalid credentials should display error message")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidLogin(String username, String password, String expectedError) {
        logger.info("Testing invalid login - username: {}", username);
        loginPage.login(username, password);
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
        logger.info("Invalid login test passed for username: {}", username);
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"Admin", "wrongpassword", "Invalid credentials"},
                {"wronguser", "admin123", "Invalid credentials"}
        };
    }

    @Test(groups = {"negative"},
            description = "Verify empty fields show Required message")
    @Description("Empty fields should show Required validation message")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyFieldsLogin() {
        logger.info("Testing empty fields validation");
        loginPage.login("", "");
        Assert.assertEquals(loginPage.getUsernameError(), "Required");
        Assert.assertEquals(loginPage.getPasswordError(), "Required");
        logger.info("Empty fields test passed");
    }
}