package ModalBox;

import Pages.SearchPage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FiltersBox {

    // Root filters element XPath
    public String filtersModalWindow = "//div[text()=\"Filters\"]/../../..";

    // Clear filters - returns FiltersBox object after filters were cleared
    public FiltersBox clearFilters(){
        ElementsCollection checkBoxes = $$(By.xpath(filtersModalWindow + "//input[@type=\"checkbox\"]"));
        $(By.xpath(filtersModalWindow + "//*[text()=\"Clear all\"]")).should(appear, Duration.ofSeconds(5)).click();
        for (SelenideElement checkBox : checkBoxes){
            checkBox.should(not(selected), Duration.ofSeconds(2));
        }
        return this;
    }

    // Close filters - returns search page object
    public SearchPage closeFilters(){
        $(By.xpath("//div[text()=\"Filters\"]/following-sibling::button")).click();
        return page(SearchPage.class);
    }


    // Apply filters
    public FiltersBox applyFilters(){
        $(By.xpath(filtersModalWindow + "//button//span[text()=\"Apply\"]")).click();
        $(By.xpath(filtersModalWindow)).should(disappear);
        return this;
    }


    // Setting field name and min/max values
    public FiltersBox fieldMinAndMaxValues(String fieldName, int min, int max){
        this.fieldMaxValue(fieldName, max);
        this.fieldMinValue(fieldName, min, max);
        return this;
    }


    public FiltersBox amenitiesSelect(){
        // CheckBoxes collection
        ElementsCollection checkBoxes = $$(By.xpath(filtersModalWindow + "//input[@type=\"checkbox\"]"));

        // Run over all checkBoxes - Click - Check if checkbox is selected
        for (SelenideElement checkBox : checkBoxes){
            checkBox.$(By.xpath("following-sibling::span")).click();
            checkBox.should(selected);
        }
        return this;
    }

    // Private methods for inner use

    private void fieldMaxValue(String fieldName, int max){
        // Field name selector
        String fieldSelector = String.format("//div[text()=\"%s\"]", fieldName);
        // Buttons collection of called field
        ElementsCollection buttons = $$(By.xpath(fieldSelector+"/following-sibling::div[1]//button"));

        for (int i = max; i > 0; i--){
            // Increment button get+click
            SelenideElement incrementButton = buttons.get(1);
            incrementButton.click();

            // Field counter value
            int counter = Integer.parseInt($(By.xpath(fieldSelector+"/following-sibling::div[1]//span")).innerText());

            // Logic of buttons to be enabled/disabled policy
            if(counter<max){
                incrementButton.should(enabled, Duration.ofSeconds(2));
            }
            if (counter>=max){
                incrementButton.should(disabled, Duration.ofSeconds(2));
            }
        }
    }

    private void fieldMinValue(String fieldName, int min, int max){
        // Field name selector
        String fieldSelector = String.format("//div[text()=\"%s\"]", fieldName);
        // Buttons collection of called field
        ElementsCollection buttons = $$(By.xpath(fieldSelector+"/following-sibling::div[1]//button"));
        for (int i = max; i > min; i--){
            // Increment button get+click
            SelenideElement dicrementButton = buttons.get(0);
            dicrementButton.click();

            // Field counter value
            int counter = Integer.parseInt($(By.xpath(fieldSelector+"/following-sibling::div[1]//span")).innerText());

            // Logic of buttons to be enabled/disabled policy
            if(counter>min){
                dicrementButton.should(enabled, Duration.ofSeconds(2));
            }
            if (counter<=min){
                dicrementButton.should(disabled, Duration.ofSeconds(2));
            }
        }
    }
}
