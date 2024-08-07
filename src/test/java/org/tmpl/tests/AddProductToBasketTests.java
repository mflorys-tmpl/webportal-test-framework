package org.tmpl.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.tmpl.pages.CartPage;
import org.tmpl.pages.HomePage;
import org.tmpl.pages.ProductPage;
import org.tmpl.pages.SmartphoneListPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddProductToBasketTests {
    private WebDriver driver;
    private HomePage homePage;
    private SmartphoneListPage smartphoneListPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        homePage = new HomePage(driver);
        smartphoneListPage = new SmartphoneListPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void shouldBeVisibleBadgeOnCartIconAfterAddingProductToCart() {
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isOnHomePage(driver), "Homepage title missmatch!");

        homePage.clickAcceptCookiesButton();
        homePage.hoverMouseOverDevicesMenu();
        assertTrue(homePage.isMenuExpanded(), "Menu was not expanded on mouse-over action!");

        homePage.clickNoSubscribtionSmartphones();
        assertTrue(smartphoneListPage.isTheListDisplayed(), "No results on the results list were displayed!");

        smartphoneListPage.clickFirstProduct();
        String expectedPriceOnStart = productPage.getPriceOnStart();
        String expectedMonthlyPrice = productPage.getMonthlyPrice();

        productPage.addToCart();
        String cartPriceOnStart = cartPage.getCartPriceOnStart();
        String cartMonthlyPrice = cartPage.getCartMonthlyPrice();

        assertEquals(unifyPriceString(expectedPriceOnStart), unifyPriceString(cartPriceOnStart), "Prices on start on the product details screen and basket screen differ!");
        assertEquals(unifyPriceString(expectedMonthlyPrice), unifyPriceString(cartMonthlyPrice), "Monthly prices on the product details screen and basket screen differ!");

        homePage.openHomePage();
        assertTrue(homePage.isOnHomePage(driver), "There was a problem navigating to the home page!");
        assertTrue(homePage.isBasketIconBadgeVisible(), "Badge on the basket icon was not displayed or the badge doesn't show a positive number!");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    private String unifyPriceString(String rawPrice) {
        if (rawPrice.contains("zł")) {
            rawPrice = rawPrice.substring(0, rawPrice.indexOf("zł")).trim();
        }
        return rawPrice.replace(" ", "");
    }
}
