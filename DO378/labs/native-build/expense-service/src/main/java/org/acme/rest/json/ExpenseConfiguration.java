package org.acme.rest.json;

import java.math.BigDecimal;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "expense")
public interface ExpenseConfiguration {
    BigDecimal getMaxSingleExpenseAmount();
    BigDecimal getMaxMonthlyExpenseAmount();

    @ConfigProperty(defaultValue = "Expense amount is larger than the limit (%s)")
    String getSingleLimitErrorMsg();

    @ConfigProperty(
        defaultValue = "If expense is accepted, it would exceed the monthly expense limit (%s)")
    String getMonthlyLimitErrorMsg();
}