package com.qa.tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.google.common.io.Files;
import com.qa.utility.ConfigReader;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigCache;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;

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
    @Step("closing down the browser")
    public void teardown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            //screenshot();
        }
        Selenide.closeWebDriver();
    }


    public void overrideSelenideConf(){
        Configuration.browser = configReader.browser();
        Configuration.timeout = configReader.timeout();
        Configuration.pageLoadTimeout = configReader.pageLoadTimeOut();
        Configuration.reportsFolder = configReader.reportFolder();
        Configuration.downloadsFolder = configReader.downloadsFolder();
        Configuration.savePageSource = configReader.savePageSource();
        Configuration.screenshots = configReader.takeScreenShots();
        Configuration.remote = configReader.remoteUrl();
    }
}
