package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Address;
import com.solvd.financialinstitution.persistence.AddressDao;
import com.solvd.financialinstitution.persistence.impl.AddressMyBatisDaoImpl;
import com.solvd.financialinstitution.service.AddressService;

import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    public AddressServiceImpl() {
        this(new AddressMyBatisDaoImpl());
    }

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public void create(Address address) {
        addressDao.create(address);
    }

    @Override
    public Optional<Address> getById(long id) {
        return addressDao.findById(id);
    }

    @Override
    public List<Address> getAll() {
        return addressDao.findAll();
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public void delete(long id) {
        addressDao.delete(id);
    }
}
