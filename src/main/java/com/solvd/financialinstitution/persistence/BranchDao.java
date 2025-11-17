package com.solvd.financialinstitution.persistence;

import com.solvd.financialinstitution.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchDao {

    void create(Branch branch);

    Optional<Branch> findById(long id);

    List<Branch> findAll();

    void update(Branch branch);

    void delete(long id);

    List<Branch> findByBankId(long bankId);
}
