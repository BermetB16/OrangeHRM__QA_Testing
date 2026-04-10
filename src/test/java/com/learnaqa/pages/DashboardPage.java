package com.learnaqa.pages;

import com.learnaqa.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {
    public DashboardPage() {
        super();
    }

    @FindBy (xpath = "//h6[@class=\"oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module\"]")
    private WebElement dashboardHeader;

    public boolean isDashboardHeaderVisible(){
      return webElementActions.isDisplayed(dashboardHeader);
    }
}
