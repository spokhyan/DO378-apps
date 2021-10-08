package org.acme.rest.json;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.acme.rest.json.Expense.PaymentMethod;

@ApplicationScoped
public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();

    @PostConstruct
    void initData() {
        expenses.add(new Expense("Groceries", PaymentMethod.CASH, "150.50"));
        expenses.add(new Expense("Civilization VI", PaymentMethod.DEBIT_CARD, "25.00"));
    }

    public List<Expense> list() {
        return expenses;
    }

    public Expense create(Expense expense) {
        expenses.add(expense);
        return expense;
    }

    public boolean delete(UUID uuid) {
        return expenses.removeIf(expense -> expense.uuid.equals(uuid));
    }

    public void update(Expense expense) {
        delete(expense.uuid);
        create(expense);
    }

    public boolean exists(UUID uuid) {
        return expenses.stream().anyMatch(exp -> exp.uuid.equals(uuid));
    }
}