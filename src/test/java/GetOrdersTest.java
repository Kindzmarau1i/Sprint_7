import DTO.orders.response.GetOrdersResponseDTO;
import api.OrderApi;
import api.TypedResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Список заказов")
public class GetOrdersTest {

    OrderApi orderApi = new OrderApi();

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void getOrdersTest() {
        // Получить список заказов
        TypedResponse<GetOrdersResponseDTO> response = orderApi.getOrders();

        // Проверить, что в тело ответа возвращается список заказов
        response.response().then().assertThat().body("orders", notNullValue());
    }
}