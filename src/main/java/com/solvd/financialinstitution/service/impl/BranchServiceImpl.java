package com.solvd.financialinstitution.service.impl;

import com.solvd.financialinstitution.domain.Address;
import com.solvd.financialinstitution.domain.Branch;
import com.solvd.financialinstitution.persistence.BranchDao;
import com.solvd.financialinstitution.persistence.impl.BranchDaoImpl;
import com.solvd.financialinstitution.service.AddressService;
import com.solvd.financialinstitution.service.BranchService;

import java.util.List;
import java.util.Optional;

public class BranchServiceImpl implements BranchService {

    private final BranchDao branchDao;
    private final AddressService addressService;

    public BranchServiceImpl() {
        this(new BranchDaoImpl(), new AddressServiceImpl());
    }

    public BranchServiceImpl(BranchDao branchDao, AddressService addressService) {
        this.branchDao = branchDao;
        this.addressService = addressService;
    }

    @Override
    public void create(Branch branch) {
        if (branch == null) {
            throw new IllegalArgumentException("branch must not be null");
        }

        Address address = branch.getAddress();
        if (address != null) {
            addressService.create(address);
            branch.setAddressId(address.getId());
        }

        branchDao.create(branch);
    }

    @Override
    public Optional<Branch> getById(long id) {
        return branchDao.findById(id);
    }

    @Override
    public List<Branch> getAll() {
        return branchDao.findAll();
    }

    @Override
    public void update(Branch branch) {
        branchDao.update(branch);
    }

    @Override
    public void delete(long id) {
        branchDao.delete(id);
    }
}
