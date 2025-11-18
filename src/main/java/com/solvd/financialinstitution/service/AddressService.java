package com.solvd.financialinstitution.service;

import com.solvd.financialinstitution.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    void create(Address address);

    Optional<Address> getById(long id);

    List<Address> getAll();

    void update(Address address);

    void delete(long id);
}
