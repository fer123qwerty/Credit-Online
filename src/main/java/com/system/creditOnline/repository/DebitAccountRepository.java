package com.system.creditOnline.repository;

import com.system.creditOnline.entity.DebitAccount;
import com.system.creditOnline.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebitAccountRepository extends JpaRepository<DebitAccount, Long> {
    List<DebitAccount> findByStatus(AccountStatus status);
}
