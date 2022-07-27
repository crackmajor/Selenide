import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.headless;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class appOrderTest {

    @BeforeEach
    public void conf() {
        Configuration.browser = "firefox";
        open("http://localhost:9999/");
    }


    @Test
    void appOrderTest() {
        $(".input_type_text > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("Василий");
        $(".input_type_tel > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("+79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $(".paragraph").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void wrongNameTest() {
        $(".input_type_text > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("Vasiliy");
        $(".input_type_tel > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("+79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $(".input_type_text > span:nth-child(1) > span:nth-child(3)").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void wrongNumberTest() {
        $(".input_type_text > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("Василий");
        $(".input_type_tel > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("79522263366");
        $(".checkbox__box").click();
        $(".button__content").click();
        $(".input_type_tel > span:nth-child(1) > span:nth-child(3)").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void emptyBoxTest() {
        $(".input_type_text > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("Василий");
        $(".input_type_tel > span:nth-child(1) > span:nth-child(2) > input:nth-child(1)").setValue("+79522263366");
        $(".button__content").click();
        String x = $(".checkbox").getCssValue("color");
        String y = "rgb(255, 92, 92)";
        assertEquals(x, y);
    }

    @Test
    void emptySendTest() {
        $(".button__content").click();
        $(".input_type_text > span:nth-child(1) > span:nth-child(3)").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptySendWithBoxTest() {
        $(".checkbox__box").click();
        $(".button__content").click();
        $(".input_type_text > span:nth-child(1) > span:nth-child(3)").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
