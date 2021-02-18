package amazon.utils;

import junit.framework.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {

    /**
     * Opens a browser and navigates to the given url
     * @param url utl we want to open when launching the browser
     */
    public static WebDriver openBrowser(String url, String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);
        return webDriver;
    }

    /**
     * Closes an open browser
     * @param webDriver webDriver controlling the browser to be closed
     */
    public static void closeBrowser(WebDriver webDriver) {
        try {
            webDriver.close();
        } catch (NoSuchSessionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits until the browser has loaded the page
     */
    public static void waitForPageToLoad(WebDriver webDriver) {
        ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(webDriver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
}
