package tests;

import config.BaseTest;
import config.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;


public class PurchaseFlowTest extends BaseTest {

    @Test(description = "Flujo E2E: login → carrito → checkout → finish")
    public void completePurchaseFlow() {
        Step.info("Iniciar Script #4: Flujo completo E2E");

        LoginPage loginPage = new LoginPage(driver, wait);
        Step.info("Login con standard_user / secret_sauce");
        loginPage.login("standard_user", "secret_sauce");
        Step.ok("Login enviado");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        Step.info("Verificar pantalla de productos");
        Assert.assertTrue(productsPage.isProductsScreenVisible());
        Step.ok("Verificación: pantalla de productos visible");

        Step.info("Agregar primer producto al carrito");
        productsPage.addFirstProductToCart();
        Step.ok("Producto agregado");

        Step.info("Abrir carrito");
        productsPage.openCart();
        Step.ok("Carrito abierto");

        CartPage cartPage = new CartPage(driver, wait);
        Step.info("Verificar producto en el carrito");
        Assert.assertTrue(cartPage.isProductInCart());
        Step.ok("Verificación: producto en el carrito");

        Step.info("Tocar CHECKOUT");
        cartPage.proceedToCheckout();
        Step.ok("Checkout iniciado");

        CheckoutPage checkoutPage = new CheckoutPage(driver, wait);
        Step.info("Completar formulario: Test / User / 12345 y CONTINUE");
        checkoutPage.fillCheckoutForm("Test", "User", "12345");
        Step.ok("Formulario enviado");

        Step.info("Verificar total en overview");
        String total = checkoutPage.getTotalText();
        Step.info("Total mostrado: \"" + total + "\"");
        Assert.assertFalse(total == null || total.isBlank(), "El total no debería estar vacío");
        Step.ok("Verificación: total presente");

        Step.info("Tocar FINISH");
        checkoutPage.finishOrder();
        Step.ok("Orden finalizada");

        Step.info("Verificar mensaje de agradecimiento");
        String thankYou = checkoutPage.getThankYouMessage();
        Step.info("Mensaje mostrado: \"" + thankYou + "\"");
        Assert.assertTrue(
                thankYou.toUpperCase().contains("THANK YOU"),
                "Se esperaba mensaje de agradecimiento. Obtenido: " + thankYou);
        Step.ok("Verificación: mensaje THANK YOU visible");
    }
}
