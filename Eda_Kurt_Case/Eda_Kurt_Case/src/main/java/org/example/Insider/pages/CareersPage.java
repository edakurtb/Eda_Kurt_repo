package org.example.Insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage {
    WebDriver driver;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isCareerSectionsVisible() {
        return driver.findElement(By.xpath("//h3[normalize-space()='Our Locations']")).isDisplayed()
                && driver.findElement(By.xpath("//h3[normalize-space()='Teams']")).isDisplayed()
                && driver.findElement(By.xpath("//h3[normalize-space()='Life at Insider']")).isDisplayed();
    }
}