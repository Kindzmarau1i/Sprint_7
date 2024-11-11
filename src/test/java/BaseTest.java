import DTO.courier.request.AuthCourierRequestDTO;
import api.CourierApi;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;

public class BaseTest {

    CourierApi courierApi = new CourierApi();

    protected String LOGIN;
    protected String PASSWORD;
    protected String FIRST_NAME;

    @Before
    public void prepareTestData() {
        this.LOGIN = RandomStringUtils.randomAlphabetic(7);
        this.PASSWORD = RandomStringUtils.randomAlphabetic(5);
        this.FIRST_NAME = RandomStringUtils.randomAlphabetic(10);
    }

    @After
    public void deleteCourier() {
        courierApi.deleteCourier(
                courierApi.loginCourier(AuthCourierRequestDTO.builder()
                        .login(LOGIN)
                        .password(PASSWORD)
                        .build()).body().getId()
        );
    }
}
