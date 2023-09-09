package com.qa.listners;

import com.qa.utility.InfluxDBManager;
import org.influxdb.dto.Point;
import org.testng.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InfluxDBSuiteListner implements ISuiteListener {
     public void onStart(ISuite suite) {
    }

     public void onFinish(ISuite suite) {
         Map<String, ISuiteResult> resultMap = suite.getResults();
         ISuiteResult result = resultMap.entrySet().iterator().next().getValue();
         ITestContext testContext = result.getTestContext();
          String suiteName =  testContext.getCurrentXmlTest().getSuite().getName();
         IResultMap failedTests = testContext.getFailedTests();
         IResultMap passedTests = testContext.getPassedTests();
         IResultMap skippedTests = testContext.getSkippedTests();

         postOverallResult(suiteName, passedTests.size(), failedTests.size(), skippedTests.size());
         Map<String, Integer> exception = new HashMap<>();
         for(ITestResult testResult :failedTests.getAllResults()){
                String expName = testResult.getThrowable().getClass().getSimpleName();
             if(exception.containsKey(expName)){
                 exception.put(expName, exception.get(expName)+1);
             }else{
                 exception.put(expName, 0);
             }
         }
        //post the exception details in Graffana
         postExceptionResult(exception);
     }
     private void postExceptionResult(Map<String, Integer> map){
         Point point = Point.measurement("Exception").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                 .tag(map
                         .entrySet()
                         .stream()
                         .map(e->e)
                         .collect(Collectors.toMap(Map.Entry::getKey, e->String.valueOf(e.getValue()))))
                 .addField("Total", String.valueOf((map.size()))).build();

         InfluxDBManager.post(point);
     }
    private void postOverallResult(String suiteName, int pass, int fail, int skip) {
        Point point = Point.measurement(suiteName).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("pass", String.valueOf(pass))
                .tag("fail", String.valueOf(fail))
                .tag("skip", String.valueOf(skip))
                .addField("Total", String.valueOf((pass+fail+skip))).build();
                //.addField("duration", (iTestResult.getEndMillis() - iTestResult.getStartMillis())).build();
        InfluxDBManager.post(point);
    }
    }

