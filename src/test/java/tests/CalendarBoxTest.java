package tests;

import Helpers.DateHelper;
import Helpers.PropertiesHelper;
import ModalBox.DatesBox;
import Pages.IndexPage;
import Pages.SearchPage;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Properties;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Feature("Calendar base functionality")
@DisplayName("Calendar test")
public class CalendarBoxTest {

    private  IndexPage indexPage;
    private SearchPage searchPage;
    private DatesBox datesBox;

    private String checkIn;
    private String checkOut;

    @BeforeAll
    public void beforeAll () throws IOException {

        Properties testProps = PropertiesHelper.getBaseProps();

        Configuration.baseUrl = testProps.getProperty("baseUrl");
        Configuration.pageLoadTimeout = Long.parseLong(testProps.getProperty("page.load.timeout"));
        Configuration.timeout = Long.parseLong(testProps.getProperty("elements.wat.timeout"));
        Configuration.headless = Boolean.parseBoolean(testProps.getProperty("headlessMode"));

        // Setting up the dates to work with
        checkIn = DateHelper.checkInDate();
        // Using X-Nights ( Booking-like service specific )
        checkOut = DateHelper.checkOutDateAfter_X_Nights(20);

        indexPage = new IndexPage().openPage();
    }


    @AfterAll
    public void afterAll (){
        // Cleanup if required
    }




    @Test
    @DisplayName("1 - Open calendar")
    @Description("Open calendar")
    public void step1() {
        searchPage = indexPage.search();
        datesBox = searchPage.openCalendar();
    }

    @Test
    @DisplayName("2 - Check unfilled calendar state")
    @Description("Passed dates are disabled + current and next month shown + message 'Select dates...' is shown ")
    public void step2() {
        datesBox.currentAndNextMonthShown();
        datesBox.passedDatesAreDisabled();
        datesBox.selectDatesMessageShown();
    }

    @Test
    @DisplayName("3 - Set dates")
    @Description("Setting dates and check the dates at search page fields")
    public void step3() {
        searchPage = datesBox.setDates(checkIn, checkOut);
        searchPage.selectedDatesAreShown(checkIn, checkOut);
    }

    @Test
    @DisplayName("4 - Check calendar state with filled dates")
    @Description("Current and next month shown + selected dates are shown in expanded calendar + selected dates are highlighted")
    public void step4() {
        datesBox = searchPage.openCalendar();
        datesBox.currentAndNextMonthShown();
        datesBox.selectedDatesAreShown(checkIn, checkOut);
        datesBox.selectedDatesAreHighlighted(checkIn, checkOut);

    }

    @Test
    @DisplayName("5 - Check calendar state after dates cleared")
    @Description("Clear dates + check if dates are cleared + message 'Select dates...' is shown")
    public void step5() {
        datesBox.clearDates();
        datesBox.datesAreCleared();
        datesBox.selectDatesMessageShown();
    }

}
