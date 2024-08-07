package org.tmpl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"osAppInnerContainer\"]/div[2]/section/section/div[5]/article/div[2]/div[1]") //uprosc
    private WebElement cartItem;

    @FindBy(xpath = "//span[@data-qa='BKT_TotalupFront']")
    private WebElement cartPriceOnStart;

    @FindBy(xpath = "//span[@data-qa='BKT_TotalMonthly']")
    private WebElement cartMonthlyPrice;

    public boolean isProductInCart() {
        waitForVisibility(cartItem);
        return cartItem.isDisplayed();
    }

    public String getCartPriceOnStart() {
        return cartPriceOnStart.getText();
    }

    public String getCartMonthlyPrice() {
        return cartMonthlyPrice.getText();
    }

    public boolean isuserOnCartPage(WebDriver driver){
        return driver.getTitle().equals("Koszyk");
    }
}
