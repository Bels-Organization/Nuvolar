package amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ItemPage {
    private By price = By.id("priceblock_ourprice");
    private By quantitySelector = By.id("quantity");
    private By quantityValues = By.className("a-dropdown-link");
    private By addToCartButton = By.id("add-to-cart-button");
    private By goToCartButton = By.id("hlb-view-cart");

    public WebElement getPrice (WebDriver driver) {
        return driver.findElement(price);
    }

    public WebElement getQuantitySelector (WebDriver driver) {
        try {
            return driver.findElement(quantitySelector);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public WebElement getAddToCartButton (WebDriver driver) {
        return driver.findElement(addToCartButton);
    }

    public WebElement getGoToCartButton (WebDriver driver) {
        return driver.findElement(goToCartButton);
    }
}
