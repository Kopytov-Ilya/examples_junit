package ru.cbr;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.cbr.data.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MethodSourceExample {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        open("https://www.cbr.ru");
        executeJavaScript("$('footer').remove()");
        $(byText("Подтвердить")).click();
        Configuration.holdBrowserOpen = true;
    }

    static Stream<Arguments> cbrSiteShouldContainAllButtonsOnSlideForGivenLocale() {
        return Stream.of(
                Arguments.of(Locale.EN, List.of("About Bank of Russia Latest operational announcements")),
                Arguments.of(Locale.RU, List.of("О Банке России Интернет-приемная Вопросы и ответы"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Для локали {0} отображаются пункты {1}")
    @Tag("MethodSource")
    void cbrSiteShouldContainAllButtonsOnSlideForGivenLocale(
            Locale locale,
            List<String> buttons
    ) {
        $$(".header_lang_item").find(text(locale.name())).click();
        $$(".col-5")
                .filter(visible)
                .shouldHave(texts(buttons));
    }

}
