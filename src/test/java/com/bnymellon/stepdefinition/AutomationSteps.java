package com.bnymellon.stepdefinition;

import io.cucumber.java8.En;
import io.cucumber.java8.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDateTime;

public class AutomationSteps implements En {

    private WebDriver driver;
    private Actions actions;
    private String regNum;
    private String make;

    public AutomationSteps() {

        Given("a user opens {string}", (String homepage) -> {
            driver.get(homepage);
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//a[@class='gem-c-button govuk-button govuk-button--start']")).click();
        });

        When("a user enters registration {string}", (String regNum) -> {
            this.regNum = regNum;
            driver.findElement(By.xpath("//input[@class='govuk-input govuk-input--width-10']")).sendKeys(regNum);
            driver.findElement(By.id("submit_vrn_button")).click();
        });

        Then("a page is displayed with vehicle information: {string} and {string}", (String make, String colour) -> {
            this.make = make;

            driver.findElement(By.id("yes-vehicle-confirm")).click();

            String txt_reg = driver.findElement(By.className("reg-mark-sm")).getText().replace(" ", "");
            String txt_make = driver.findElement(By.xpath("//dt[text()='Make']/following-sibling::dd")).getText().replace(" ", "");
            String txt_colour = driver.findElement(By.xpath("//dt[text()='Colour']/following-sibling::dd")).getText();

            //Check car details
            Assert.assertEquals(this.regNum, txt_reg);
            Assert.assertEquals(make, txt_make);
            Assert.assertEquals(colour, txt_colour);

            driver.findElement(By.id("capture_confirm_button")).click();
        });

        And("the vehicle found page is displayed with {string} and {string} information", (String tax, String mot) -> {

            String txt_reg = driver.findElement(By.className("reg-mark")).getText().replace(" ", "");
            String txt_make = driver.findElement(By.xpath("//dt[text()='Vehicle make']/following-sibling::dd")).getText().replace(" ", "");
            String taxInfo = driver.findElement(By.xpath("//div[@class='govuk-grid-column-one-half'][1]//h2")).getText().split(" ")[1].trim();
            String motInfo = driver.findElement(By.xpath("//div[@class='govuk-grid-column-one-half'][2]//h2")).getText().split(" ")[1].trim();

            Assert.assertEquals(this.regNum, txt_reg);
            Assert.assertEquals(this.make, txt_make);
            Assert.assertEquals(tax, taxInfo);
            Assert.assertEquals(mot, motInfo);
        });

        Before(() -> {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            actions = new Actions(driver);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
            driver.manage().window().maximize();
        });

        AfterStep((Scenario scenario) -> {
            final byte[] screenshot = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", LocalDateTime.now().toString());
        });

        After(() -> {
            driver.quit();
        });
    }
}
