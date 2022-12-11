package Pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class AllListingsPage {

    private ElementsCollection list;
    private int allListingsNumber;

    public AllListingsPage openPage(){
        open("/all-listings");
        return this;
    }

    // Scroll down till have content to load. When no more content to load assert listing elements
    // When no more content to load assert listing elementsCollection size to the value from All label
    public void scrollAllTableDown(){
        // loader=TRUE for first scroll down try (Checks if by scrolling down we can get more content to load)
        boolean loader = true;
        allListingsNumber = getListingsLabelValue();

        // Init listings on page, before scrolling
        list = $$(By.xpath("//a[contains(@href, '/listings/')]"));
        // Element for scroll to
        SelenideElement footerElement = $(By.xpath("//a[text()=\"Terms & conditions\"]"));

        // While still have content to load (loader=TRUE) - scroll down
        while (loader){
            footerElement.scrollIntoView(true);

            // If content loader not shown - loader = FALSE
            loader = contentLoader();

            list = $$(By.xpath("//a[contains(@href, '/listings/')]"));
        }

    }

    public void checkIfNumberOfItemsIsCorrect(){
        list.should(CollectionCondition.size(allListingsNumber));
    }

    // Private methods for inner use

    // Check if content loader appears after scroll and disappears after content loaded - returns TRUE if appeared
    private boolean contentLoader (){
        boolean isVisible = false;
        SelenideElement loader = $(By.xpath("//div[@class=\"sc-bUQyIj ilGZh\"]"));

        try {
            loader.should(Condition.visible, Duration.ofSeconds(5));
            isVisible = true;
            loader.should(Condition.hidden, Duration.ofSeconds(10));
        } catch (com.codeborne.selenide.ex.ElementNotFound e){
            // Last scroll needs to ignore ElementNotFound exception, to be sure there is no more content to load
        }
        return isVisible;
    }

    // Getting value that shown in All label
    private int getListingsLabelValue(){
        String allValue = $(By.xpath("//h2[text()=\"Properties\"]/..//div/span[text()=\"All\"]/span")).innerText().replaceAll("\\p{P}","");
        return Integer.parseInt(allValue);
    }
}
