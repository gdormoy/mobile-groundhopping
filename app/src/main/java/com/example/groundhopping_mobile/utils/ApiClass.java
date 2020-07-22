package com.example.groundhopping_mobile.utils;
import com.example.groundhopping_mobile.data.model.Stadium;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
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
        user_info.put("role", "user");
        formBody = RequestBody.create(JSON,user_info.toString());
        System.out.println("URL: " + uri + " body: " + user_info);
        String response = setOkHttpRequest(uri, formBody, "POST", null);
        return response;
    }

    public static void getUserByName(String username, String token){
        token = token.replace("\"", "");
        username = username.replace("\"", "");
        String uri = url + "users/getuser?username=" + username;
        setOkHttpRequest(uri, null, "GET", token);
    }

    public static void getUservehicules(String username, String token, String id){
        token = token.replace("\"", "");
        username = username.replace("\"", "");
        String uri = url + "users/get-user-car?username=" + username + "&userID=" + id;
        setOkHttpRequest(uri, null, "GET", token);
    }

    public static void getwinner(String homeTeam, String awayTeam) throws JSONException {
        String uri = url + "contests/get-winner";
        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("home", homeTeam);
        body.put("away", awayTeam);
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
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void getStadium(String id){
        String uri = url + "stadiums/get-stadium-by-id?stadium=" + id;
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

    public static void getUserStadium(String username, String id, String token){
        username = username.replace("\"", "");
        id = id.replace("\"", "");
        token = token.replace("\"", "");

        String uri = url + "users/getstadiums?username=" + username + "&userID=" + id;
        setOkHttpRequest(uri, null, "GET", token);
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

    public static void addUserbet(String contestID, String username, String ts1, String ts2, String money, String token) throws JSONException {
        username = username.replace("\"", "");
        String uri = url + "users/add-bet?username=" + username;
        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("contestId", Integer.valueOf(contestID));
        body.put("ts1", Integer.valueOf(ts1));
        body.put("ts2", Integer.valueOf(ts2));
        body.put("money", Integer.valueOf(money));
        formBody = RequestBody.create(JSON,body.toString());
        setOkHttpRequest(uri, formBody, "POST", token);
    }

    public static void getCarpoolingoffer() {
        String uri = url + "car-pooling/get-carpooling-offers";
        setOkHttpRequest(uri, null, "GET", null);
    }

    public static void addCarpoolingoffer(String username, String token, String userID, String contestID, String address, String godate, String backdate, String price) throws JSONException {
        username = username.replace("\"", "");
        token = token.replace("\"", "");
        String uri = url + "car-pooling/add-car-polling-offer/?username=" + username;
        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("userID", Integer.valueOf(userID));
        body.put("ContestID", Integer.valueOf(contestID));
        body.put("AddressFrom", address);
        body.put("GoDate", godate);
        body.put("BackDate", backdate);
        body.put("Price", Integer.valueOf(price));
        formBody = RequestBody.create(JSON,body.toString());
        setOkHttpRequest(uri, formBody, "POST", token);
    }

    public static void getUserContest(String username, String id, String token){
        username = username.replace("\"", "");
        id = id.replace("\"", "");
        token = token.replace("\"", "");

        String uri = url + "users/getcontests?username=" + username + "&userID=" + id;
        setOkHttpRequest(uri, null, "GET", token);
    }

    public static void removeUserStadium(String username, String userid, String stadiumid, String token) throws JSONException {
        username = username.replace("\"", "");
        userid = userid.replace("\"", "");
        stadiumid = stadiumid.replace("\"", "");
        token = token.replace("\"", "");
        String uri = url + "users/delete-user-stadium?username=" + username;

        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("userID", Integer.valueOf(userid));
        body.put("stadiumID", Integer.valueOf(stadiumid));
        formBody = RequestBody.create(JSON,body.toString());
        System.out.println(uri);
        setOkHttpRequest(uri, formBody, "DELETE", token);
    }

    public static void removeUserContest(String username, String userid, String contestID, String token) throws JSONException {
        username = username.replace("\"", "");
        userid = userid.replace("\"", "");
        contestID = contestID.replace("\"", "");
        token = token.replace("\"", "");
        String uri = url + "users/delete-user-contest?username=" + username;

        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("userID", Integer.valueOf(userid));
        body.put("contestID", Integer.valueOf(contestID));
        formBody = RequestBody.create(JSON,body.toString());
        System.out.println(uri);
        setOkHttpRequest(uri, formBody, "DELETE", token);
    }

    public static void deleteUser(String username, String token) {
        username = username.replace("\"", "");
        token = token.replace("\"", "");
        String uri = url + "users/delete-user?username=" + username;
        setOkHttpRequest(uri, null, "DELETE", token);
    }

    public static void getRequiredFuel(String consumption, String type, ArrayList<Stadium> list, JSONObject origine) throws JSONException {
        String uri = url + "get-required-fuel";
        JSONArray waypoints = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject destination = new JSONObject();
            System.out.println("List: {latitude: " + list.get(i).getLatitude() + ", longitude: " + list.get(i).getLongitude() + "}");
            destination.put("latitude", list.get(i).getLatitude());
            destination.put("longitude", list.get(i).getLongitude());
            waypoints.put(destination);
            System.out.println(waypoints);
        }


        RequestBody formBody;
        JSONObject body = new JSONObject();
        body.put("consommation", consumption);
        body.put("type", type);
        body.put("origine", origine);
        body.put("destinations", waypoints);
        System.out.println(body);
        formBody = RequestBody.create(JSON,body.toString());
        setOkHttpRequest(uri, formBody, "POST", null);
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
