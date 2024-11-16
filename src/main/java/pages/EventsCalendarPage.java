package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventsCalendarPage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(EventsCalendarPage.class);

    // Локаторы
    private By eventCards = By.xpath("//a[@class='dod_new-event']"); // Карточки мероприятий
    private By eventDate = By.xpath(".//span[@class='dod_new-event__date-text']"); // Дата проведения в карточке
    private By eventTypeFilter = By.xpath("/html/body/div[1]/div/div[1]/div/section/header/div[1]/div/div[1]/span"); // Кнопка "Тип мероприятия"
    private By webinarOption = By.xpath("/html/body/div[1]/div/div[1]/div/section/header/div[1]/div/div[2]/a[4]"); // Опция "Открытые вебинары"
    private By eventType = By.xpath(".//div[@class='dod_new-type__text']"); // Тип мероприятия в карточке

    public EventsCalendarPage(WebDriver driver) {
        this.driver = driver;
    }

    // Прокрутка страницы вниз для подгрузки карточек
    public void scrollToLoadAllEvents() {
        logger.info("Прокручиваем страницу вниз для подгрузки всех карточек мероприятий...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(1000); // Ожидание подгрузки контента
            } catch (InterruptedException e) {
                logger.error("Ошибка при ожидании подгрузки контента", e);
            }
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
        logger.info("Все карточки мероприятий загружены.");
    }


    // Проверка, что все даты мероприятий больше или равны текущей дате
    public boolean areAllEventDatesValid() {
        logger.info("Проверяем, что все даты мероприятий больше или равны текущей дате...");
        List<WebElement> cards = driver.findElements(eventCards);
        logger.info("Найдено карточек мероприятий: " + cards.size());
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru")); // Пример: "18 ноября"

        for (WebElement card : cards) {
            try {
                // Находим дату внутри карточки
                WebElement dateElement = card.findElement(eventDate);
                String dateText = dateElement.getText();
                logger.info("Дата мероприятия (текст): " + dateText);

                // Добавляем текущий год к дате
                String fullDateText = dateText + " " + LocalDate.now().getYear();

                // Парсим дату
                LocalDate eventDate = LocalDate.parse(dateText + " " + LocalDate.now().getYear(), formatter);

                // Проверяем дату
                if (eventDate.isBefore(currentDate)) {
                    logger.error("Обнаружена дата мероприятия, которая раньше текущей: " + eventDate);
                    return false;
                }
            } catch (Exception e) {
                logger.error("Ошибка обработки даты для одной из карточек", e);
                return false;
            }
        }
        logger.info("Все даты мероприятий корректны.");
        return true;
    }

    // Применение фильтра по типу "Открытые вебинары"
    public void filterByOpenWebinars() {
        logger.info("Применяем фильтр по типу мероприятий: 'Открытые вебинары'...");
        driver.findElement(eventTypeFilter).click();
        driver.findElement(webinarOption).click();
        logger.info("Фильтр успешно применен.");
    }

    // Проверка, что на странице отображаются только карточки с типом "Открытые вебинары"
    public boolean validateOnlyOpenWebinarsDisplayed() {
        logger.info("Проверяем, что отображаются только карточки с типом 'Открытые вебинары'...");
        List<WebElement> cards = driver.findElements(eventCards);

        for (WebElement card : cards) {
            String type = card.findElement(eventType).getText();
            logger.info("Тип мероприятия в карточке: " + type);
            if (!type.equals("Открытый вебинар")) {
                logger.error("Найдена карточка с неподходящим типом: " + type);
                return false;
            }
        }
        logger.info("Все карточки соответствуют типу 'Открытый вебинар'.");
        return true;
    }

    // Парсинг текста даты в LocalDate
    private LocalDate parseDate(String dateText, DateTimeFormatter formatter) {
        LocalDate now = LocalDate.now();
        LocalDate parsedDate = LocalDate.parse(dateText + " " + now.getYear(), formatter);

        // Проверяем, не относится ли дата к следующему году
        if (parsedDate.isBefore(now)) {
            parsedDate = parsedDate.plusYears(1);
        }
        return parsedDate;
    }
}
