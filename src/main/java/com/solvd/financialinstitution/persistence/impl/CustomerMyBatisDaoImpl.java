package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Customer;
import com.solvd.financialinstitution.persistence.CustomerDao;
import com.solvd.financialinstitution.persistence.MyBatisUtil;
import com.solvd.financialinstitution.persistence.mybatis.CustomerMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CustomerMyBatisDaoImpl implements CustomerDao {

    @Override
    public void create(Customer customer) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.create(customer);
            session.commit();
        }
    }

    @Override
    public Optional<Customer> findById(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Customer> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void update(Customer customer) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.update(customer);
            session.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }

    @Override
    public Optional<Customer> findByIdWithAccountsAndLoans(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return Optional.ofNullable(mapper.findByIdWithAccountsAndLoans(id));
        }
    }

    @Override
    public List<Customer> findByCity(String city) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.findByCity(city);
        }
    }
}
