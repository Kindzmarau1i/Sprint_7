import DTO.orders.objects.Orders;
import DTO.orders.response.GetOrdersResponseDTO;
import api.OrderApi;
import api.TypedResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

@DisplayName("Список заказов")
public class GetOrdersTest extends BaseTest {

    OrderApi orderApi = new OrderApi();

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void getOrdersTest() {
        // Получить список заказов
        TypedResponse<GetOrdersResponseDTO> response = orderApi.getOrders();

        // Проверить, что в тело ответа возвращается список заказов
        List<Orders> orders = response.response().then().extract().jsonPath().getList("orders", Orders.class);
        MatcherAssert.assertThat(orders, not(empty()));
        MatcherAssert.assertThat(orders, hasItems(
                hasProperty("id"),
                hasProperty("courierId"),
                hasProperty("firstName"),
                hasProperty("lastName"),
                hasProperty("address"),
                hasProperty("metroStation"),
                hasProperty("phone"),
                hasProperty("rentTime"),
                hasProperty("deliveryDate"),
                hasProperty("track"),
                hasProperty("color"),
                hasProperty("comment"),
                hasProperty("createdAt"),
                hasProperty("updatedAt"),
                hasProperty("status")
        ));
    }
}