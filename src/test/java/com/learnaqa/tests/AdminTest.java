package com.learnaqa.tests;

import com.learnaqa.pages.AdminPage;
import com.learnaqa.pages.LoginPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;

public class AdminTest extends BaseTest{

    private static final Logger logger = LogManager.getLogger(AdminTest.class);
    AdminPage adminPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage(){
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");
        loginPage.waitForLoginToComplete();
        adminPage = new AdminPage();
        adminPage.clickAdmin();
    }

    @Test(groups = {"smoke"},
    description = "Verify header Managenment is visible")
    @Description("Management has to be visible")
    @Severity(SeverityLevel.NORMAL)
    public void testsManagementIsVisible() {
        logger.info("checking the header Management's visibility");
        Assert.assertTrue(adminPage.isUserManagementVisible());
        logger.info("Header Management is visible");

    }
}
