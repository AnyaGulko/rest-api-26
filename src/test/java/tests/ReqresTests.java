package tests;

import models.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static specs.Specifications.*;
import static utils.RandomUtils.getJob;
import static utils.RandomUtils.getName;

public class ReqresTests extends TestBase {

    @Test
    @DisplayName("Успешная авторизация пользователя")
    void successfulLoginTest() {
        LoginRequestBodyModel authData = new LoginRequestBodyModel();

        step("Создать запрос", () -> {
            authData.setEmail("eve.holt@reqres.in");
            authData.setPassword("cityslicka");
        });

        LoginResponseModel response = step("Выполнить запрос", () ->
                given(RequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(okResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Проверить ответ", () ->
                assertThat("QpwL5tke4Pnpja7X4").isEqualTo(response.getToken()));
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    void createUserTest() {
        UserCreationRequestModel request = new UserCreationRequestModel();

        step("Создать запрос", () -> {
            request.setName(getName());
            request.setJob(getJob());
        });

        UserCreationResponseModel response = step("Выполнить запрос", () ->
                given(RequestSpec)
                        .body(request)
                        .when()
                        .post("/users")
                        .then()
                        .spec(userCreatingResponseSpec)
                        .extract().as(UserCreationResponseModel.class));

        step("Проверить ответ", () -> {
            assertThat(request.getJob()).isEqualTo(response.getJob());
            assertThat(request.getName()).isEqualTo(response.getName());
            assertThat(response.getId()).isNotBlank();
            assertThat(response.getCreatedAt()).isInSameDayAs(new Date());
        });
    }


    @Test
    @Disabled
    @DisplayName("Получение списка цветов")
    void getColorsListTest() {
        ColorsListResponseModel response = step("Выполнить запрос", () ->
                given(RequestSpec)
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(okResponseSpec)
                        .extract().as(ColorsListResponseModel.class));

        step("Проверить ответ", () -> {
        });

//        given()
//                .log().uri()
//                .get("/unknown")
//                .then()
//                .log().status()
//                .log().body()
//                .statusCode(200)
//                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }

    @Test
    @DisplayName("Регистрация пользователя без пароля")
    void registrationWithoutPasswordTest() {
        LoginRequestBodyModel authData = new LoginRequestBodyModel();

        step("Создать запрос", () -> {
            authData.setEmail("eve.holt@reqres.in");
        });

        ErrorMassageModel response = step("Создать запрос", () ->
                given(RequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(bedRequestResponseSpec)
                        .extract().as(ErrorMassageModel.class));

        step("Проверить ответ", () ->
                assertThat("Missing password").isEqualTo(response.getError()));
    }


    @Test
    @DisplayName("Изменение данных пользователя. Метод PATCH")
    void updateUsersInfoTest() {

        UserUpdateRequestModel request = new UserUpdateRequestModel();

        step("Создать запрос", () -> {
            request.setName(getName());
            request.setJob(getJob());
        });

        UserUpdateResponseModel response = step("Выполнить запрос", () ->
                given(RequestSpec)
                        .body(request)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(okResponseSpec)
                        .extract().as(UserUpdateResponseModel.class));

        step("Проверить ответ", () -> {
            assertThat(request.getJob()).isEqualTo(response.getJob());
            assertThat(request.getName()).isEqualTo(response.getName());
            assertThat(response.getUpdatedAt()).isInSameDayAs(new Date());
        });
    }

}
