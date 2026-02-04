package com.example.payment;

public interface PaymentApiClient {
    PaymentApiResponse charge(String apiKey, double amount);
}
