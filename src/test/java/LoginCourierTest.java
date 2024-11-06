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

import static org.hamcrest.Matchers.hasKey;

@DisplayName("Логин курьера")
public class LoginCourierTest extends BaseTest {

    CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка, курьер может авторизоваться")
    public void loginCourierSuccessTest() {
        // Создать нового курьера
        createCourier(LOGIN, PASSWORD, FIRST_NAME);

        // Аворизовать курьера
        TypedResponse<AuthCourierResponseDTO> secondResponse = getCourierId(LOGIN, PASSWORD);

        // 1.Проверить, что курьер может автризоваться
        // 2.Проверить, что для авторизации нужно передать все обязательные поля
        // 3.Проверить, что успешный запрос возвращает id
        Assert.assertEquals(200, secondResponse.statusCode());
        secondResponse.response().then().assertThat().body("", hasKey("id"));
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
