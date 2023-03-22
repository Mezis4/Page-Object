package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardNumber {
        private String number;
    }

    public static CardNumber getCardNumber() {
        return new CardNumber("5559 0000 0000 0001");
    }

    public static CardNumber getOtherCardNumber() {
        return new CardNumber("5559 0000 0000 0002");
    }

    public static int getMoneyAmount(int balance) {
        return new Random().nextInt(balance);
    }
}
