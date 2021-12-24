package com.bnymellon.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import com.bnymellon.utils.Report;


@CucumberOptions(
        features = {"src/test/resources"},
        glue = {"com/bnymellon/stepdefinition"},
        tags = "@ui",
        plugin = {"pretty", "html:target/html/report", "json:target/json/cucumber.json"},
        publish = true
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterTest
    public void afterAllScenarios() {
        Report.createReport();
    }
}
