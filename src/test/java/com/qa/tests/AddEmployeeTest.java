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
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.codeborne.selenide.testng.TextReport;

import java.lang.reflect.Method;


public class AddEmployeeTest extends TestSetup {

    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test(testName = "Test the employee", description = "Add the employee and validate")
    @Step("Add the employee and validate whether employer created successfully")
    public void testEmployee(Method method)  {

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
    @Step("validate the employee and the credentials")
    public void testEmployee2(Method method)  {
        //read yml in Hask key

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
    @Step("Validate the invalid credentials of user")
    public void invalidCredentials(){
        LoginPage.getInstance()
                .validateInvalidCredentials("Admin", "Admin1234");
    }
}
