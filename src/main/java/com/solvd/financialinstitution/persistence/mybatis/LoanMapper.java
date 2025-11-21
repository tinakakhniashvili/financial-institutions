package com.solvd.financialinstitution.persistence.mybatis;

import com.solvd.financialinstitution.domain.Loan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanMapper {

    void create(Loan loan);

    Loan findById(@Param("id") long id);

    List<Loan> findAll();

    void update(Loan loan);

    void delete(@Param("id") long id);

    List<Loan> findByCustomerId(@Param("customerId") long customerId);
}
