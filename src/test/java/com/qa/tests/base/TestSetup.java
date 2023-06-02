package com.qa.tests.base;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLog;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.TextReport;
import com.google.common.io.Files;
import com.qa.utility.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import lombok.SneakyThrows;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigCache;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;

import static com.codeborne.selenide.Selenide.open;

//@Listeners({TextReport.class})
public class TestSetup {

    static ConfigReader configReader = ConfigCache.getOrCreate(ConfigReader.class);

    @BeforeSuite
    public void setupAllureReports() {
       // SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        overrideSelenideConf();

    }

    @BeforeMethod
    @Step("Launch the application")
    public void init(){
        open(configReader.baseUrl());

    }
    @AfterMethod
    public void teardown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            //screenshot();
        }
        Selenide.closeWebDriver();
    }

    @Attachment(type = "image/png")
    @SneakyThrows
    public byte[] screenshot()  {
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

    public void overrideSelenideConf(){
        Configuration.browser = configReader.browser();
        Configuration.timeout = configReader.timeout();
        Configuration.pageLoadTimeout = configReader.pageLoadTimeOut();
        Configuration.reportsFolder = configReader.reportFolder();
        Configuration.downloadsFolder = configReader.downloadsFolder();
        Configuration.savePageSource = configReader.savePageSource();
    }
}
