package com.bnymellon.runner;

import com.bnymellon.utils.Helper;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;


@CucumberOptions(
        features = {"src/test/resources"},
        glue = {"com.bnymellon.stepdefinition"},
        tags = "@Ui",
        plugin = {"pretty", "html:target/html/report", "json:target/json/cucumber.json"},
        publish = true
)
public class RunnerTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterTest
    public void afterAllScenarios() {
        Helper.createReport();
    }

    @BeforeTest
    public void beforeAllScenarios() {
        WebDriverManager.chromedriver().setup();
    }

}
