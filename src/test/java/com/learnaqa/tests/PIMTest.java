package com.learnaqa.tests;

import com.learnaqa.driver.Driver;
import com.learnaqa.pages.LoginPage;
import com.learnaqa.pages.PIMPage;
import com.learnaqa.utils.RandomEmployeeGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class PIMTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(PIMTest.class);
    private PIMPage pimPage;
    private String createdEmployeeId;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");
        loginPage.waitForLoginToComplete();
        pimPage = new PIMPage();
        pimPage.clickPIM();
    }

    @Test(groups = {"smoke"},
            description = "Verify PIM header is visible")
    @Description("PIM header has to be visible")
    @Severity(SeverityLevel.NORMAL)
    public void testPIMHeaderIsVisible() {
        logger.info("Testing PIM header visibility");
        Assert.assertTrue(pimPage.isPIMHeaderVisible());
        logger.info("PIM header is visible");
    }

    @Test(groups = {"smoke", "regression"}, description = "Verify adding a new employee")
    @Description("Should successfully add a new employee")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddEmployee() {
        String firstName = RandomEmployeeGenerator.randomFirstName();
        String lastName = RandomEmployeeGenerator.randomLastName();
        logger.info("Adding employee: {} {}", firstName, lastName);
        pimPage.addEmployee(firstName, lastName);

        createdEmployeeId = pimPage.getLastCreatedEmployeeId();
        Assert.assertNotNull(createdEmployeeId, "Employee ID not generated");
        // После addEmployee страница уже должна быть на Personal Details
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("viewPersonalDetails"),
                "URL does not contain viewPersonalDetails");
        Assert.assertNotNull(createdEmployeeId, "Employee ID not found in URL");
        logger.info("Employee {} {} added successfully with ID: {}", firstName, lastName, createdEmployeeId);
    }

    @AfterMethod
    public void cleanup() {
        if (createdEmployeeId != null) {
            try {
                pimPage.deleteEmployee(createdEmployeeId);
                logger.info("Cleaned up employee with ID: {}", createdEmployeeId);
                createdEmployeeId = null;
            } catch (Exception e) {
                logger.error("Failed to delete employee {}: {}", createdEmployeeId, e.getMessage());
                createdEmployeeId = null;
            }
        }
    }
}