package com.example.groundhopping_mobile.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String email;
    private String birthDate;
    private Float money;
    private String token;

    public LoggedInUser(String userId, String displayName, String email, String birthDate, Float money, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.birthDate = birthDate;
        this.money = money;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserEmail() {
        return email;
    }

    public String getUserBirthDate() {
        return birthDate;
    }

    public Float getUserMoney() {
        return money;
    }

    public String getUserToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", money=" + money +
                ", token='" + token + '\'' +
                '}';
    }
}