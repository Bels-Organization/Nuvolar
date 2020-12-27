package WebUtils;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserUtils {

    public static WebDriver openBrowser (String driver, String webDriverPath, String url) {
        System.setProperty(driver, webDriverPath);
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);
        return webDriver;
    }

    public static void closeBrowser (WebDriver webDriver) {
        try {
            webDriver.close();
        } catch (NoSuchSessionException e) {
            e.printStackTrace();
        }
    }
}
