package ru.cbr;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CsvFileSourseExample {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        open("https://www.cbr.ru/search/");
        executeJavaScript("$('footer').remove()");
        $(byText("Подтвердить")).click();
        //Configuration.holdBrowserOpen = true;
    }

    @CsvFileSource(resources = "/testData.csv")
    @ParameterizedTest(name = "При поиске документа {0}, должно вывести {1} материал(а)")
    @Tag("CSVFileSource")
    void searchResultShouldBeCorrectForSelectedED(
            String documentType,
            String countResults
    ) {
        $("input").setValue(documentType).pressEnter();
        $(".results_counter").shouldHave(text(countResults));
    }

}
