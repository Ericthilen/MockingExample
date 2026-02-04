package com.example.payment;

public interface PaymentEmailService {
    void sendPaymentConfirmation(String email, double amount);
}
