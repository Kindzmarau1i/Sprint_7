package api;

import DTO.error.TestErrorDescription;
import io.restassured.response.Response;

/**
 * Обертка для работы с ответами запросов
 * Позволяет возвращать как объект Response, так и десереализовывать json в объект
 * Позволяет работать с телом ошибок
 */
public class TypedResponse<T> {
    private final Response response;
    private final Class<T> cls;
    private T valid;
    private TestErrorDescription invalid;

    public TypedResponse(Response response, Class<T> cls) {
        this.response = response;
        this.cls = cls;
    }

    public T body() {
        if (valid == null && !response.body().asString().contentEquals("")) {
            valid = response.as(cls);
        }
        return valid;
    }

    public Response response() {
        return response;
    }

    public int statusCode() {
        return response.statusCode();
    }

    public TestErrorDescription error() {
        if (invalid == null && !response.body().asString().contentEquals("")) {
            invalid = response.as(TestErrorDescription.class);
        }
        return invalid;
    }
}