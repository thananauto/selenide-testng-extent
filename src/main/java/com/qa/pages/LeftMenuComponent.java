package com.qa.pages;

import com.qa.entity.LeftMenuComponentType;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

public class LeftMenuComponent {

    @Step("Select the menu ")
    public LeftMenuComponent selectLeftMenu(LeftMenuComponentType menuComponentType){
        $(byText(menuComponentType.getMenuName())).should(visible).click();
        return this;
    }

    @SneakyThrows
    public <T> T getInstanceOfPage(Class<T> clazz)  {
        return clazz.getDeclaredConstructor().newInstance();
    }

}
