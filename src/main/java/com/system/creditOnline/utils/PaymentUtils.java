package com.system.creditOnline.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PaymentUtils {

    public static long calculateTerm(LocalDate loanDate, LocalDate currentDate) {
        return ChronoUnit.DAYS.between(loanDate, currentDate);
    }

    public static double calculateInterest(double amount, long term, double interestRate, int businessYearDays) {
        return (amount * term * interestRate) / businessYearDays;
    }

    public static double calculateVat(double interest, double vatRate) {
        return interest * vatRate;
    }

    public static double calculateTotalPayment(double amount, double interest, double vat) {
        return amount + interest + vat;
    }
}

