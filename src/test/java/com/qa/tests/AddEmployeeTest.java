package com.qa.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.qa.entity.Employee;
import com.qa.entity.LeftMenuComponentType;
import com.qa.entity.LoginDetails;
import com.qa.pages.EmployeeInformationPage;
import com.qa.pages.LoginPage;
import com.qa.testdata.TestData;
import com.qa.tests.base.TestSetup;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.codeborne.selenide.testng.TextReport;


public class AddEmployeeTest extends TestSetup {

    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test(testName = "Test the employee", description = "Add the employee and validate")
    public void testEmployee()  {
        Employee employee = TestData.getEmployeeData();
        LoginDetails loginDetails = TestData.getLoginData();

        LoginPage.getInstance()
                .logIntoApplication(loginDetails.getUserName(), loginDetails.getPassWord())
                .getLeftMenuComponent()
                .selectLeftMenu(LeftMenuComponentType.PIM)
                .getInstanceOfPage(EmployeeInformationPage.class)
                .addEmployee(employee)
                .employeeCreatedSuccessfully();
    }

    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test(testName = "Test the employee2", description = "Verify the employee and validate")
    public void testEmployee2()  {
        Employee employee = TestData.getEmployeeData();
        LoginDetails loginDetails = TestData.getLoginData();

        LoginPage.getInstance()
                .logIntoApplication("Admin", "Password")
                .getLeftMenuComponent()
                .selectLeftMenu(LeftMenuComponentType.PIM)
                .getInstanceOfPage(EmployeeInformationPage.class)
                .addEmployee(employee)
                .employeeCreatedSuccessfully();
    }

    @Story("Check the invalid credentials")
    @Severity(SeverityLevel.MINOR)
    @Test(testName = "Validate the invalid", description = "Verify invalid credentials scenario")
    public void invalidCredentials(){
        LoginPage.getInstance()
                .validateInvalidCredentials("Admin", "admin1234");
    }
}
