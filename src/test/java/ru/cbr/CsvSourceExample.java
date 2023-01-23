package ru.cbr;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CsvSourceExample {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        open("https://www.cbr.ru/search/");
        executeJavaScript("$('footer').remove()");
        $(byText("Подтвердить")).click();
        //Configuration.holdBrowserOpen = true;
    }

    @CsvSource({
            "ED807, 22",
            "ED808, 21"
    })
    @ParameterizedTest(name = "При поиске документа {0}, должно вывести {1} материал(а)")
    @Tag("CSVSource")
    void searchResultShouldBeCorrectForSelectedED(
            String documentType,
            String countResults
    ) {
        $("input").setValue(documentType).pressEnter();
        $(".results_counter").shouldHave(text(countResults));
    }

}
