package api.apiTests;

import api.endpoints.UserEndpoint;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.Helper;

public class UserTests extends ApiTestBase{
    User user;


    Faker faker;
    @BeforeClass
    public void setup(){
        faker = new Faker();
        user = new User();
        user.setId(faker.idNumber().hashCode());
        user.setUsername(faker.name().username());
        System.out.println("username-----> "+faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().safeEmailAddress());
        user.setPassword(faker.internet().password(5,10));
        user.setPhone(faker.phoneNumber().cellPhone());
        log = LogManager.getLogger(this.getClass());

    }

    @Test(priority = 1, testName = "Home : Check fashion",
            suiteName = "Home", groups = {"Home"},
            description="description test")
    public void createUserTest(){
       log.error("post, Create user");
       Response res =  UserEndpoint.createUser(user);
      // res.then().log().all();
       Assert.assertEquals(res.statusCode(),200);
       log.info(" user created");

    }
    @Test(priority = 2, testName = "Home : Check fashion",
            suiteName = "Home", groups = {"Home"},
            description="description test")
    public void getUserTest(){
        log.info(" get user ");
     //   System.out.println("getuser.username-----> "+this.user.getUsername());
        log.info("call  post method ");
        Response res =  UserEndpoint.getUser(this.user.getUsername());
        log.info("print log");
      //  res.then().log().all();
        log.info("Assert status code ");
        Assert.assertEquals(res.statusCode(),200);

    }
    @Test(priority = 3, testName = "Home : Check fashion",
            suiteName = "Home", groups = {"Home"},
            description="description test")
    public void updateUserTest(){
        log.info("  put method ");
       // System.out.println("getuser.username-----> "+this.user.getUsername());
        log.info("Update data ");
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().safeEmailAddress());

        Response res =  UserEndpoint.updateUser(user, this.user.getUsername());
      //  res.then().log().all();
        Assert.assertEquals(res.statusCode(),200);
    }
    @Test(priority = 4, testName = "Home : Check fashion",
            suiteName = "Home", groups = {"Home"},
            description="description test")
    public void deletUserTest(){
      //  System.out.println("getuser.username-----> "+this.user.getUsername());

        Response res =  UserEndpoint.deleteUser(this.user.getUsername());
     //   res.then().log().all();
        Assert.assertEquals(res.statusCode(),200);
    }
}
