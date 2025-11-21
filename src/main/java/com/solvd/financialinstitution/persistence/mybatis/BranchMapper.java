package com.solvd.financialinstitution.persistence.mybatis;

import com.solvd.financialinstitution.domain.Branch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BranchMapper {

    void create(Branch branch);

    Branch findById(@Param("id") long id);

    List<Branch> findAll();

    void update(Branch branch);

    void delete(@Param("id") long id);
}
