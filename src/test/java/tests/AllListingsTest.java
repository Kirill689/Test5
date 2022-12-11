package tests;

import Helpers.PropertiesHelper;
import Pages.AllListingsPage;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Properties;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Feature("Listings page test for correct items count")
@DisplayName("Listings page")
public class AllListingsTest {

    private  AllListingsPage allListingsPage;

    @BeforeAll
    public void beforeAll () throws IOException {

        Properties testProps = PropertiesHelper.getBaseProps();

        Configuration.baseUrl = testProps.getProperty("baseUrl");
        Configuration.pageLoadTimeout = Long.parseLong(testProps.getProperty("page.load.timeout"));
        Configuration.timeout = Long.parseLong(testProps.getProperty("elements.wat.timeout"));
        Configuration.headless = Boolean.parseBoolean(testProps.getProperty("headlessMode"));

        allListingsPage = new AllListingsPage().openPage();
    }

    @AfterAll
    public void afterAll (){
        // Cleanup if required
    }


    @Test
    @DisplayName("1 - Open all listings page")
    @Description("Open all listings page")
    public void step1() {
        allListingsPage = new AllListingsPage().openPage();
    }

    @Test
    @DisplayName("2 - Check listings number")
    @Description("Get listings number from 'All' label and scroll down till no more content to load + check if number of listings on page is correct")
    public void step2() {
        allListingsPage.scrollAllTableDown();
        allListingsPage.checkIfNumberOfItemsIsCorrect();
    }

}
