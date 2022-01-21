package ru.netology.web.paga;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection refillButton = $$("[data-test-id='action-deposit']");


    public TransferPage firstCardRefillButtonClick() {
        refillButton.first().click();
        return new TransferPage();
    }

    public TransferPage secondCardRefillButtonClick() {
        refillButton.last().click();
        return new TransferPage();
    }

    public int getFirstCardBalance() {
        return getCardBalance("01");
    }

    public int getSecondCardBalance() {
        return getCardBalance("02");
    }

    public int getCardBalance(String id) {
        val text = cards.findBy(Condition.text(id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public String returnToDashboard() {
        val text = $("[data-test-id='dashboard']").text();
        return text;
    }

    public void showDashboardMessage() {
        $("[data-test-id='dashboard']").shouldHave(Condition.exactText("Личный кабинет"));
    }
}