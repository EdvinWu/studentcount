package lv.ctco;

import io.restassured.RestAssured;
import lv.ctco.student.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StudentCountApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class StudentCountApplicationAssuredTests {
	public static final int OK = HttpStatus.OK.value();
	public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
	public static final int CREATED = HttpStatus.CREATED.value();

	@Before
	public void before(){
		RestAssured.port = 8090;
	}

	@Test
	public void testGetOK() {
		Student student = new Student();
		student.setName("John");
		student.setSurname("Snow");
		given().contentType("application/json").body(student).when().post("/students").then().statusCode(CREATED);
		get("/students").then().statusCode(OK);
	}

	@Test
	public void testGetNotFound() {
		get("/something").then().statusCode(NOT_FOUND);
	}

	@Test
	public void testGetByIDOK() {
		Student student = new Student();
		student.setName("John");
		student.setSurname("Snow");

		given().contentType("application/json").body(student).when().post("/students").then().statusCode(CREATED);
		get("/students/1").then().contentType("application/json").body("name", equalTo("John"));
	}

	@Test
	public void testGetByIDNotFound() {
		get("/students/-1").then().statusCode(NOT_FOUND);
	}

	@Test
	public void testDeleteByIDNotFound() {
		delete("/students/456456").then().statusCode(NOT_FOUND);
	}

	@Test
	public void testDeleteByIDOK() {
        Student student = new Student();
        student.setName("John");
		student.setSurname("Snow");
        given().contentType("application/json").body(student).when().post("/students").then().statusCode(CREATED);
		delete("/students/1").then().statusCode(OK);
	}

	@Test
	public void testPostOK() {
		Student student = new Student();
		student.setName("John");
		student.setSurname("Snow");
		given().contentType("application/json").body(student)
				.when().post("/students")
				.then().statusCode(CREATED);
	}

	@Test
	public void testPutOK() {
		Student student = new Student();
		student.setName("John123");
		student.setSurname("Snow123");


		given().contentType("application/json").body(student)
				.when().post("/students/")
				.then().statusCode(CREATED);

		student.setName("Joe");
		student.setSurname("XO");
		given().contentType("application/json").body(student)
				.when().put("students/1")
				.then().statusCode(OK);
	}

	@Test
	public void testPutFails() {
		Student student = new Student();
		given().contentType("application/json").body(student)
				.when().put("students/-1")
				.then().statusCode(NOT_FOUND);
	}




}
