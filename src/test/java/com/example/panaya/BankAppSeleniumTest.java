package com.example.panaya;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAppSeleniumTest {

    private WebDriver driver;

    // This runs before every single test
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Uncomment the next line if you want the tests to run invisibly in the background
        // options.addArguments("--headless"); 
        
        // Initialize the Chrome browser
        driver = new ChromeDriver(options);
    }

    @Test
    public void testDepositMoney() {
        // 1. Navigate to the local application
        driver.get("http://localhost:8080");

        // 2. Locate the input field and the deposit button using their HTML attributes
        WebElement amountInput = driver.findElement(By.name("amount"));
        WebElement depositButton = driver.findElement(By.cssSelector(".btn-deposit"));

        // 3. Simulate user actions: Type "50.00" and click "Deposit"
        amountInput.sendKeys("50.00");
        depositButton.click();

        // 4. Verify the result by checking the status message on the screen
        WebElement message = driver.findElement(By.className("message"));
        assertTrue(message.getText().contains("Successfully deposited: $50.00"));
    }

    @Test
    public void testWithdrawMoney() {
        // 1. Navigate to the local application
        driver.get("http://localhost:8080");

        // 2. Locate the input field and the withdraw button
        WebElement amountInput = driver.findElement(By.name("amount"));
        WebElement withdrawButton = driver.findElement(By.cssSelector(".btn-withdraw"));

        // 3. Simulate user actions: Type "20.00" and click "Withdraw"
        amountInput.sendKeys("20.00");
        withdrawButton.click();

        // 4. Verify the result
        WebElement message = driver.findElement(By.className("message"));
        assertTrue(message.getText().contains("Successfully withdrew: $20.00"));
    }
    
    @Test
    public void testOverdrawProtection() {
        driver.get("http://localhost:8080");

        WebElement amountInput = driver.findElement(By.name("amount"));
        WebElement withdrawButton = driver.findElement(By.cssSelector(".btn-withdraw"));

        // Attempt to withdraw an absurdly large amount
        amountInput.sendKeys("999999.00");
        withdrawButton.click();

        WebElement message = driver.findElement(By.className("message"));
        assertTrue(message.getText().contains("Insufficient funds!"));
    }

    // This runs after every single test to clean up
    @AfterEach
    public void tearDown() {
        // Close the browser window
        if (driver != null) {
            driver.quit();
        }
    }
}