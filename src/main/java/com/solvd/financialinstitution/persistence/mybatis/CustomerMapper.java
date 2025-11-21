package com.solvd.financialinstitution.persistence.mybatis;

import com.solvd.financialinstitution.domain.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {

    void create(Customer customer);

    Customer findById(@Param("id") long id);

    List<Customer> findAll();

    void update(Customer customer);

    void delete(@Param("id") long id);

    Customer findByIdWithAccountsAndLoans(@Param("id") long id);

    List<Customer> findByCity(@Param("city") String city);
}
