package org.tmpl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private static final String BASE_URL = "https://www.t-mobile.pl/";
    private static final String HOME_PAGE_TITLE = "Telefony, Tablety, Laptopy, Szybki Internet - Dołącz do T-Mobile";
    Actions actions = new Actions(driver);

    @FindBy(xpath = "//*[text()='Urządzenia']")
    private WebElement devicesMenu;

    @FindBy(xpath = "//*[@id=\"main-menu\"]/div/ul/li[1]/div/div/ul/li[4]/ul/li[1]/a/div/span")
    //todo - znalezc lepszy selector
    private WebElement noSubscriptionSmartphones;

    @FindBy(id = "didomi-notice-agree-button")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//*[@title=\"Koszyk\"]")
    private WebElement basketIcon;

    @FindBy(xpath = "//body[contains(@class, 'is-expanded-account')]")
    private WebElement isMenuExpanded;

    @FindBy(xpath = "//*[contains(@class, 'rounded-full bottom-[6px]')]")
    private WebElement basketBadge;


    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openHomePage() {
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    public void hoverMouseOverDevicesMenu() {
        actions.moveToElement(devicesMenu).perform();
    }

    public void clickNoSubscribtionSmartphones() {
        noSubscriptionSmartphones.click();
    }

    public void clickAcceptCookiesButton() {
        if (acceptCookiesButton.isDisplayed()) acceptCookiesButton.click();
    }

    public boolean isBasketIconBadgeVisible() {
        return basketBadge.getText().matches("\\d+");
    }

    public boolean isOnHomePage(WebDriver driver) {
        return driver.getTitle().equals(HOME_PAGE_TITLE);
    }

    public boolean isMenuExpanded() {
        return isMenuExpanded.isEnabled();
    }

    public boolean hasBasketIconBadgeNumber(int number) {
        return isBasketIconBadgeVisible() &&
                basketBadge.getText().equals(String.valueOf(number));
    }
}
