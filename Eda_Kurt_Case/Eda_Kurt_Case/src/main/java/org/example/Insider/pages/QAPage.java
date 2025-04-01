package org.example.Insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class QAPage {
    WebDriver driver;

    public QAPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openQAJobsPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
    }

    public void clickSeeAllQAJobs() {
        driver.findElement(By.xpath("//a[contains(text(),'See all QA jobs')]"))
                .click();
    }

    public void applyFilters() {
        driver.findElement(By.xpath("//button[contains(text(),'Filter')]"))
                .click();
        driver.findElement(By.xpath("//select[@name='location']")).sendKeys("Istanbul, Turkey");
        driver.findElement(By.xpath("//select[@name='department']")).sendKeys("Quality Assurance");
    }

    public boolean areFilteredJobsCorrect() {
        List<WebElement> jobCards = driver.findElements(By.cssSelector(".position-list-item-wrapper"));
        for (WebElement job : jobCards) {
            String position = job.findElement(By.cssSelector(".position-title"))
                    .getText();
            String department = job.findElement(By.cssSelector(".position-department"))
                    .getText();
            String location = job.findElement(By.cssSelector(".position-location"))
                    .getText();

            if (!position.contains("Quality Assurance") ||
                    !department.equals("Quality Assurance") ||
                    !location.equals("Istanbul, Turkey")) {
                return false;
            }
        }
        return true;
    }

    public boolean viewRoleRedirectsToLever() {
        WebElement viewRoleButton = driver.findElement(By.xpath("//a[contains(text(),'View Role')]"));
        viewRoleButton.click();
        return driver.getCurrentUrl().contains("lever.co");
    }
}