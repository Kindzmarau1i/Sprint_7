import DTO.courier.request.AuthCourierRequestDTO;
import DTO.courier.request.CreateCourierRequestDTO;
import DTO.courier.response.CreateCourierResponseDTO;
import api.CourierApi;
import api.TypedResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

@DisplayName("Создание курьера")
public class CreateCourierTest {

    CourierApi courierApi = new CourierApi();

    private final String LOGIN = "Логин";
    private final String PASSWORD = "Пароль";
    private final String FIRST_NAME = "Имя";

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверка, что можно создать курьера")
    public void createCourierSuccessfulTest() {
        // Подготовить тестовые данные
        Map<String, String> testData = prepareTestData();

        // Создать нового курьера
        TypedResponse<CreateCourierResponseDTO> response = createCourier(testData.get(LOGIN), testData.get(PASSWORD),
                testData.get(FIRST_NAME));

        // Проверить, что курьера можно создать
        Assert.assertEquals(201, response.statusCode());

        // Удалить курьера
        courierApi.deleteCourier(getCourierId(testData.get(LOGIN), testData.get(PASSWORD)));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверка, что нельзя создать двух одинаковых курьеров")
    public void createSameCourierTest() {
        // Подготовить тестовые данные
        Map<String, String> testData = prepareTestData();

        // Создать нового курьера
        TypedResponse<CreateCourierResponseDTO> firstResponse = createCourier(testData.get(LOGIN),
                testData.get(PASSWORD), testData.get(FIRST_NAME));
        Assert.assertEquals(201, firstResponse.statusCode());

        // Создать курьера с существующими данными
        TypedResponse<CreateCourierResponseDTO> secondResponse = createCourier(testData.get(LOGIN),
                testData.get(PASSWORD), testData.get(FIRST_NAME));

        // Проверить, что нельзя создать двух одинаковых курьеров
        Assert.assertEquals(409, secondResponse.statusCode());

        // Удалить курьера
        courierApi.deleteCourier(getCourierId(testData.get(LOGIN), testData.get(PASSWORD)));
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    @Description("Проверка, что в ответе запроса возвращается атрибут 'ok: true'")
    public void checkResponseBodyTest() {
        // Подготовить тестовые данные
        Map<String, String> testData = prepareTestData();

        // Создать нового курьера
        TypedResponse<CreateCourierResponseDTO> response = createCourier(testData.get(LOGIN), testData.get(PASSWORD),
                testData.get(FIRST_NAME));

        // Проверить, что курьера можно создать
        Assert.assertTrue(response.body().getOk());

        // Удалить курьера
        courierApi.deleteCourier(getCourierId(testData.get(LOGIN), testData.get(PASSWORD)));
    }

    @Test
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку")
    @Description("Проверка, что запрос возвращает ошибку, если не пердать обазятельный атрибут")
    public void createCourierWithoutRequiredAttributes() {
        // Подготовить тестовые данные
        Map<String, String> testData = prepareTestData();

        // Создать нового курьера без обязательного атрибута
        TypedResponse<CreateCourierResponseDTO> response = courierApi.createCourier(CreateCourierRequestDTO.builder()
                .password(testData.get(PASSWORD))
                .firstName(testData.get(FIRST_NAME))
                .build());

        // Проверить, что на создание курьера вернулась ошибка
        Assert.assertEquals(400, response.statusCode());
        Assert.assertEquals("Недостаточно данных для создания учетной записи", response.error().getMessage());

        // Удалить курьера
        courierApi.deleteCourier(getCourierId(testData.get(LOGIN), testData.get(PASSWORD)));
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Description("Проверка, что запрос возвращает ошибку, если создать курьера с не уникальным логином")
    public void createCourierWithExistLogin() {
        // Подготовить тестовые данные
        Map<String, String> testData = prepareTestData();

        // Создать нового курьера
        createCourier(testData.get(LOGIN), testData.get(PASSWORD), testData.get(FIRST_NAME));

        // Создать курьера с существующим логином
        TypedResponse<CreateCourierResponseDTO> secondResponse = createCourier(testData.get(LOGIN),
                RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(10));

        // Проверить, что на создание курьера вернулась ошибка
        Assert.assertEquals(409, secondResponse.statusCode());
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.", secondResponse.error().getMessage());

        // Удалить курьера
        courierApi.deleteCourier(getCourierId(testData.get(LOGIN), testData.get(PASSWORD)));
    }

    /**
     * Подготовить тест-дату
     */
    public Map<String, String> prepareTestData() {
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(5);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return Map.of(LOGIN, login, PASSWORD, password, FIRST_NAME, firstName);
    }

    /**
     * Создать нового курьера
     */
    public TypedResponse<CreateCourierResponseDTO> createCourier(String login, String password, String firstName) {
        return courierApi.createCourier(CreateCourierRequestDTO.builder()
                .login(login)
                .password(password)
                .firstName(firstName)
                .build());
    }

    /**
     * Получить id курьера
     */
    public Integer getCourierId(String login, String password) {
        return courierApi.loginCourier(AuthCourierRequestDTO.builder()
                .login(login)
                .password(password)
                .build()).body().getId();
    }
}