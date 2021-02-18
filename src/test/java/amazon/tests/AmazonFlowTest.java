package amazon.tests;

import amazon.pages.CartPage;
import amazon.pages.ItemPage;
import amazon.pages.HomePage;
import amazon.pages.ResultsPage;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static amazon.utils.BrowserUtils.*;
import static amazon.utils.Constants.chromeDriverPath;
import static amazon.utils.Constants.jsonPath;
import static jsonUtils.JsonUtils.readJsonDataset;


public class AmazonFlowTest {

    @DataProvider (name = "dataProviderFactory")
    public Object[][] dataProviderFactory (ITestContext context) throws IOException, ParseException {
        JSONObject jsonObject = readJsonDataset(jsonPath);
        return new Object[][]{{ jsonObject.get("url"),
                jsonObject.get("expectedUrl"),
                jsonObject.get("search1"),
                jsonObject.get("item1Quantity"),
                jsonObject.get("search2"),
                jsonObject.get("item2Quantity")}};
    }

    @Test (dataProvider = "dataProviderFactory")
    public void browserTest (String url, String expectedUrl, String search1, String item1Quantity, String search2, String item2Quantity) {
        //1. Go to https://www.amazon.com
        WebDriver driver = openBrowser(url, chromeDriverPath);
        waitForPageToLoad(driver);
        //2. Search for "hats for men"
        search(driver, search1);
        //3. Add first hat to Cart with quantity 2
        double firstSelectedItemPrice = addFirstItem(driver, item1Quantity);
        //4. Open cart and assert total price and quantity are correct
        checkShoppingCart (driver, firstSelectedItemPrice, Integer.parseInt(item1Quantity), 0, Integer.parseInt(item2Quantity));
        //5. Search for "hats for women"
        search(driver, search2);
        //6. Add first hat to Cart with quantity 1
        double secondSelectedItemPrice = addFirstItem(driver, item2Quantity);
        //7. Open cart and assert total price and quantity are correct
        checkShoppingCart(driver, secondSelectedItemPrice, Integer.parseInt(item2Quantity), firstSelectedItemPrice, Integer.parseInt(item1Quantity));
        //8. Change the quantity for item selected at step 3 from 2 to 1 item in Cart
        changeQuantity(driver, 1, 1);
        //9. Assert total price and quantity are changed correctly
        checkShoppingCart(driver, secondSelectedItemPrice, Integer.parseInt(item2Quantity), firstSelectedItemPrice, 1);
        closeBrowser(driver);
    }

    private void search (WebDriver driver, String item) {
        HomePage homePage = new HomePage();
        WebElement searchField = homePage.getSearchField(driver);
        WebElement searchButton = homePage.getSearchButton(driver);
        searchField.sendKeys(item);
        searchButton.click();
        waitForPageToLoad(driver);
    }

    private double addFirstItem (WebDriver driver, String quantity) {
        //Selection of first item on the list
        ResultsPage resultsPage = new ResultsPage();
        List<WebElement> results = resultsPage.getResultItem(driver);
        results.get(0).click();
        waitForPageToLoad(driver);
        //Getting the price for further checks
        ItemPage itemPage = new ItemPage();
        WebElement itemPrice = itemPage.getPrice(driver);
        double price = getPrice(itemPrice);
        //Select 2 items
        if (itemPage.getQuantitySelector(driver) != null) {
            Select quantitySelection = new Select(itemPage.getQuantitySelector(driver));
            quantitySelection.selectByValue(quantity);
        }
        //Adding to cart
        WebElement addToCart = itemPage.getAddToCartButton(driver);
        addToCart.click();
        waitForPageToLoad(driver);
        //Going to cart
        WebElement goToCart = itemPage.getGoToCartButton(driver);
        goToCart.click();
        waitForPageToLoad(driver);
        return price;
    }

    private double getPrice (WebElement itemPrice) {
        String price = itemPrice.getText().replaceAll("[^0-9?!\\.]","");
        return Double.parseDouble(price);
    }

    private void checkShoppingCart (WebDriver driver, double price1, int quantity1, double price2, int quantity2) {
        CartPage cartPage = new CartPage();
        List<WebElement> items = cartPage.getItem(driver);
        List<Double> expectedPrices = Arrays.asList(price1, price2);
        List<Integer> expectedQuantity = Arrays.asList(quantity1, quantity2);
        for (int i = 0; i<items.size(); i++) {
            double actualItemPrice = getPrice(cartPage.getItemPrice(driver, i));
            int actualItemQuantity;
            if (cartPage.getItemQuantity(driver, i).getSize() == null) {
                actualItemQuantity = 1;
            } else {
                Select quantitySelection = new Select(cartPage.getItemQuantity(driver, i));
                actualItemQuantity = Integer.parseInt(quantitySelection.getFirstSelectedOption().getText().replaceAll("[^0-9?!\\.]", ""));
            }
            Assert.assertTrue(actualItemPrice == expectedPrices.get(i),
                    "Actual price = " + actualItemPrice + "; expected price = " + expectedPrices.get(i));
            Assert.assertTrue(actualItemQuantity == expectedQuantity.get(i),
                    "Item " + i + ": actual quantity = " + actualItemQuantity + "; expected quantity = " + expectedQuantity.get(i));
        }
        double totalPrice = round(price1*quantity1 + price2*quantity2, 2);
        double expectedTotalPrice = round(Double.parseDouble(cartPage.getTotalPrice(driver).getText().replaceAll("[^0-9?!\\.]","")), 2);
        Assert.assertTrue(totalPrice == expectedTotalPrice,
                "Actual total price = " + totalPrice + "; expected total price = " + expectedTotalPrice);
    }

    private void changeQuantity (WebDriver driver, int itemIndex, int newQuantity) {
        CartPage cartPage = new CartPage();
        Select quantitySelection = new Select(cartPage.getItemQuantity(driver, itemIndex));
        quantitySelection.selectByValue(String.valueOf(newQuantity));
        waitForPageToLoad(driver);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}