package com.qa.tests.base;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Stream;

public class TestdataProvider {

    @DataProvider(name = "check", parallel = false)
    public Object[][] dataProvider(){
        Map<String, Object> address = new HashMap<>();
        address.put("12213", "23weq");
        address.put("pwasdd", "23e");

        Map<String, Object> map = new HashMap<>();
        map.put("user", "admin");
        map.put("pwd", "password");
        map.put("address", address);
       // Object[][] array = new Object[][]{map.keySet().toArray(), map.entrySet().toArray()};

        return new Object[][]{
                new Object[]{map}
        };


    }

    @DataProvider(name = "check1", parallel = false)
    public Iterator<Object> test1(){
        Map<String, Object> address = new HashMap<>();
        address.put("12213", "23weq");
        address.put("pwasdd", "23e");

        Map<String, Object> map = new HashMap<>();
        map.put("user", "admin");
        map.put("pwd", "password");
        map.put("address", address);
        // Object[][] array = new Object[][]{map.keySet().toArray(), map.entrySet().toArray()};
        Set<Map.Entry<String, Object> > entrySet
                = map.entrySet();

        // Creating an ArrayList of Entry objects
      return (Iterator<Object>) new ArrayList<Map.Entry<String, Object>>(entrySet);




    }
    @DataProvider (name = "data-provider")
    public Object[][] dpMethod(){
        return new Object[][] {{"First-Value", "teo"}, {"Second-Value", "seo"}};
    }
    @Test(dataProvider = "check1")
    public void test(Map<String, Object> map){
        System.out.println(map);
    }
}
