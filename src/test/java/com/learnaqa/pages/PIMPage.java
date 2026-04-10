package com.learnaqa.pages;

import com.learnaqa.pages.base.BasePage;
import com.learnaqa.utils.RandomEmployeeGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PIMPage extends BasePage {

    private String lastCreatedEmployeeId;

    public PIMPage() {
        super();
    }

    @FindBy(xpath = "//span[text()='PIM']/parent::a")
    private WebElement pimButton;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addEmployeeButton;

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//h6[contains(@class,'oxd-topbar-header-breadcrumb-module')]")
    private WebElement pimHeader;

    @FindBy(xpath = "//label[text()='Employee Id']/following::input[1]")
    private WebElement searchEmployeeIdField;

    @FindBy(xpath = "//button[contains(@class,'oxd-button--secondary') and normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//i[contains(@class,'bi-trash')]/parent::button")
    private WebElement deleteIconButton;

    @FindBy(xpath = "//button[normalize-space()='Yes, Delete']")
    private WebElement confirmDeleteButton;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast')]")
    private WebElement toastNotification;

    @FindBy(xpath = "(//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-row')]//button[contains(@class,'oxd-icon-button')])[2]")
    private WebElement deleteButtonForFirstRow;

    @FindBy(xpath = "//label[text()='Employee Id']/following::input[1]")
    private WebElement employeeIdField;

    public void clickPIM() {
        webElementActions.click(pimButton);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("pim"));
    }

    public void addEmployee(String firstName, String lastName) {
        webElementActions.click(addEmployeeButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-form-loader")));

        lastCreatedEmployeeId = RandomEmployeeGenerator.randomEmployeeId();
        webElementActions.sendKeys(employeeIdField, lastCreatedEmployeeId);

        webElementActions.sendKeys(firstNameField, firstName);
        webElementActions.sendKeys(lastNameField, lastName);

        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        webElementActions.click(saveButton);
        // После клика на Save
        try {
            WebElement errorToast = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'oxd-toast') and contains(.,'already exists')]")));
            if (errorToast.isDisplayed()) {
                throw new RuntimeException("Failed to add employee: " + errorToast.getText());
            }
        } catch (TimeoutException e) {
            // Ошибки нет – продолжаем
        }

        // Ожидаем либо изменения URL, либо появления элемента Personal Details
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("viewPersonalDetails"),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Personal Details']"))
        ));
    }
    public String getLastCreatedEmployeeId() {
        return lastCreatedEmployeeId;
    }

    public boolean isPIMHeaderVisible() {
        return webElementActions.isDisplayed(pimHeader);
    }

    public String getEmployeeIdFromUrl() {
        String url = driver.getCurrentUrl();
        if (url.contains("empNumber/")) {
            return url.split("empNumber/")[1];
        }
        return null;
    }

    public void deleteEmployee(String employeeId) {
        // 1. Убедимся, что мы на странице списка сотрудников
        if (!driver.getCurrentUrl().contains("viewEmployeeList")) {
            webElementActions.click(pimButton);
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains("viewEmployeeList"));
        }

        // 2. Ищем сотрудника по ID
        webElementActions.sendKeys(searchEmployeeIdField, employeeId);
        webElementActions.click(searchButton);

        // 3. Ждём, пока таблица обновится
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".oxd-table-body")));

        // 4. Находим кнопку удаления для этого сотрудника
        WebElement deleteButton = getDeleteButtonForEmployee(employeeId);
        webElementActions.click(deleteButton);

        // 5. Подтверждаем удаление
        webElementActions.click(confirmDeleteButton);

        // 6. Ожидаем появления и исчезновения тоста (опционально)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(toastNotification));
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOf(toastNotification));
        } catch (TimeoutException e) {
            logger.warn("Toast notification not shown after deletion");
        }

        // 7. Убеждаемся, что сотрудник исчез из таблицы
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(String.format("//div[@class='oxd-table-body']//div[text()='%s']", employeeId))));
    }
    private WebElement getDeleteButtonForEmployee(String employeeId) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".oxd-table-body")));
        return driver.findElement(By.xpath(
                String.format("//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-row')][contains(.,'%s')]//i[contains(@class,'bi-trash')]/parent::button", employeeId)
        ));
    }
}