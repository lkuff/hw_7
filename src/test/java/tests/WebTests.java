package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTests extends TestBase {

    @BeforeEach
    void setUp() {
        open("https://brandshop.ru/");
    }

    @ValueSource(strings = {
            "Новинки", "Бренды", "Мужское", "Женское", "Аксессуары", "Скидки"})
    @ParameterizedTest(name = "Проверка наличия элемента {0} на главной странице сайта в хедере")
    void checkElement(String element) {
        $(".header__wrapper").shouldHave(text(element));
    }
}
