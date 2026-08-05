package tests;

import config.BaseTest;
import config.Step;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

/**
 * Script #3: Orden por precio (High to Low).
 */
public class ProductsTest extends BaseTest {

    private static final String APP_PACKAGE = "com.swaglabsmobileapp";

    @BeforeMethod(alwaysRun = true)
    public void resetApp() {
        Step.info("Reiniciar app hacia pantalla de login");
        driver.terminateApp(APP_PACKAGE);
        driver.activateApp(APP_PACKAGE);
        Step.ok("App reiniciada");
    }

    @Test(description = "Ordenar por Price (High to Low) deja el primer precio mayor que el último")
    public void sortByPriceHighToLow_firstPriceGreaterThanLast() {
        Step.info("Iniciar Script #3: Orden por precio (High to Low)");

        LoginPage loginPage = new LoginPage(driver, wait);
        Step.info("Login con standard_user / secret_sauce");
        loginPage.login("standard_user", "secret_sauce");
        Step.ok("Login enviado");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        Step.info("Verificar pantalla de productos");
        Assert.assertTrue(productsPage.isProductsScreenVisible());
        Step.ok("Verificación: pantalla de productos visible");

        Step.info("Abrir dropdown y seleccionar Price (high to low)");
        productsPage.sortByPriceHighToLow();
        Step.ok("Orden High to Low aplicado");

        Step.info("Leer precios visibles en pantalla");
        List<WebElement> prices = productsPage.getPriceElements();
        Assert.assertTrue(prices.size() >= 2, "Se necesitan al menos 2 precios para comparar");
        Step.ok("Verificación: hay al menos 2 precios (" + prices.size() + ")");

        double firstPrice = productsPage.parsePrice(prices.get(0).getText());
        double lastPrice = productsPage.parsePrice(prices.get(prices.size() - 1).getText());
        Step.info("primerPrecio=" + firstPrice + " | ultimoPrecio=" + lastPrice);

        Assert.assertTrue(
                firstPrice > lastPrice,
                "Se esperaba primerPrecio > ultimoPrecio. Obtenido: " + firstPrice + " vs " + lastPrice);
        Step.ok("Verificación: primerPrecio > ultimoPrecio");
    }
}
