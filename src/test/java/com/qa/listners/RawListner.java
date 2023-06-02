package com.qa.listners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import com.qa.report.ExtentTestManager;
import com.qa.tests.base.TestSetup;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class RawListner implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
         ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), iTestResult.getMethod().getDescription());
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        OnTestRun(iTestResult);
    }
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        OnTestRun(iTestResult);
    }
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        OnTestRun(iTestResult);
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }
    @Override
    public void onStart(ITestContext iTestContext) {


    }
    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("On Finish"+ iTestContext.getName());
        ExtentTestManager.flush();
    }

    @SneakyThrows
    public void OnTestRun(ITestResult result){
       ExtentTest extentTest = ExtentTestManager.getTest();
        if(result.getStatus()==ITestResult.FAILURE){
            extentTest.assignCategory(result.getInstanceName());
            extentTest.log(Status.FAIL,"Details: "+result.getMethod().getDescription());
            extentTest.log(Status.FAIL, MarkupHelper.createLabel("FAILED test case name is:"+""+result.getName(),ExtentColor.RED));
            Reporter.log("Failed Report"+"",true);
            extentTest.fail(result.getThrowable());
            for(int i=0;i<result.getParameters().length;i++){
                extentTest.log(Status.FAIL,result.getParameters()[i].toString());
            }

            WebDriver driver = WebDriverRunner.getWebDriver();
            //Take base64Screenshot screenshot for extent reports
           String path = getScreenshot(driver, result.getName());
            extentTest.addScreenCaptureFromPath(path);
        }
        else if(result.getStatus()==ITestResult.SUCCESS){
            extentTest.log(Status.PASS,"TestClass:"+result.getClass().getName()+" : "+result.getMethod().getDescription());
        }
        else if(result.getStatus()==ITestResult.SKIP){
            extentTest.log(Status.SKIP,"TestCaseSKIPPEDis"+""+result.getMethod().getDescription());
        }
    }
    @Attachment(type = "image/png")
    @SneakyThrows
    public byte[] screenshot()  {
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

    @SneakyThrows
    public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination =  "screenshot/"+screenshotName+dateName+".png";
        File finalDestination = new File(System.getProperty("user.dir")+"/target/reports/"+destination);
        FileUtils.copyFile(source, finalDestination);
        //Returns the captured file path
        return destination;
    }
}
