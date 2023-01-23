package ru.cbr;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ValueSourceExample {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        open("https://www.cbr.ru/search/");
        executeJavaScript("$('footer').remove()");
        $(byText("Подтвердить")).click();
        //Configuration.holdBrowserOpen = true;
    }

    @ValueSource(strings = {"ED807", "ED808"})
    @ParameterizedTest(name = "Поиск документа типа {0}")
    @Tag("ValueSource")
    void searchOfDocumentEDType(String documentType) {
        $("input").setValue(documentType).pressEnter();
        $$(".cross-result").first().shouldHave(text(documentType));
    }

}
