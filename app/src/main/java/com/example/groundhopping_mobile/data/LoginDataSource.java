package com.example.groundhopping_mobile.data;

import com.example.groundhopping_mobile.data.model.LoggedInUser;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;

import static com.example.groundhopping_mobile.utils.ApiClass.getSHA;
import static com.example.groundhopping_mobile.utils.ApiClass.toHexString;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

public class LoginDataSource {

    final ApiClass apiClass = new ApiClass();

    public Result<LoggedInUser> login(String username, String password) {
        String hashed_password = "";
        JsonNode res = null;
        try {
             hashed_password = toHexString(getSHA(password));
            System.out.println(hashed_password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            apiClass.resetResp();
            ApiClass.connection(username, hashed_password);
            do {
            } while (apiClass.getResp() == null);
            res = apiClass.getResp();
            apiClass.resetResp();

            System.out.println("res : " + res);
            LoggedInUser User =
                    new LoggedInUser(
                            res.get("body").get("ID").toString(),
                            res.get("body").get("Username").toString(),
                            res.get("body").get("Email").toString(),
                            res.get("body").get("BirthDate").toString(),
                            Float.parseFloat(res.get("body").get("Money").toString()),
                            res.get("body").get("token").toString());
            return new Result.Success<>(User);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public static void logout() {
        System.out.println("Logout");
        // TODO: revoke authentication
    }
}