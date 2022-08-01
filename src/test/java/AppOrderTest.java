import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppOrderTest {

    @BeforeEach
    public void conf() {
        Configuration.browser = "firefox";
        open("http://localhost:9999/");
    }

    @Test
    void appOrderTest() {
        $("[data-test-id='name'] input").setValue("Василий");
        $("[data-test-id='phone'] input").setValue("+79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void appOrderTestWithCompoundSurname() {
        $("[data-test-id='name'] input").setValue("Капабланка-и-Граупера Василий");
        $("[data-test-id='phone'] input").setValue("+79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void wrongNameTest() {
        $("[data-test-id='name'] input").setValue("Vasiliy");
        $("[data-test-id='phone'] input").setValue("+79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void wrongNumberTest() {
        $("[data-test-id='name'] input").setValue("Василий");
        $("[data-test-id='phone'] input").setValue("79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void emptyBoxTest() {
        $("[data-test-id='name'] input").setValue("Василий");
        $("[data-test-id='phone'] input").setValue("+79522263366");
        $(".button__content").click();
        $("label.checkbox_theme_alfa-on-white.input_invalid").shouldBe();
    }

    @Test
    void emptySendTest() {
        $(".button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptySendWithBoxTest() {
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void sendWithoutNumber() {
        $("[data-test-id='name'] input").setValue("Василий Васечкин");
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void sendWithoutName() {
        $("[data-test-id='phone'] input").setValue("+79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
