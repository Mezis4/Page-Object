package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataHelper.getCardNumber;
import static ru.netology.data.DataHelper.getOtherCardNumber;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Order(1)
    @Test
    @DisplayName("shouldTransferMoneyFromFirstToSecondCard")
    void shouldTransferMoneyFromFirstCard() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var secondCardBalance = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        var moneyAmount = DataHelper.getMoneyAmount(firstCardBalance);
        var transferPage = dashboardPage.transferToSecondCard();
        transferPage.transferOperation(moneyAmount, getCardNumber());
        var actualBalanceFirst = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var actualBalanceSecond = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        Assertions.assertEquals(firstCardBalance - moneyAmount, actualBalanceFirst);
        Assertions.assertEquals(secondCardBalance + moneyAmount, actualBalanceSecond);
    }

    @Order(2)
    @Test
    @DisplayName("shouldTransferMoneyFromSecondToFirstCard")
    void shouldTransferMoneyFromSecondCard() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var secondCardBalance = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        var moneyAmount = DataHelper.getMoneyAmount(secondCardBalance);
        var transferPage = dashboardPage.transferToFirstCard();
        transferPage.transferOperation(moneyAmount, getOtherCardNumber());
        var actualBalanceFirst = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var actualBalanceSecond = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        Assertions.assertEquals(firstCardBalance + moneyAmount, actualBalanceFirst);
        Assertions.assertEquals(secondCardBalance - moneyAmount, actualBalanceSecond);
    }

    @Order(5)
    @Test
    @DisplayName("shouldShowErrorIfTransferAmountMoreThanDeposit")
    void shouldShowErrorIfTransferAmountMoreThanDeposit() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var secondCardBalance = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        var moneyAmount = DataHelper.getMoneyAmount(secondCardBalance) * 1000;
        var transferPage = dashboardPage.transferToFirstCard();
        transferPage.transferOperation(moneyAmount, getOtherCardNumber());
        transferPage.moreThanDepositError();
        var actualBalanceFirst = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var actualBalanceSecond = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        Assertions.assertEquals(firstCardBalance, actualBalanceFirst);
        Assertions.assertEquals(secondCardBalance, actualBalanceSecond);
    }

    @Order(3)
    @Test
    @DisplayName("shouldShowErrorIfTransferFirstToFirstCard")
    void shouldShowErrorIfTransferFirstToFirstCard() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(getCardNumber().getNumber());
        var moneyAmount = DataHelper.getMoneyAmount(firstCardBalance);
        var transferPage = dashboardPage.transferToFirstCard();
        transferPage.transferOperation(moneyAmount, getCardNumber());
        transferPage.transferBetweenOneCardError();
        var actualBalanceFirst = dashboardPage.getCardBalance(getCardNumber().getNumber());
        Assertions.assertEquals(firstCardBalance, actualBalanceFirst);
    }

    @Order(4)
    @Test
    @DisplayName("shouldShowErrorIfTransferSecondToSecondCard")
    void shouldShowErrorIfTransferSecondToSecondCard() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var secondCardBalance = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        var moneyAmount = DataHelper.getMoneyAmount(secondCardBalance);
        var transferPage = dashboardPage.transferToSecondCard();
        transferPage.transferOperation(moneyAmount, getCardNumber());
        transferPage.transferBetweenOneCardError();
        var actualBalanceSecond = dashboardPage.getCardBalance(getOtherCardNumber().getNumber());
        Assertions.assertEquals(secondCardBalance, actualBalanceSecond);
    }
}
