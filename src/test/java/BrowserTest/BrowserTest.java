package BrowserTest;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static browserBaseTest.BrowserBaseTest.closeBrowser;
import static browserBaseTest.BrowserBaseTest.openBrowser;
import static jsonUtils.JsonUtils.readJsonDataset;


public class BrowserTest {

    @DataProvider (name = "dataProviderFactory")
    public Object[][] dataProviderFactory (ITestContext context) throws IOException, ParseException {
        JSONObject jsonObject = readJsonDataset("src/test/resources/BrowserTest/BrowserTest.json");
        return new Object[][]{{ jsonObject.get("url"), jsonObject.get("expectedUrl"), jsonObject.get("chromeDriverPath")}};
    }

    @Test (dataProvider = "dataProviderFactory")
    public void browserTest (String url, String expectedUrl, String chromeDriverPath) {
        WebDriver webDriver = openBrowser(url, chromeDriverPath);
        Assert.assertEquals(webDriver.getCurrentUrl(), expectedUrl);
        closeBrowser(webDriver);
    }
}