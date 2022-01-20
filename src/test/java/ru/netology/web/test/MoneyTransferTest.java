package ru.netology.web.test;

import lombok.val;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.paga.LoginPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        int amount = 100 + (int) (Math.random() * 5000);
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardBalanceFirst = dashboard.getFirstCardBalance();
        val cardBalanceSecond = dashboard.getSecondCardBalance();
        val cardInfo = DataHelper.Card.getSecondCardInfo();
        val transferMoney = dashboard.firstCardRefillButtonClick();
        transferMoney.transfer(cardInfo, amount);
        val cardBalanceAfterSendFirst = DataHelper.Card.cardBalanceAfterGetMoney(cardBalanceFirst, amount);
        val cardBalanceAfterSendSecond = DataHelper.Card.cardBalanceAfterSendMoney(cardBalanceSecond, amount);
        assertEquals(cardBalanceAfterSendFirst, dashboard.getFirstCardBalance());
        assertEquals(cardBalanceAfterSendSecond, dashboard.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        int amount = 100 + (int) (Math.random() * 5000);
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardBalanceFirst = dashboard.getFirstCardBalance();
        val cardBalanceSecond = dashboard.getSecondCardBalance();
        val cardInfo = DataHelper.Card.getFirstCardInfo();
        val transferMoney = dashboard.secondCardRefillButtonClick();
        transferMoney.transfer(cardInfo, amount);
        val cardBalanceAfterSendFirst = DataHelper.Card.cardBalanceAfterSendMoney(cardBalanceFirst, amount);
        val cardBalanceAfterSendSecond = DataHelper.Card.cardBalanceAfterGetMoney(cardBalanceSecond, amount);
        assertEquals(cardBalanceAfterSendFirst, dashboard.getFirstCardBalance());
        assertEquals(cardBalanceAfterSendSecond, dashboard.getSecondCardBalance());
    }

    @Test
    void shouldReturnToTheDashboardPageIfClickCancelButton() {
        int amount = 100 + (int) (Math.random() * 5000);
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardInfo = DataHelper.Card.getFirstCardInfo();
        val transferMoney = dashboard.firstCardRefillButtonClick();
        transferMoney.transferCancel(cardInfo, amount);
        dashboard.showDashboardMessage();
    }

    @Test
    void shouldShowErrorMessageIfWrongCardNumber() {
        int amount = 100 + (int) (Math.random() * 5000);
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardInfo = DataHelper.Card.getWrongCardInfo();
        val transferMoney = dashboard.firstCardRefillButtonClick();
        transferMoney.transfer(cardInfo, amount);
        transferMoney.showAlertMessage();
    }

    @Test
    void shouldReturnAlertMessageIfSendMoreThanHave() {
        int amount = 15000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val cardInfo = DataHelper.Card.getSecondCardInfo();
        val transferMoney = dashboard.firstCardRefillButtonClick();
        transferMoney.transfer(cardInfo, amount);
        transferMoney.showErrorMessage();
    }
}