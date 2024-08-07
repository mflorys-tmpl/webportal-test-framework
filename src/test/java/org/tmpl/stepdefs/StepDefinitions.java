package org.tmpl.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmpl.pages.CartPage;
import org.tmpl.pages.HomePage;
import org.tmpl.pages.ProductPage;
import org.tmpl.pages.SmartphoneListPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    private WebDriver driver;
    private HomePage homePage;
    private SmartphoneListPage smartphoneListPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private static final Logger logger = LoggerFactory.getLogger(StepDefinitions.class);

    @Before
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

        logger.info("Test setup complete.");
    }

    @Given("I am on the T-Mobile homepage")
    public void iAmOnTheTMobileHomepage() {
        homePage.openHomePage();
        Assertions.assertTrue(homePage.isOnHomePage(driver), "Homepage title missmatch!");
        logger.info("Navigated to the homepage.");
    }

    @When("I accept the cookies policy")
    public void iAcceptTheCookiesPolicy() {
        homePage.clickAcceptCookiesButton();
        logger.info("Accepted cookies policy.");
    }

    @When("I navigate to the devices section")
    public void iNavigateToTheDevicesSection() {
        homePage.hoverMouseOverDevicesMenu();
        assertTrue(homePage.isMenuExpanded(), "Menu was not expanded on mouse-over action!");
        logger.info("Mouse over Devices menu.");
    }

    @When("I select {string}")
    public void iSelect(String option) {
        if (option.equals("Bez abonamentu")) {
            homePage.clickNoSubscribtionSmartphones();
        }
        logger.info("Clicked on '{}'.", option);
    }

    @When("I click on the first product in the list")
    public void iClickOnTheFirstProductInTheList() {
        smartphoneListPage.clickFirstProduct();
        logger.info("Clicked on the first product in the list.");
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {
        String expectedPriceOnStart = productPage.getPriceOnStart();
        String expectedMonthlyPrice = productPage.getMonthlyPrice();
        productPage.addToCart();
        assertTrue(currentPageContains("\"heading\":\"Podsumowanie\""), "User was not navigated to the cart summary!");
        logger.info("Added product to cart.");
        assertEquals(unifyPriceString(expectedPriceOnStart), unifyPriceString(cartPage.getCartPriceOnStart()), "Prices on start on the product details screen and basket screen differ!");
        assertEquals(unifyPriceString(expectedMonthlyPrice), unifyPriceString(cartPage.getCartMonthlyPrice()), "Monthly prices on the product details screen and basket screen differ!");
    }

    @Then("I should see the product in the cart with correct prices")
    public void iShouldSeeTheProductInTheCartWithCorrectPrices() {
        homePage.openHomePage();
        assertTrue(homePage.isOnHomePage(driver));
        logger.info("Navigated back to the homepage.");
        assertTrue(homePage.isBasketIconBadgeVisible(), "The cart is empty!");
        logger.info("Cart verification completed successfully.");
    }

    @Then("I should see a badge on the basket icon with number {int}")
    public void iShouldSeeABadgeOnTheBasketIconWithNumber(int number) {
        Assertions.assertTrue(homePage.hasBasketIconBadgeNumber(number));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }

    private String unifyPriceString(String rawPrice) {
        if (rawPrice.contains("zł")) {
            rawPrice = rawPrice.substring(0, rawPrice.indexOf("zł")).trim();
        }
        return rawPrice.replace(" ", "");
    }

    private boolean currentPageContains(String phrase) {
        return driver.getPageSource().contains(phrase);
    }
}
