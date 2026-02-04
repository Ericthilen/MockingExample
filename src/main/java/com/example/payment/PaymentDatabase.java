package com.example.payment;

public interface PaymentDatabase {
    void savePayment(double amount, String status);
}
