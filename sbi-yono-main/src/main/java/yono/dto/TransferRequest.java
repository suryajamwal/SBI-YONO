package yono.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {

    @NotBlank(message = "Receiver account number is required")
    private String receiverAccountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", inclusive = true, message = "Transfer amount must be greater than 0")
    private BigDecimal amount;

    public TransferRequest() {
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}