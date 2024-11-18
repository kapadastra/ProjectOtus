package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // Локатор для раздела "Тестирование"
    private By testingCoursesSection = By.xpath("//div[text()='Тестирование']");

    // Локатор для раздела "Обучение"
    private By trainingSection = By.cssSelector("span[title='Обучение']");

    // Локатор для "Календаря мероприятий" в выпадающем меню
    private By eventsCalendar = By.cssSelector("a[href='https://otus.ru/events/near']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }



    // Метод для клика по разделу "Тестирование"
    public void clickTestingCourses() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Кликакем на раздел Тестирование");
        wait.until(ExpectedConditions.visibilityOfElementLocated(testingCoursesSection));
        driver.findElement(testingCoursesSection).click();
    }

    // Метод для перехода в "Календарь мероприятий" с наведением на "Обучение"
    public void goToEventsCalendar() {
        logger.info("Ищем элемент Обучения");
        Actions actions = new Actions(driver); // Создаем объект Actions для управления действиями на странице
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(trainingSection));
        WebElement trainingElement = driver.findElement(trainingSection); // Находим элемент "Обучение"

        // Наводим курсор на "Обучение" для открытия выпадающего меню
        logger.info("Наводим курсор на Обучение для открытия выпадающего меню");
        actions.moveToElement(trainingElement).perform();

        // После того как меню открылось, кликаем по "Календарь мероприятий"
        logger.info("Ждём появления Календаря мероприятий");
        wait.until(ExpectedConditions.visibilityOfElementLocated(eventsCalendar));
        logger.info("Кликакем на Календарь мероприятий");
        driver.findElement(eventsCalendar).click();
    }
}