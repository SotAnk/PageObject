package ru.netology.web.paga;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement sumField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement cardField = $("[data-test-id='to'] input");
    private SelenideElement sendButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    public void transfer(DataHelper.Card card, int amount) {
        sumField.setValue(String.valueOf(amount));
        fromField.setValue(card.getNumber());
        sendButton.click();
    }

    public void transferCancel(DataHelper.Card card, int amount) {
        sumField.setValue(String.valueOf(amount));
        fromField.setValue(card.getNumber());
        cancelButton.click();
    }

    public SelenideElement showAlertMessage() {
        return $(withText("Ошибка!")).shouldHave(Condition.visible);
    }

    public SelenideElement showErrorMessage() {
        return $(withText("У вас недостаточно средств для перевода такой суммы")).shouldBe(Condition.visible);
    }
}