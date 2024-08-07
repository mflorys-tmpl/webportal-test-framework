package org.tmpl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//*[@class='buttonText'])[2]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='style_priceInfoSection']//div[@class='dt_price_change']//div")
    private List<WebElement> prices;

    public void addToCart() {
        addToCartButton.click();
    }

    public String getPriceOnStart() {
        return prices.get(prices.size() - 2).getText();
    }

    public String getMonthlyPrice() {
        return prices.getLast().getText();
    }
}
