package com.solvd.financialinstitution.service;

import com.solvd.financialinstitution.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchService {

    void create(Branch branch);

    Optional<Branch> getById(long id);

    List<Branch> getAll();

    void update(Branch branch);

    void delete(long id);
}
