package com.example.groundhopping_mobile.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClass {
    private static String url = "https://rooruzlxaj.execute-api.eu-west-1.amazonaws.com/prod/";
    private String api_key = null;
    private static JsonNode auth = null;
    private static JsonNode resp = null;
    private static ObjectMapper mapper = new ObjectMapper();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public static String setOkHttpRequest(String url, RequestBody formBody, String type, String token) {
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        System.out.println("Request is send");

        if (type.equals("POST")) {
            if (formBody != null) {
                if (token != null) {
                    System.out.println("Token: " + token);
                    token = token.replace("\"", "");
                    request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", token)
                            .post(formBody)
                            .build();
                    System.out.println("request body: " + request.body() + " header: " + request.headers());
                    System.out.println("formbod: " + formBody);
                } else {
                    System.out.println("send without token");
                    request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    System.out.println(request);
                }
            } else {
                if (token != null){
                    token = token.replace("\"", "");
                    request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", token)
                            .build();
                }else {
                    request = new Request.Builder()
                            .url(url)
                            .build();
                }
            }
        } else if (type.equals("GET")) {
            if(formBody == null){
                if (token != null){
                    token = token.replace("\"", "");
                    request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", token)
                            .build();
                } else {
                    request = new Request.Builder()
                            .url(url)
                            .build();
                }
            }else{
                if (token != null) {
                    token = token.replace("\"", "");
                    request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", token)
                            .post(formBody)
                            .build();
                } else {
                    request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                }

            }
        } else if (type.equals("PATCH")) {
            if (formBody != null) {
                if (token != null) {
                    token = token.replace("\"", "");
                    request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", token)
                            .patch(formBody)
                            .build();
                } else {
                    request = new Request.Builder()
                            .url(url)
                            .patch(formBody)
                            .build();
                }
            } else {
                if (token != null) {
                    token = token.replace("\"", "");
                    request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", token)
                            .method("PATCH", null)
                            .build();
                } else {
                    request = new Request.Builder()
                            .url(url)
                            .method("PATCH", null)
                            .build();
                }
            }
        } else if (type.equals("PUT")) {
            if (token != null) {
                token = token.replace("\"", "");
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", token)
                        .put(formBody)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .put(formBody)
                        .build();
            }
        } else if (type.equals("DELETE")) {
            if (token != null) {
                token = token.replace("\"", "");
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", token)
                        .delete()
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .delete()
                        .build();
            }
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("notok");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                resp = mapper.readTree(response.body().string());
            }
        });
        return null;
    }

    public static void connection(String username, String password) throws JSONException {
        String uri = url + "login";
        RequestBody formBody;
        JSONObject user_auth = new JSONObject();
        user_auth.put("username", username);
        user_auth.put("password", password);
        formBody = RequestBody.create(JSON,user_auth.toString());
        System.out.println("formbody: " + formBody);
        setOkHttpRequest(uri, formBody, "POST", null);
    }

    public static String register(String username, String password, String email, String birthdate) throws JSONException {
        String uri = url + "register";
        RequestBody formBody;
        JSONObject user_info = new JSONObject();
        user_info.put("username", username);
        user_info.put("password", password);
        user_info.put("email", email);
        user_info.put("birthdate", birthdate);
        formBody = RequestBody.create(JSON,user_info.toString());
        String response = setOkHttpRequest(uri, formBody, "POST", null);
        return response;
    }

    public static void getUserByName(String username, String token){
        token = token.replace("\"", "");
        username = username.replace("\"", "");
        String uri = url + "users/getuser?username=" + username;
        System.out.println(uri);
        setOkHttpRequest(uri, null, "GET", token);
    }

    public static void getUservehicules(String username, String token, String id){
        token = token.replace("\"", "");
        username = username.replace("\"", "");
        String uri = url + "users/get-user-car?username=" + username + "&userID=" + id;
        System.out.println(uri);
        setOkHttpRequest(uri, null, "GET", token);
    }

    public static void getwinner(String homeTeam, String awayTeam) throws JSONException {
        String uri = url + "contests/get-winner";
        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("home", Integer.valueOf(homeTeam));
        body.put("away", Integer.valueOf(awayTeam));
        formBody = RequestBody.create(JSON,body.toString());
        setOkHttpRequest(uri, formBody, "POST", null);
    }

    public static void getContests(){
        String uri = url + "contests/getcontests";
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void getTeams(){
        String uri = url + "teams/get-teams";
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void getTeam(String id){
        String uri = url + "teams/get-team-by-id?team=" + id;
        System.out.println(uri);
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void getStadium(String id){
        String uri = url + "stadiums/get-stadium-by-id?stadium=" + id;
        System.out.println(uri);
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void getStadiums(){
        String uri = url + "stadiums/getstadiums";
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void addUserStadium(String stadiumID, String userID, String username, String token, Integer note) throws JSONException {
        username = username.replace("\"", "");
        String uri = url + "users/addstadium?username=" + username;
        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("userID", Integer.valueOf(userID));
        body.put("stadiumID", Integer.valueOf(stadiumID));
        body.put("note", note);
        formBody = RequestBody.create(JSON,body.toString());
        setOkHttpRequest(uri, formBody, "POST", token);
    }

    public static void addUserContest(String contestID, String userID, String username, String token) throws JSONException {
        username = username.replace("\"", "");
        String uri = url + "users/addcontest?username=" + username;
        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("userID", Integer.valueOf(userID));
        body.put("ContestId", Integer.valueOf(contestID));
        formBody = RequestBody.create(JSON,body.toString());
        setOkHttpRequest(uri, formBody, "POST", token);
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String getApi_key() {
        return api_key;
    }

    public JsonNode getAuth() {
        return auth;
    }

    public void setAuth(JsonNode jsonNode){
        auth = jsonNode;
    }

    public JsonNode getResp() {
        return resp;
    }

    public void resetResp() { resp = null; }
}
