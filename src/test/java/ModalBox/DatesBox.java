package ModalBox;

import Helpers.DateHelper;
import Pages.SearchPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DatesBox {

    private String calendarBoxSelector = "//div[@class=\"_Popover _Popover--show\"]";

    // Test
    // Check if selected dates are highlighted
    public void selectedDatesAreHighlighted(String checkIn, String checkOut){

        // Gating dates between check in and check out
        List<String> datesToBeHighLighted = DateHelper.DAYS_BetweenCheckInAndCheckOut(checkIn, checkOut);
        // Getting a both month days
        ElementsCollection days = $$(By.className("CalendarDay"));

        // Filter a days by css classes that show selected days
        List<String> highLightedDays =
                days.filter(not(Condition.cssClass("ktZJUZ"))).filter(not(Condition.cssClass("imdtFs"))).texts();

        // Checking if both collections have exactly same elements
        assertThat(highLightedDays).containsExactlyElementsOf(datesToBeHighLighted);
    }

    public void passedDatesAreDisabled(){
        String current = LocalDate.now().getMonth().toString();
        SelenideElement currentMonth = $(By.xpath(calendarBoxSelector+"/div/div[3]/div[1]")).should(Condition.text(current));
        ElementsCollection disabledDates = currentMonth.$$(By.className("CalendarDay"));
        for (SelenideElement element: disabledDates){
            if (Integer.parseInt(element.innerText())<DateHelper.currentDay()){
                element.should(Condition.attribute("disabled"));
            }
        }
    }

    // Check if selected dates are shown in calendar box
    public void selectedDatesAreShown(String checkIn, String checkOut) {

        // Formatting a dates as it shown in a calendar box
        String checkInMonth = checkIn.split(" ")[1].substring(0, 3);
        String checkInDay = checkIn.split(" ")[0];
        // Formatting a dates as it shown in a calendar box
        String checkOutMonth = checkOut.split(" ")[1].substring(0, 3);;
        String checkOutDay = checkOut.split(" ")[0];

        $(By.xpath(calendarBoxSelector+"/div/div[4]")).should(Condition.text(checkInMonth + " " +checkInDay), Duration.ofSeconds(4)).innerText();
        $(By.xpath(calendarBoxSelector+"/div/div[4]")).should(Condition.text(checkOutMonth + " " +checkOutDay), Duration.ofSeconds(4));
    }

    // Setting dates to calendar
    public SearchPage setDates(String checkIn, String checkOut) {
        Map<String, SelenideElement> tables = monthTables();

        String checkInMonth = checkIn.split(" ")[1];
        String checkInDay = checkIn.split(" ")[0];

        String checkOutMonth = checkOut.split(" ")[1];
        String checkOutDay = checkOut.split(" ")[0];

        checkIn(checkInDay, checkInMonth, tables);
        checkOut(checkOutDay, checkOutMonth, tables);

        return page(SearchPage.class);
    }

    // Clear dates in calendar
    public SearchPage clearDates(){
        $(By.xpath(calendarBoxSelector+"//button/span[text()=\"Clear dates\"]")).click();
        return page(SearchPage.class);
    }

    // Checks if there left any days in calendar that still selected
    public void datesAreCleared(){
        ElementsCollection days = $$(By.className("CalendarDay"));
        for (SelenideElement day : days){
            // Check by css class that styles selected days
            day.shouldNot(cssClass("fcUaJz"));
            day.shouldNot(cssClass("fnwDTN"));
        }
    }

    // Check if - "Select check-in and check-out dates" message are shown
    public void selectDatesMessageShown(){
        $(By.xpath(calendarBoxSelector+"//div[text()=\"Select check-in and check-out dates\"]")).should(appear, Duration.ofSeconds(4));
    }

    // Checking month table is shows current and next month
    public void currentAndNextMonthShown(){
        String current = LocalDate.now().getMonth().toString();
        String next = LocalDate.now().plusMonths(1).getMonth().toString();

        $(By.xpath(calendarBoxSelector+"/div/div[3]/div[1]")).should(Condition.text(current), Duration.ofSeconds(4));
        $(By.xpath(calendarBoxSelector+"/div/div[3]/div[3]")).should(Condition.text(next), Duration.ofSeconds(4));
    }

    // Private methods for inner use

    private void checkIn(String day, String month, Map<String, SelenideElement> tables){
        // Getting the right month table then getting all it's days then - selecting a date
        ElementsCollection daysForCheckIn = tables.get(month.toUpperCase(Locale.ROOT)).$$(By.className("CalendarDay"));
        daysForCheckIn.find(Condition.text(day)).click();
    }

    private void checkOut(String day, String month, Map<String, SelenideElement> tables){
        // Getting the right month table then getting all it's days then - selecting a date
        ElementsCollection checkOutDays = tables.get(month.toUpperCase(Locale.ROOT)).$$(By.className("CalendarDay"));
        checkOutDays.find(Condition.text(day)).click();
    }


    // Getting month tables as two separate elements to work with
    // - used to simplify cases when check in and checkout are not in same month
    private Map<String, SelenideElement> monthTables(){
        String current = LocalDate.now().getMonth().toString();
        String next = LocalDate.now().plusMonths(1).getMonth().toString();

        SelenideElement currentMonth = $(By.xpath(calendarBoxSelector+"/div/div[3]/div[1]")).should(Condition.text(current));
        SelenideElement nextMonth = $(By.xpath(calendarBoxSelector+"/div/div[3]/div[3]")).should(Condition.text(next));

        Map<String, SelenideElement> tablesMap = new HashMap<>();

        tablesMap.put(current, currentMonth);
        tablesMap.put(next, nextMonth);

        return tablesMap;
    }
}
