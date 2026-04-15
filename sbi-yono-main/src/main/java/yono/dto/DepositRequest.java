package yono.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DepositRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", inclusive = true, message = "Deposit amount must be greater than 0")
    private BigDecimal amount;

    public DepositRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}