package com.solvd.financialinstitution.persistence.mybatis;

import com.solvd.financialinstitution.domain.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {

    void create(Address address);

    Address findById(@Param("id") long id);

    List<Address> findAll();

    void update(Address address);

    void delete(@Param("id") long id);
}
