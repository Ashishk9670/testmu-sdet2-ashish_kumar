package models;

public record PaymentDetails(String method, String cardNumber, String expiryMonth, String expiryYear, String cvv) {
}
