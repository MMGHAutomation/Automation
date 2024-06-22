package api.apiTests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.StaticThreadLocal;

import java.util.HashMap;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class HTTPRequests {

	int id ;
	@BeforeClass(alwaysRun = true)
	public void initialize(){
	}

	@Test(priority = 1, testName = "apis : get all users",
			suiteName = "Home", groups = {"apis"},
			description="description test")
	public void getUsers(){
		given()
				.when()
				      .get("https://reqres.in/api/users?page=2")
				.then()
				      .statusCode(200)
				      .body("page", equalTo(2)).log().all();

	}

	@Test(priority = 2, testName = "apis : Create user",
			suiteName = "Home", groups = {"apis"},
			description="description test")
	public void createUsers(){
		HashMap data = new HashMap();
		data.put("name","mina");
		data.put("job","leader");
		id = given()
				.contentType("application/json").body(data)
			.when()
				.post("https://reqres.in/api/users").jsonPath().getInt("id");
				//.then()
				//.statusCode(201)
				//.log().all();

	}

	@Test(priority = 3, testName = "apis : update user", dependsOnMethods = "createUsers",
			suiteName = "Home", groups = {"apis"},
			description="description test")
	public void updateUsers(){
		HashMap data = new HashMap();
		data.put("name","mina mounir");
		data.put("job","trainer");
		given()
				.contentType("application/json").body(data)
				.pathParams("userID",id)
		.when()
				.put("https://reqres.in/api/users/{userID}")
		.then()
		      .statusCode(200)
		      .log().all();

	}



}
