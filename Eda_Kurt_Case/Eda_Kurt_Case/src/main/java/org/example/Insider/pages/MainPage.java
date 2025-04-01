package org.example.Insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage(String url) {
        driver.get(url);
    }

    public boolean isHomePageLoaded() {
        return driver.findElement(By.cssSelector("#navigation img"))
                .isDisplayed();
    }

    public void goToCareers() {
        WebElement companyMenu = driver.findElement(By.xpath("//a[@class='nav-link dropdown-toggle' and contains(text(),'Company')]"));
        companyMenu.click();

        WebElement careersLink = driver.findElement(By.xpath("//a[contains(text(),'Careers')]"));
        careersLink.click();
    }
}
