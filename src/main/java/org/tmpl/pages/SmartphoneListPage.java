package org.tmpl.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SmartphoneListPage extends BasePage {
    public SmartphoneListPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@data-qa='LST_ProductCard0']")
    private WebElement firstProduct;

    public void clickFirstProduct() {
        firstProduct.click();
    }

    public boolean isTheListDisplayed() {
        return firstProduct.isEnabled();
    }

}
