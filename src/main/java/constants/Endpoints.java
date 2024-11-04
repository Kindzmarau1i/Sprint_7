package constants;

/**
 * URL-ы для работы с сервисом
 */
public class Endpoints {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    // courier
    public static final String COURIER = BASE_URI + "/api/v1/courier";
    public static final String COURIER_LOGIN = COURIER + "/login";

    // orders
    public static final String ORDERS = BASE_URI + "/api/v1/orders";
}