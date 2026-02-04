package com.example.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentProcessorTest {

    private PaymentApiClient paymentApiClient;
    private PaymentDatabase paymentDatabase;
    private PaymentEmailService emailService;
    private PaymentProcessor paymentProcessor;

    @BeforeEach
    void setup() {
        paymentApiClient = mock(PaymentApiClient.class);
        paymentDatabase = mock(PaymentDatabase.class);
        emailService = mock(PaymentEmailService.class);

        paymentProcessor = new PaymentProcessor(
                "sk_test_123456",
                paymentApiClient,
                paymentDatabase,
                emailService
        );
    }

    @Test
    void processPayment_success() {
        PaymentApiResponse response = mock(PaymentApiResponse.class);
        when(response.isSuccess()).thenReturn(true);
        when(paymentApiClient.charge("sk_test_123456", 100.0)).thenReturn(response);

        boolean result = paymentProcessor.processPayment(100.0);

        assertThat(result).isTrue();
        verify(paymentDatabase).savePayment(100.0, "SUCCESS");
        verify(emailService).sendPaymentConfirmation("user@example.com", 100.0);
    }

    @Test
    void processPayment_failure() {
        PaymentApiResponse response = mock(PaymentApiResponse.class);
        when(response.isSuccess()).thenReturn(false);
        when(paymentApiClient.charge("sk_test_123456", 100.0)).thenReturn(response);

        boolean result = paymentProcessor.processPayment(100.0);

        assertThat(result).isFalse();
        verify(paymentDatabase).savePayment(100.0, "FAILED");
        verify(emailService, never()).sendPaymentConfirmation(anyString(), anyDouble());
    }
}
