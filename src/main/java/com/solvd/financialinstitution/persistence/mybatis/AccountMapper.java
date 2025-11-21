package com.solvd.financialinstitution.persistence.mybatis;

import com.solvd.financialinstitution.domain.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {

    void create(Account account);

    Account findById(@Param("id") long id);

    List<Account> findAll();

    void update(Account account);

    void delete(@Param("id") long id);

    List<Account> findByCustomerId(@Param("customerId") long customerId);
}
