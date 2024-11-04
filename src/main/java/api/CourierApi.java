package api;

import DTO.courier.request.AuthCourierRequestDTO;
import DTO.courier.request.CreateCourierRequestDTO;
import DTO.courier.response.AuthCourierResponseDTO;
import DTO.courier.response.CreateCourierResponseDTO;
import constants.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API для работы с курьерами
 */
public class CourierApi {

    @Step("Создание курьера")
    public TypedResponse<CreateCourierResponseDTO> createCourier(CreateCourierRequestDTO request) {
        System.out.print("\n");
        Response response = given().log().method().log().uri().log().body()
                .header("Content-type", "application/json")
                .body(request)
                .post(Endpoints.COURIER);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, CreateCourierResponseDTO.class);
    }

    @Step("Логин курьера в системе")
    public TypedResponse<AuthCourierResponseDTO> loginCourier(AuthCourierRequestDTO request) {
        System.out.print("\n");
        Response response = given().log().method().log().uri().log().body()
                .header("Content-type", "application/json")
                .body(request)
                .post(Endpoints.COURIER_LOGIN);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, AuthCourierResponseDTO.class);
    }

    @Step("Удаление курьера")
    public TypedResponse<CreateCourierResponseDTO> deleteCourier(Integer courierId) {
        System.out.print("\n");
        Response response = given().log().method().log().uri()
                .delete(Endpoints.COURIER + "/" + courierId);
        System.out.print("Response body: ");
        response.then().log().body();
        return new TypedResponse<>(response, CreateCourierResponseDTO.class);
    }
}