package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.devtools.v114.fetch.model.AuthChallengeResponse;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
public class UserEndpoint {

   public static Response createUser(User user){
      Response res= given()
              .contentType(ContentType.JSON)
              .accept(ContentType.JSON)
              .body(user)
        .when()
               .post(Routes.createUserURL);
      return res;
    }

    public static Response getUser(String userName){
        Response res= given()
                .pathParams("username", userName)
                .when()
                .get(Routes.getUserURL);
        return res;
    }
    public static Response updateUser(User user, String userName){
        Response res= given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParams("username", userName)
                .body(user)
                .when()
                .put(Routes.updateUserURL);
        return res;
    }

    public static Response deleteUser(String userName){
        Response res= given()
                .pathParams("username", userName)
                .when()
                .delete(Routes.deleteUserURL);
        return res;
    }
}
