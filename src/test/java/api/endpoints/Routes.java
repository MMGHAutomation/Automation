package api.endpoints;
/*
* https://petstore.swagger.io/#/user
* https://petstore.swagger.io/#/user/{username}
* https://petstore.swagger.io/#/user/{username}
* https://petstore.swagger.io/#/user/{username}
* */

public class Routes {
public static String api_base_Url ="https://petstore.swagger.io/v2";

// User module
    public static String createUserURL = api_base_Url+"/user";
    public static String getUserURL = api_base_Url+"/user/{username}";
    public static String updateUserURL = api_base_Url+"/user/{username}";
    public static String deleteUserURL = api_base_Url+"/user/{username}";

}
