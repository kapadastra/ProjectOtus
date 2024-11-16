package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestingCoursesPage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(TestingCoursesPage.class);

    // Локатор для карточки "Тестирование"
    private By testingCoursesCard = By.xpath("//*[@id=\"__next\"]/div[1]/main/div/section[2]/div[2]/div/a[6]");

    // Локатор для всех карточек курсов
    private By courseCards = By.xpath("//*[@id=\"__next\"]/div[1]/main/div/section[2]/div[2]/div/a");

    // Локаторы внутри карточки курса
    private By courseTitle = By.xpath("//*[@id=\"__next\"]/div[1]/main/div/section/div[2]/div[2]/h1"); // заголовок курса
    private By courseDescription = By.xpath("//*[@id=\"__next\"]/div[1]/main/div/section/div[2]/div[2]/div[2]/p[1]"); // описание курса
    private By courseDuration = By.xpath("//*[@id=\"__next\"]/div[1]/main/div/section/div[3]/div/div[3]/p"); // длительность обучения
    private By courseFormat = By.xpath("//*[@id=\"__next\"]/div[1]/main/div/section/div[3]/div/div[4]/p"); // формат курса

    public TestingCoursesPage(WebDriver driver) {
        this.driver = driver;
    }


    // Метод для перехода в карточку курса из раздела "Тестирование"
    public void clickTestingCard(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Переход в карточку курса из раздела 'Тестирование'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(testingCoursesCard));
        driver.findElement(testingCoursesCard).click();
    }

    // Метод для подсчета количества карточек курсов
    public int getCourseCardsCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Получаем количество карточек курсов в разделе 'Тестирование'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseCards));
        List<WebElement> cards = driver.findElements(courseCards);
        logger.info("Найдено карточек курсов: " + cards.size());
        return cards.size();
    }

    // Метод для получения информации о курсе
    public String getCourseTitle() {
        logger.info("Проверяем название курса");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseTitle));
        String title = driver.findElement(courseTitle).getText();
        logger.info("Название курса: " + title);
        return title;
    }

    public String getCourseDescription() {
        logger.info("Проверяем описание курса");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseDescription));
        String description = driver.findElement(courseDescription).getText();
        logger.info("Описание курса: " + description);
        return description;
    }

    public String getCourseDuration() {
        logger.info("Проверяем длительность курса");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseDuration));
        String duration = driver.findElement(courseDuration).getText();
        logger.info("Длительность курса: " + duration);
        return duration;
    }

    public String getCourseFormat() {
        logger.info("Проверяем формат курса");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseFormat));
        String format = driver.findElement(courseFormat).getText();
        logger.info("Формат курса: " + format);
        return format;
    }
}

