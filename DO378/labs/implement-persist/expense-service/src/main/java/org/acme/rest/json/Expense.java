package org.acme.rest.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;

public class Expense {

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    public UUID uuid;
    public String name;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime creationDate;
    public PaymentMethod paymentMethod;
    public BigDecimal amount;

    public Expense(UUID uuid, String name, LocalDateTime creationDate, PaymentMethod paymentMethod, String amount) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal(amount);
    }

    public Expense(String name, PaymentMethod paymentMethod, String amount) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount);
    }

    @JsonbCreator
    public static Expense of(String name, PaymentMethod paymentMethod, String amount) {
        return new Expense(name, paymentMethod, amount);
    }

}