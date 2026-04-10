package com.learnaqa.tests;

import com.learnaqa.driver.Driver;
import com.learnaqa.pages.DashboardPage;
import com.learnaqa.pages.LoginPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DashBoardTest extends BaseTest{

    private static final Logger logger = LogManager.getLogger(DashBoardTest.class);
    private DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage(){
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");
        loginPage.waitForLoginToComplete();
        dashboardPage = new DashboardPage();
    }

    @Test(groups = {"smoke"},
    description = "Verify valid dashboard title")
    @Description("has to be correct title")
    @Severity(SeverityLevel.NORMAL)
    public void testDashboardTitleIsCorrect(){
        logger.info("checking valid title");
        Assert.assertEquals(Driver.getDriver().getTitle(), "OrangeHRM");
        logger.info("The title is valid");
    }

    @Test(groups = {"smoke", "regression"},
    description = "Visible header")
    @Description("Header should be visible")
    public void testDashboardHeaderIsVisible(){
        logger.info("Testing the visibility of header");
        Assert.assertTrue(dashboardPage.isDashboardHeaderVisible());
        logger.info("The header is visible");
    }
}
