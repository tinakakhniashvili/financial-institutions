package com.solvd.financialinstitution.persistence.dao;

import com.solvd.financialinstitution.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDao {

    void create(Address address);

    Optional<Address> findById(long id);

    List<Address> findAll();

    void update(Address address);

    void delete(long id);
}
