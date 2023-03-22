package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromCard = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $(".notification__content");
    private SelenideElement errorMessageField = $("[data-test-id= 'from']  .input__sub");

    public void transferOperation(int moneyAmount, DataHelper.CardNumber card) {
        amountField.setValue(String.valueOf(moneyAmount));
        fromCard.setValue(card.getNumber());
        transferButton.click();
    }

    public void moreThanDepositError() {
        errorMessage.shouldHave(Condition.text("Недостаточно средств"), Duration.ofSeconds(15)).
                shouldBe(Condition.visible);
    }

    public void transferBetweenOneCardError() {
        errorMessageField.shouldBe(Condition.visible, Condition.text("неверный номер карты"));
    }
}
