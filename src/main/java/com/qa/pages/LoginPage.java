package com.qa.pages;

import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.Click;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private LoginPage(){}

    public static LoginPage getInstance(){
        return new LoginPage();
    }

    private final SelenideElement USERNAME= $(byName("username"));
    private final SelenideElement PASSWORD= $(byAttribute("placeholder","Password"));
    private final SelenideElement LOGIN= $(byAttribute("type", "submit"));
    private final SelenideElement INVALID_CREDENTIAL = $(".oxd-alert-content--error");

    @Step("Entering the credentials")
    public HomePage logIntoApplication(String username, String password){
        USERNAME.should(enabled).setValue(username);
        PASSWORD.should(visible).setValue(password);
        LOGIN.should(enabled).click(ClickOptions.usingJavaScript());
         return new HomePage();
    }

    @Step("Validate invalid credentials username: {0} and password: {1}")
    public void validateInvalidCredentials(String username, String password){
        logIntoApplication(username, password);
        INVALID_CREDENTIAL.shouldBe(visible).shouldHave(text("Invalid credentials"));

    }

    @SneakyThrows
    public <T> T getInstanceOfPage(Class<T> clazz)  {
        return clazz.getDeclaredConstructor().newInstance();
    }

}
