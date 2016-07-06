package lv.ctco;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class StudentControllerTest {

    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();

    @Test
    public void testGetOK() {
        get("/students").then().statusCode(OK);
    }

    @Test
    public void testGetNotFound() {
        get("/something").then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetByIDOK() {
        get("/students/0").then().body("name", equalTo("Name"));
    }

    @Test
    public void testGetByIDNotFound() {
        get("/students/1").then().statusCode(NOT_FOUND);
    }

    @Test
    public void testDeleteByIDNotFound() {
        delete("/students/1").then().statusCode(NOT_FOUND);
    }

    @Test
    public void testDeleteByIDOK() {
        delete("/students/0").then().statusCode(OK);
    }

    @Test
    public void testPostOK() {
        Student student = new Student();
        student.setName("John");
        student.setName("Snow");
        given().body(student).when().post("/students").then().statusCode(CREATED);
    }

}