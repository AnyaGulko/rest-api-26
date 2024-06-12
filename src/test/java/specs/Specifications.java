package specs;

import helpers.CustomAllureListener;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.*;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specifications {
    public static RequestSpecification RequestSpec = with()
            .filter(CustomAllureListener.withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static ResponseSpecification okResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification userCreatingResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification bedRequestResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();
}
