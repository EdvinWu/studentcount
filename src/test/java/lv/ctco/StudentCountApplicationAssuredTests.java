package lv.ctco;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
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
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	public void testGetOK() {
		Student student = new Student();
		student.setName("John");
		student.setSurname("Snow");
		Headers header = given().contentType("application/json").body(student).when().post("/students").getHeaders();
		get(header.getValue("Location")).then().statusCode(OK);
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

		Headers header = given().contentType("application/json").body(student).when().post("/students").getHeaders();
		get(header.getValue("Location")).then().body("name", equalTo("John"));
	}

	@Test
	public void testGetByIDNotFound() {
		get("/students/-1").then().statusCode(NOT_FOUND);
	}

	@Test
	public void testDeleteByIDNotFound() {
		delete("/students/-1").then().statusCode(NOT_FOUND);
	}

	@Test
	public void testDeleteByIDOK() {
        Student student = new Student();
        student.setName("John");
		student.setSurname("Snow");
		Headers header = given().contentType("application/json").body(student).when().post("/students").getHeaders();
		delete(header.getValue("Location")).then().statusCode(OK);
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


		Headers header = given().contentType("application/json").body(student)
				.when().post("/students/").getHeaders();

		student.setName("Joe");
		student.setSurname("XO");
		given().contentType("application/json").body(student)
				.when().put(header.getValue("Location"))
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
