package com.solvd.financialinstitution.persistence.dao;

import com.solvd.financialinstitution.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    void create(Account account);

    Optional<Account> findById(long id);

    List<Account> findAll();

    void update(Account account);

    void delete(long id);
}
