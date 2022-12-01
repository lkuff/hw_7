package tests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebTests extends TestBase {

    @BeforeEach
    void setUp() {
        open("https://brandshop.ru/");
    }

    @ValueSource(strings = {
            "Новинки", "Бренды", "Мужское", "Женское", "Аксессуары", "Скидки"})
    @ParameterizedTest(name = "Проверка наличия элемента {0} на главной странице сайта в хедере")
    void checkElementTest(String element) {
        $(".header__wrapper").shouldHave(text(element));
    }

    @CsvSource(value = {
            "Мужское, Мужская одежда",
            "Женское, Женская одежда"
    })
    @ParameterizedTest(name = "Проверка открытия страницы {1} при поиске {0}")
    void checkOpenPageTest(String searchQuery, String openPage) {
        $(".icon-search").click();
        $(".digi-search-form__input").setValue(searchQuery).pressEnter();
        $(".digi-category-found-list").shouldHave(text(openPage));
    }

    static Stream<Arguments> checkGenderTest() {
        return Stream.of(
                Arguments.of("Мужское", List.of("мужской", "унисекс")),
                Arguments.of("Женское", List.of("женский", "унисекс", "мужской"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка наличия пола {1} на странице выбора одежды при поиске {0}")
    void checkGenderTest(String searchQuery, List<String> gender) {
        $(".icon-search").click();
        $(".digi-search-form__input").setValue(searchQuery).pressEnter();
        $$(".facet-sex li").filter(visible).shouldHave(CollectionCondition.texts(gender));
    }
}