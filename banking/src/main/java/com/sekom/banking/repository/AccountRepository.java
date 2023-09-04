package com.sekom.banking.repository;

import com.sekom.banking.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account,UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // Apply pessimistic write lock
    @Query("SELECT a FROM Account a WHERE a.id = :accountId")
    Account findAccountWithPessimisticLock(UUID accountId);


}
