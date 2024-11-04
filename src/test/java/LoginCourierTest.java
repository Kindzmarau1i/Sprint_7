import DTO.courier.request.AuthCourierRequestDTO;
import DTO.courier.request.CreateCourierRequestDTO;
import DTO.courier.response.AuthCourierResponseDTO;
import api.CourierApi;
import api.TypedResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;

@DisplayName("Логин курьера")
public class LoginCourierTest {

    CourierApi courierApi = new CourierApi();

    private final String LOGIN = "Логин";
    private final String PASSWORD = "Пароль";
    private final String FIRST_NAME = "Имя";

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка, курьер может авторизоваться")
    public void loginCourierSuccessTest() {
        // Подготовить тестовые данные
        Map<String, String> testData = prepareTestData();

        // Создать нового курьера
        createCourier(testData.get(LOGIN), testData.get(PASSWORD), testData.get(FIRST_NAME));

        // Аворизовать курьера
        TypedResponse<AuthCourierResponseDTO> secondResponse = getCourierId(testData.get(LOGIN), testData.get(PASSWORD));

        // 1.Проверить, что курьер может автризоваться
        // 2.Проверить, что для авторизации нужно передать все обязательные поля
        // 3.Проверить, что успешный запрос возвращает id
        Assert.assertEquals(200, secondResponse.statusCode());
        secondResponse.response().then().assertThat().body("", hasKey("id"));

        // Удалить курьера
        courierApi.deleteCourier(getCourierId(testData.get(LOGIN), testData.get(PASSWORD)).body().getId());
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль")
    @Description("Проверка, что запрос возвращает ошибку, если неправильно указать логин или пароль")
    public void wrongLoginCourierTest() {

        // Аворизовать курьера
        TypedResponse<AuthCourierResponseDTO> response = getCourierId(RandomStringUtils.randomAlphabetic(7),
                RandomStringUtils.randomAlphabetic(5));

        // 1.Проверить, что система вернёт ошибку, если неправильно указать логин или пароль
        // 2.Проверить, что если авторизоваться под несуществующим пользователем, запрос возвращает ошибку
        Assert.assertEquals(404, response.statusCode());
        Assert.assertEquals("Учетная запись не найдена", response.error().getMessage());
    }

    @Test
    @DisplayName("Если какого-то поля нет, запрос возвращает ошибку")
    @Description("Проверка, что запрос возвращает ошибку, если какого-то поля нет")
    public void loginCourierWithoutRequiredAttributeTest() {

        // Аворизовать курьера
        TypedResponse<AuthCourierResponseDTO> response = getCourierId(null, RandomStringUtils.randomAlphabetic(7));

        // Проверить, что система вернёт ошибку, если не указать обязательный атрибут
        Assert.assertEquals(400, response.statusCode());
        Assert.assertEquals("Недостаточно данных для входа", response.error().getMessage());
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
    public void createCourier(String login, String password, String firstName) {
        courierApi.createCourier(CreateCourierRequestDTO.builder()
                .login(login)
                .password(password)
                .firstName(firstName)
                .build());
    }

    /**
     * Авторизовать курьера
     */
    public TypedResponse<AuthCourierResponseDTO> getCourierId(String login, String password) {
        return courierApi.loginCourier(AuthCourierRequestDTO.builder()
                .login(login)
                .password(password)
                .build());
    }
}
