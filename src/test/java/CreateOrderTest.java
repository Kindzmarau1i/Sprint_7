import DTO.orders.request.CreateOrdersRequestDTO;
import DTO.orders.response.CreateOrdersResponseDTO;
import api.OrderApi;
import api.TypedResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasKey;

@DisplayName("Создание заказа")
@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final List<String> colour;
    OrderApi orderApi = new OrderApi();

    public CreateOrderTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[] getColourData() {
        return new Object[]{
                List.of("BLACK"),
                List.of("BLACK, GREY"),
                null
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка, что при создании заказа можно указать один цвет, несколько цветов, вообще не указывать цвет")
    public void loginCourierSuccessTest() {
        // Создать заказ
        TypedResponse<CreateOrdersResponseDTO> response = orderApi.createOrder(CreateOrdersRequestDTO.builder()
                .firstName(RandomStringUtils.randomAlphabetic(5))
                .lastName(RandomStringUtils.randomAlphabetic(7))
                .address(RandomStringUtils.randomAlphabetic(15))
                .metroStation(RandomStringUtils.randomAlphabetic(10))
                .phone(RandomStringUtils.randomAlphabetic(11))
                .rentTime(Integer.parseInt(RandomStringUtils.randomNumeric(1)))
                .deliveryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .comment(RandomStringUtils.randomAlphabetic(11))
                .color(colour != null ? colour.toArray(new String[3]) : null)
                .build());

        // 1.Проверить, что можно указать один из цветов — BLACK или GREY;
        // 2.Проверить, что можно указать оба цвета;
        // 3.Проверить, что можно совсем не указывать цвет;
        // 4.Проверить, что тело ответа содержит track.
        Assert.assertEquals(201, response.statusCode());
        response.response().then().assertThat().body("", hasKey("track"));
    }
}