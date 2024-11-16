package tests;

import factory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.TestingCoursesPage;
import pages.EventsCalendarPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseAndEventsTests {
    private static final Logger logger = LogManager.getLogger(CourseAndEventsTests.class);

    private WebDriver driver;
    private HomePage homePage;
    private TestingCoursesPage testingCoursesPage;
    private EventsCalendarPage eventsCalendarPage;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "edge");
        String baseUrl = System.getProperty("baseUrl", "https://otus.ru/");
        String mode = System.getProperty("mode", "fullscreen");
        // Инициализация WebDriver
        logger.info("Инициализация веб-драйвера");
        driver = WebDriverFactory.getBrowser(browser, mode);

        if (driver == null) {
            throw new IllegalStateException("WebDriver не был инициализирован");
        }


        // Инициализация страниц
        logger.info("Инициализация страниц");
        homePage = new HomePage(driver);
        testingCoursesPage = new TestingCoursesPage(driver);
        eventsCalendarPage = new EventsCalendarPage(driver);

        logger.info("Открытие главной страницы Otus");
        driver.get(baseUrl);
    }

    @AfterEach
    public void tearDown() {
        // Закрытие браузера
        if (driver != null) {
            logger.info("Закрытие веб-драйвера");
            driver.quit();
        }
    }

    @Test
    public void testCourseCount() {
        // Переход в раздел тестирования
        homePage.clickTestingCourses();

        // Проверка количества карточек курсов
        int courseCount = testingCoursesPage.getCourseCardsCount();
        assertEquals(10, courseCount, "Количество курсов в разделе 'Тестирование' отличается от ожидаемого!");
    }

    @Test
    public void testCourseDetails(){
        // Переход в раздел тестирования
        homePage.clickTestingCourses();

        // Переход на конкретную карточку курса
        testingCoursesPage.clickTestingCard();

        // Проверка деталей курса
        String title = testingCoursesPage.getCourseTitle();
        String description = testingCoursesPage.getCourseDescription();
        String duration = testingCoursesPage.getCourseDuration();
        String format = testingCoursesPage.getCourseFormat();

        assertTrue(title.length() > 0, "Название курса отсутствует!");
        assertTrue(description.length() > 0, "Описание курса отсутствует!");
        assertTrue(duration.length() > 0, "Длительность курса отсутствует!");
        assertTrue(format.length() > 0, "Формат курса отсутствует!");
    }

    @Test
    public void testEventDatesValidation() {
        // Переход в раздел событий
        homePage.goToEventsCalendar();

        // Прокрутка страницы для подгрузки карточек
        eventsCalendarPage.scrollToLoadAllEvents();

        // Проверка, что все даты мероприятий корректны
        assertTrue(eventsCalendarPage.areAllEventDatesValid(), "Некорректные даты мероприятий найдены!");
    }

    @Test
    public void testEventTypeFilter() {
        // Переход в раздел событий
        homePage.goToEventsCalendar();

        // Применение фильтра по типу "Открытые вебинары"
        eventsCalendarPage.filterByOpenWebinars();

        // Проверка, что отображаются только карточки с типом "Открытые вебинары"
        assertTrue(eventsCalendarPage.validateOnlyOpenWebinarsDisplayed(), "Найдены карточки с неверным типом мероприятий!");
    }
}