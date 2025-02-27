package src;

import java.time.LocalDate;

public class Payment {
    private final String paymentId;

    private final double amount;

    private final PaymentMethod paymentMethod;

    private final LocalDate paymentDate;

    // Enum for different payment methods
    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, PAYPAL, CASH
    }

    // Constructor
    public Payment(final String paymentId, final double amount, final PaymentMethod paymentMethod, final LocalDate paymentDate) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }


    public double getAmount() {
        return amount;
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
    }


    // Overriding toString() for structured information display
    @Override
    public String toString() {
        return "Payment ID: " + paymentId + "\n" + "Amount: $" + amount + "\n" + "Method: " + paymentMethod + "\n" + "Date: " + paymentDate.toString();
    }
}