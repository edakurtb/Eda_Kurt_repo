package org.example.InsiderTest;

import org.example.Insider.base.BaseTest;
import org.example.Insider.pages.CareersPage;
import org.example.Insider.pages.MainPage;
import org.example.Insider.pages.QAPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsiderTest extends BaseTest {

    @Test
    public void fullTestScenario() {
        MainPage homePage = new MainPage(driver);
        CareersPage careersPage = new CareersPage(driver);
        QAPage qaPage = new QAPage(driver);

        // Step 1: Go to homepage and verify it
        homePage.openHomePage("https://useinsider.com/");
        Assertions.assertTrue(homePage.isHomePageLoaded(), "Home page did not load correctly");

        // Step 2: Navigate to Careers and verify sections
        homePage.goToCareers();
        Assertions.assertTrue(careersPage.isCareerSectionsVisible(), "Career page sections are not visible");

        // Step 3: Go to QA jobs page and apply filters
        qaPage.openQAJobsPage();
        qaPage.clickSeeAllQAJobs();
        qaPage.applyFilters();

        // Step 4: Validate filtered job listings
        Assertions.assertTrue(qaPage.areFilteredJobsCorrect(), "Job filter results are incorrect");

        // Step 5: View Role redirection to Lever
        Assertions.assertTrue(qaPage.viewRoleRedirectsToLever(), "View Role button did not redirect to Lever");
    }
}