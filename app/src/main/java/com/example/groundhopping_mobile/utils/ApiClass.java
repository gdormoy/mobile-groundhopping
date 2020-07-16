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
import okhttp3.FormBody;
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

    public static String setOkHttpRequest(String url, RequestBody formBody, String type) {
        OkHttpClient client = new OkHttpClient();
        Request request = null;

        if (type.equals("POST")) {
            if (formBody != null) {
                System.out.println(formBody);
                request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                System.out.println("Request: " + request.toString());
            } else {
                request = new Request.Builder()
                        .url(url)
                        .build();
            }
        } else if (type.equals("GET")) {
            if(formBody == null){
                request = new Request.Builder()
                        .url(url)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
            }
        } else if (type.equals("PATCH")) {
            if (formBody != null) {
                request = new Request.Builder()
                        .url(url)
                        .patch(formBody)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .method("PATCH", null)
                        .build();
            }
        } else if (type.equals("PUT")) {
            request = new Request.Builder()
                    .url(url)
                    .put(formBody)
                    .build();
        } else if (type.equals("DELETE")) {
            request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("notok");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("response: " + response);
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
        setOkHttpRequest(uri, formBody, "POST");
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
        String response = setOkHttpRequest(uri, formBody, "POST");
        return response;
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
}
