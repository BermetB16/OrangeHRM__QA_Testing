package com.learnaqa.tests;

import com.learnaqa.pages.LeavePage;
import com.learnaqa.pages.LoginPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeaveTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LeaveTest.class);
    private LeavePage leavePage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");
        loginPage.waitForLoginToComplete();
        leavePage = new LeavePage();
        leavePage.clickLeave();
    }

    @Test(groups = {"smoke"},
    description = "Verify Leave header is visible")
    @Description("Leave header has to be visible")
    @Severity(SeverityLevel.NORMAL)
    public void testLeaveHeaderIsVisible(){
        logger.info("Testing Leave header visibility");
        Assert.assertTrue(leavePage.isLeaveHeaderVisible());
        logger.info("Leave header is visible");
    }
}
