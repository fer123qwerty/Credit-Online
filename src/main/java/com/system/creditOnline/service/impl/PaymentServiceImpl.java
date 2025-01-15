package com.system.creditOnline.service.impl;

import com.system.creditOnline.dto.PaymentDTO;
import com.system.creditOnline.entity.DebitAccount;
import com.system.creditOnline.entity.Loan;
import com.system.creditOnline.enums.AccountStatus;
import com.system.creditOnline.enums.LoanStatus;
import com.system.creditOnline.repository.DebitAccountRepository;
import com.system.creditOnline.repository.LoanRepository;
import com.system.creditOnline.service.PaymentService;
import com.system.creditOnline.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final LoanRepository loanRepository;
    private final DebitAccountRepository debitAccountRepository;

    @Autowired
    public PaymentServiceImpl(LoanRepository loanRepository, DebitAccountRepository debitAccountRepository) {
        this.loanRepository = loanRepository;
        this.debitAccountRepository = debitAccountRepository;
    }

    @Override
    public List<PaymentDTO> processPayments(LocalDate currentDate, double interestRate, double vatRate, int businessYearDays) {
        return getActiveAccounts().stream()
                .flatMap(account -> processAccountPayments(account, currentDate, interestRate, vatRate, businessYearDays).stream())
                .collect(Collectors.toList());
    }

    private List<DebitAccount> getActiveAccounts() {
        return debitAccountRepository.findByStatus(AccountStatus.ACTIVE);
    }

    private List<PaymentDTO> processAccountPayments(DebitAccount account, LocalDate currentDate, double interestRate, double vatRate, int businessYearDays) {
        return getPendingLoans(account.getCustomer()).stream()
                .map(loan -> processLoanPayment(account, loan, currentDate, interestRate, vatRate, businessYearDays))
                .filter(paymentDTO -> paymentDTO != null)
                .collect(Collectors.toList());
    }

    private List<Loan> getPendingLoans(String customer) {
        return loanRepository.findByCustomerAndStatusOrderByDateAsc(customer, LoanStatus.PENDING);
    }

    private PaymentDTO processLoanPayment(DebitAccount account, Loan loan, LocalDate currentDate, double interestRate, double vatRate, int businessYearDays) {
        long term = PaymentUtils.calculateTerm(loan.getDate(), currentDate);
        double interest = PaymentUtils.calculateInterest(loan.getAmount(), term, interestRate, businessYearDays);
        double vat = PaymentUtils.calculateVat(interest, vatRate);
        double payment = PaymentUtils.calculateTotalPayment(loan.getAmount(), interest, vat);

        if (canProcessPayment(account, payment)) {
            applyPayment(account, loan, payment);
            return createPaymentDTO(account, loan, payment, term, interest, vat);
        }

        return null;
    }

    private boolean canProcessPayment(DebitAccount account, double payment) {
        return account.getAmount() >= payment;
    }

    private void applyPayment(DebitAccount account, Loan loan, double payment) {
        updateLoanStatus(loan);
        updateAccountBalance(account, payment);
    }

    private void updateLoanStatus(Loan loan) {
        loan.setStatus(LoanStatus.PAID);
        loanRepository.save(loan);
    }

    private void updateAccountBalance(DebitAccount account, double payment) {
        account.setAmount(account.getAmount() - payment);
        debitAccountRepository.save(account);
    }

    private PaymentDTO createPaymentDTO(DebitAccount account, Loan loan, double payment, long term, double interest, double vat) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setCustomer(account.getCustomer());
        paymentDTO.setTerm((int) term);
        paymentDTO.setAmount(loan.getAmount());
        paymentDTO.setInterest(interest);
        paymentDTO.setVat(vat);
        paymentDTO.setPayment(payment);
        return paymentDTO;
    }
}
