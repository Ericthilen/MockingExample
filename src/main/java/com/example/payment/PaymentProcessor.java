package com.example.payment;

public class PaymentProcessor {
    private final String apiKey;
    private final PaymentApiClient paymentApiClient;
    private final PaymentDatabase paymentDatabase;
    private final PaymentEmailService emailService;

    public PaymentProcessor(String apiKey, PaymentApiClient paymentApiClient, PaymentDatabase paymentDatabase, PaymentEmailService emailService) {
        this.apiKey = apiKey;
        this.paymentApiClient = paymentApiClient;
        this.paymentDatabase = paymentDatabase;
        this.emailService = emailService;
    }

    public boolean processPayment(double amount) {
        PaymentApiResponse response = paymentApiClient.charge(apiKey, amount);

        if (response.isSuccess()) {
            paymentDatabase.savePayment(amount, "SUCCESS");
            emailService.sendPaymentConfirmation("user@example.com", amount);
            return true;
        } else {
            paymentDatabase.savePayment(amount, "FAILED");
            return false;
        }
    }
}
