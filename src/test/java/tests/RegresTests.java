package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static utils.RandomUtils.getJob;
import static utils.RandomUtils.getName;

public class RegresTests {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    @DisplayName("Успешная авторизация за пользователя")
    void successfulLoginTest() {
        String authData = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .body(authData)
                .contentType(JSON)
                .when()
                .log().uri()
                .post("api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    void createUserTest() {
        String name = getName();
        String job = getJob();
        String authData = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\"" +
                "}";

        given()
                .body(authData)
                .contentType(JSON)
                .when()
                .log().uri()
                .post("api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Получение списка цветов")
    void getColorsListTest() {
        given()
                .log().uri()
                .get("api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }

    @Test
    @DisplayName("Регистрация пользователя без пароля")
    void registrationWithoutPasswordTest() {
        String email = "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}";
        String errorMassage = "{\"error\":\"Missing password\"}";

        given()
                .body(email)
                .contentType(JSON)
                .log().uri()
                .post("api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body(is(errorMassage));
    }

    @Test
    @DisplayName("Изменение данных пользователя. Метод PATCH")
    void updateUsersInfoTest() {
        String updateData = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        given()
                .body(updateData)
                .contentType(JSON)
                .log().body()
                .log().uri()
                .patch("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

}
