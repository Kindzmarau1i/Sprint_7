import DTO.courier.request.CreateCourierRequestDTO;
import DTO.courier.response.CreateCourierResponseDTO;
import api.CourierApi;
import api.TypedResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

@DisplayName("Создание курьера")
public class CreateCourierTest extends BaseTest {

    CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверка, что можно создать курьера")
    public void createCourierSuccessfulTest() {
        // Создать нового курьера
        TypedResponse<CreateCourierResponseDTO> response = createCourier(LOGIN, PASSWORD, FIRST_NAME);

        // Проверить, что курьера можно создать
        Assert.assertEquals(201, response.statusCode());
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверка, что нельзя создать двух одинаковых курьеров")
    public void createSameCourierTest() {
        // Создать нового курьера
        TypedResponse<CreateCourierResponseDTO> firstResponse = createCourier(LOGIN, PASSWORD, FIRST_NAME);
        Assert.assertEquals(201, firstResponse.statusCode());

        // Создать курьера с существующими данными
        TypedResponse<CreateCourierResponseDTO> secondResponse = createCourier(LOGIN, PASSWORD, FIRST_NAME);

        // Проверить, что нельзя создать двух одинаковых курьеров
        Assert.assertEquals(409, secondResponse.statusCode());
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    @Description("Проверка, что в ответе запроса возвращается атрибут 'ok: true'")
    public void checkResponseBodyTest() {
        // Создать нового курьера
        TypedResponse<CreateCourierResponseDTO> response = createCourier(LOGIN, PASSWORD, FIRST_NAME);

        // Проверить, что курьера можно создать
        Assert.assertTrue(response.body().getOk());
    }

    @Test
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку")
    @Description("Проверка, что запрос возвращает ошибку, если не пердать обазятельный атрибут")
    public void createCourierWithoutRequiredAttributes() {
        // Создать нового курьера без обязательного атрибута
        TypedResponse<CreateCourierResponseDTO> response = courierApi.createCourier(CreateCourierRequestDTO.builder()
                .password(PASSWORD)
                .firstName(FIRST_NAME)
                .build());

        // Проверить, что на создание курьера вернулась ошибка
        Assert.assertEquals(400, response.statusCode());
        Assert.assertEquals("Недостаточно данных для создания учетной записи", response.error().getMessage());
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Description("Проверка, что запрос возвращает ошибку, если создать курьера с не уникальным логином")
    public void createCourierWithExistLogin() {
        // Создать нового курьера
        createCourier(LOGIN, PASSWORD, FIRST_NAME);

        // Создать курьера с существующим логином
        TypedResponse<CreateCourierResponseDTO> secondResponse = createCourier(LOGIN,
                RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(10));

        // Проверить, что на создание курьера вернулась ошибка
        Assert.assertEquals(409, secondResponse.statusCode());
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.", secondResponse.error().getMessage());
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
}