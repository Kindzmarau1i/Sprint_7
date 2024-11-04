package api;

import DTO.orders.request.CreateOrdersRequestDTO;
import DTO.orders.response.CreateOrdersResponseDTO;
import DTO.orders.response.GetOrdersResponseDTO;
import constants.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API для работы с заказами
 */
public class OrderApi {

    @Step("Создание заказа")
    public TypedResponse<CreateOrdersResponseDTO> createOrder(CreateOrdersRequestDTO request) {
        System.out.println("\n");
        Response response = given().log().method().log().uri().log().body()
                .header("Content-type", "application/json")
                .body(request)
                .post(Endpoints.ORDERS);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, CreateOrdersResponseDTO.class);
    }

    @Step("Получение списка заказов")
    public TypedResponse<GetOrdersResponseDTO> getOrders() {
        System.out.println("\n");
        Response response = given().log().method().log().uri()
                .get(Endpoints.ORDERS);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, GetOrdersResponseDTO.class);
    }

    @Step("Получение списка заказов")
    public TypedResponse<GetOrdersResponseDTO> getOrders(String queryParam, String queryValue) {
        System.out.println("\n");
        Response response = given().log().method().log().uri()
                .queryParam(queryParam, queryValue)
                .get(Endpoints.ORDERS);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, GetOrdersResponseDTO.class);
    }

    @Step("Получение списка заказов")
    public TypedResponse<GetOrdersResponseDTO> getOrders(String queryParam, String queryValue, Object... query) {
        System.out.println("\n");
        Response response = given().log().method().log().uri()
                .queryParams(queryParam, queryValue, query)
                .get(Endpoints.ORDERS);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, GetOrdersResponseDTO.class);
    }
}