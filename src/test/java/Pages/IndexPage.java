package Pages;

import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class IndexPage {

    public IndexPage openPage(){
        open("/");
        return this;
    }

    public SearchPage search(){
        $(By.xpath("//button//span[text()=\"Search\"]")).should(appear).click();
        $(By.xpath("//h2")).shouldHave(text("properties found")).should(appear, Duration.ofSeconds(15));
        return page(SearchPage.class);
    }
}
