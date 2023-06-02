package com.qa.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.qa.entity.Employee;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

public class EmployeeInformationPage {

    private static final SelenideElement ADD_EMPLOYEE_BTN = $(byTagAndText("a","Add Employee"));
    private static final SelenideElement FIRST_NAME = $(byName("firstName"));
    private static final SelenideElement MIDDLE_NAME = $(byName("middleName"));
    private static final SelenideElement LAST_NAME = $(byName("lastName"));
    private static final SelenideElement SAVE = $(byTagAndText("button","Save"));
    private static final SelenideElement IMAGE = $(byAttribute("type","file"));
    private static final SelenideElement SUCCESS = $(byText("Success"));


    public EmployeeInformationPage addEmployee(Employee employee){
        ADD_EMPLOYEE_BTN.should(visible).click();
        FIRST_NAME.should(visible).setValue(employee.getFirstName());
        MIDDLE_NAME.should(visible).setValue(employee.getMiddleName());
        LAST_NAME.should(visible).setValue(employee.getLastName());
        IMAGE.should(enabled).uploadFromClasspath("Design-pattern.png");
        SAVE.should(enabled).click();
        SUCCESS.shouldBe(appear);
        return this;
    }

    public void employeeCreatedSuccessfully(){
        SUCCESS.shouldBe(enabled);

    }
}
