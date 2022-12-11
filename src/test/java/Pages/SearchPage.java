package Pages;

import ModalBox.DatesBox;
import ModalBox.FiltersBox;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;


public class SearchPage {

    public FiltersBox openFilters(){
        $(By.xpath("//span[text()=\"Filter\"]")).click();
        $(By.xpath("//div[text()=\"Filters\"]")).should(appear);
        return page(FiltersBox.class);
    }

    public DatesBox openCalendar(){
        $(By.xpath("//div[text()=\"Check-in\"]")).click();
        return page(DatesBox.class);
    }


    public void selectedDatesAreShown(String checkIn, String checkOut) {
        $(By.xpath("//div[text()=\"Check-in\"]/following-sibling::div")).should(text(checkIn));
        $(By.xpath("//div[text()=\"Check-out\"]/following-sibling::div")).should(text(checkOut));
    }
}
