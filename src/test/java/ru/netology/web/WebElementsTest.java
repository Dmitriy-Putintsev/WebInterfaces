package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebElementsTest {
    WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp1() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void close() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldOpenBrowser() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79277656556");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click(); // чекбокс
        driver.findElement(By.tagName("button")).click(); // Отправить

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim()); // trim-удаляет лишние пробелы и с лева и с права
    }

    @Test
    public void shouldPhoneFieldValidation() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7(927)765-65-56");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldBeExposedConsentFlag() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79277656556");
        driver.findElement(By.tagName("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }


}