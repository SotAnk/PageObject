package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import ru.netology.web.paga.DashboardPage;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static Card getFirstCardInfo() {
        val dashboard = new DashboardPage();
        return new Card("5559 0000 0000 0001", dashboard.getCardBalance("01"));
    }

    public static Card getSecondCardInfo() {
        val dashboard = new DashboardPage();
        return new Card("5559 0000 0000 0002", dashboard.getCardBalance("02"));
    }

    public static Card getWrongCardInfo() {
        val dashboard = new DashboardPage();
        return new Card("5559 0000 0000 0003", 10000);
    }

    public static int cardBalanceAfterSendMoney(int balance, int amount) {
        int total = balance - amount;
        return total;
    }

    public static int cardBalanceAfterGetMoney(int balance, int amount) {
        int total = balance + amount;
        return total;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class Card {
        String number;
        int balance;
    }
}






