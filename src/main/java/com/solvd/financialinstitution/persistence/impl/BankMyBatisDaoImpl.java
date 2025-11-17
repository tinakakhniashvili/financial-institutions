package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Bank;
import com.solvd.financialinstitution.persistence.BankDao;
import com.solvd.financialinstitution.persistence.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class BankMyBatisDaoImpl implements BankDao {

    @Override
    public void create(Bank bank) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            mapper.create(bank);
            session.commit();
        }
    }

    @Override
    public Optional<Bank> findById(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            Bank bank = mapper.findById(id);
            return Optional.ofNullable(bank);
        }
    }

    @Override
    public List<Bank> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            return mapper.findAll();
        }
    }

    @Override
    public void update(Bank bank) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            mapper.update(bank);
            session.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            mapper.delete(id);
            session.commit();
        }
    }

    @Override
    public Optional<Bank> findByIdWithBranchesAndAddresses(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            Bank bank = mapper.findByIdWithBranchesAndAddresses(id);
            return Optional.ofNullable(bank);
        }
    }

    @Override
    public List<Bank> findAllWithBranchesAndAddresses() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BankDao mapper = session.getMapper(BankDao.class);
            return mapper.findAllWithBranchesAndAddresses();
        }
    }
}
