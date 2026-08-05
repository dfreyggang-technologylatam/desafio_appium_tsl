package tests;

import config.BaseTest;
import config.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

/**
 * Script #1: Login exitoso + agregar producto al carrito.
 * Script #2: Login inválido (locked_out_user).
 */
public class LoginTest extends BaseTest {

    private static final String APP_PACKAGE = "com.swaglabsmobileapp";

    @BeforeMethod(alwaysRun = true)
    public void resetToLoginScreen() {
        Step.info("Reiniciar app hacia pantalla de login");
        driver.terminateApp(APP_PACKAGE);
        driver.activateApp(APP_PACKAGE);
        Step.ok("App reiniciada");
    }

    @Test(description = "Login inválido con locked_out_user muestra mensaje de bloqueo")
    public void invalidLogin_lockedOutUser_showsErrorMessage() {
        Step.info("Iniciar Script #2: Login inválido (locked_out_user)");

        LoginPage loginPage = new LoginPage(driver, wait);
        Step.info("Ingresar usuario locked_out_user / secret_sauce y tocar LOGIN");
        loginPage.login("locked_out_user", "secret_sauce");
        Step.ok("Credenciales enviadas");

        Step.info("Obtener mensaje de error en pantalla");
        String error = loginPage.getErrorMessage();
        Step.info("Mensaje mostrado: \"" + error + "\"");

        Assert.assertTrue(
                error.contains("Sorry, this user has been locked out."),
                "Se esperaba mensaje de usuario bloqueado. Obtenido: " + error);
        Step.ok("Verificación: mensaje de usuario bloqueado correcto");
    }

    @Test(description = "Login exitoso y agregar el primer producto al carrito")
    public void loginAndAddProductToCart() {
        Step.info("Iniciar Script #1: Login exitoso + agregar producto al carrito");

        LoginPage loginPage = new LoginPage(driver, wait);
        Step.info("Ingresar standard_user / secret_sauce y tocar LOGIN");
        loginPage.login("standard_user", "secret_sauce");
        Step.ok("Login enviado");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        Step.info("Verificar pantalla de productos visible");
        Assert.assertTrue(productsPage.isProductsScreenVisible(), "La pantalla PRODUCTS no es visible");
        Step.ok("Verificación: pantalla de productos visible");

        Step.info("Verificar título PRODUCTS");
        Assert.assertEquals(productsPage.getProductsTitle(), "PRODUCTS");
        Step.ok("Verificación: título es \"PRODUCTS\"");

        Step.info("Agregar el primer producto (ADD TO CART)");
        productsPage.addFirstProductToCart();
        Step.ok("Producto agregado");

        Step.info("Tocar ícono del carrito");
        productsPage.openCart();
        Step.ok("Carrito abierto");

        CartPage cartPage = new CartPage(driver, wait);
        Step.info("Verificar que el producto aparece en el carrito");
        Assert.assertTrue(cartPage.isProductInCart(), "El producto no aparece en el carrito");
        Step.ok("Verificación: producto aparece en el carrito");
    }
}
