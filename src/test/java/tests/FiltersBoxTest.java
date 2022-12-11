package tests;

import Helpers.PropertiesHelper;
import ModalBox.FiltersBox;
import Pages.IndexPage;
import Pages.SearchPage;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Properties;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Feature("Filters elements test")
@DisplayName("Filters test")
public class FiltersBoxTest {

    private IndexPage indexPage;
    private FiltersBox filtersBox;

    @BeforeAll
    public void beforeAll () throws IOException {

        Properties testProps = PropertiesHelper.getBaseProps();

        Configuration.baseUrl = testProps.getProperty("baseUrl");
        Configuration.pageLoadTimeout = Long.parseLong(testProps.getProperty("page.load.timeout"));
        Configuration.timeout = Long.parseLong(testProps.getProperty("elements.wat.timeout"));
        Configuration.headless = Boolean.parseBoolean(testProps.getProperty("headlessMode"));

        indexPage = new IndexPage().openPage();
    }


    @AfterAll
    public void afterAll (){
        // Cleanup if required
    }


    @Test
    @DisplayName("1 - Open filters")
    @Description("Go to search page and open filters window")
    public void step1() {
        SearchPage searchPage = indexPage.search();
        filtersBox = searchPage.openFilters();
    }

    @Test
    @DisplayName("2 - Check Min|Max field values")
    @Description("Test min and max values for all fields")
    public void step2(){
        filtersBox
                .fieldMinAndMaxValues("Beds", 0, 10)
                .fieldMinAndMaxValues("Bedrooms", 0, 10)
                .fieldMinAndMaxValues("Bathrooms", 0, 10);
    }

    @Test
    @DisplayName("3 - Check 'Check-box' elements")
    @Description("All amenities checkBoxes test")
    public void step3(){
        filtersBox
                .amenitiesSelect()
                .clearFilters()
                .closeFilters();
    }

}
