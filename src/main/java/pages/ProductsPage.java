package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Pantalla de Products de Swag Labs.
 */
public class ProductsPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // TODO: ajustar localizadores con Appium Inspector
    private final By productsTitle = AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")");
    private final By firstAddToCart = AppiumBy.accessibilityId("test-ADD TO CART");
    private final By cartIcon = AppiumBy.accessibilityId("test-Cart");
    private final By sortButton = AppiumBy.accessibilityId("test-Modal Selector Button");
    private final By sortHighToLow = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Price (high to low)\")");
    private final By productPrices = AppiumBy.accessibilityId("test-Price");

    public ProductsPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isProductsScreenVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle)).isDisplayed();
    }

    public String getProductsTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle)).getText();
    }

    public void addFirstProductToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(firstAddToCart)).click();
        // Tras agregar, el botón pasa a REMOVE
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("test-REMOVE")));
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public void sortByPriceHighToLow() {
        wait.until(ExpectedConditions.elementToBeClickable(sortButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(sortHighToLow)).click();
    }

    public List<WebElement> getPriceElements() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productPrices));
        return driver.findElements(productPrices);
    }

    public double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replace("$", "").trim());
    }
}
