package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Account;
import com.solvd.financialinstitution.persistence.AccountDao;
import com.solvd.financialinstitution.persistence.MyBatisUtil;
import com.solvd.financialinstitution.persistence.mybatis.AccountMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class AccountMyBatisDaoImpl implements AccountDao {

    @Override
    public void create(Account account) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            mapper.create(account);
            session.commit();
        }
    }

    @Override
    public Optional<Account> findById(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Account> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void update(Account account) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            mapper.update(account);
            session.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }

    @Override
    public List<Account> findByCustomerId(long customerId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.findByCustomerId(customerId);
        }
    }
}
