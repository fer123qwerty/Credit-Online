package com.system.creditOnline.repository;

import com.system.creditOnline.entity.Loan;
import com.system.creditOnline.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomerAndStatusOrderByDateAsc(String customer, LoanStatus status);
}
