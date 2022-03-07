
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class UserTest {

    @BeforeEach
    void setUp() {
        DataGenerator.User.activeUserRegistration();
        open("http://localhost:9999/");
        $("[data-test-id = 'login'] .input__control").setValue(DataGenerator.User.getUsername());
        $("[data-test-id = 'password'] .input__control").setValue(DataGenerator.User.getPassword());
    }

    @Test
    void shouldLoginSuccessfully() {
        $("[data-test-id='action-login'] .button__content").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

        @Test
        void shouldNotLoginByDifferentName() {
            $("[data-test-id='login'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, DataGenerator.User.getAnotherUsername());
            $("[data-test-id='action-login'] .button__content").click();
            $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
        }

        @Test
        void shouldNotLoginByDifferentPassword () {
            $("[data-test-id='password'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, DataGenerator.User.getAnotherPassword());
            $("[data-test-id='action-login'] .button__content").click();
            $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
        }

        @Test
        void shouldNotLoginDueBlock () {
            DataGenerator.User.inactiveUserRegistration();
            $("[data-test-id='action-login'] .button__content").click();
            $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
        }
    }

