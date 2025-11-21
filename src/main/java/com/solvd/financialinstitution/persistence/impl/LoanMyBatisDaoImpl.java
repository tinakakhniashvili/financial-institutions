package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Loan;
import com.solvd.financialinstitution.persistence.LoanDao;
import com.solvd.financialinstitution.persistence.MyBatisUtil;
import com.solvd.financialinstitution.persistence.mybatis.LoanMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class LoanMyBatisDaoImpl implements LoanDao {

    @Override
    public void create(Loan loan) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            mapper.create(loan);
            session.commit();
        }
    }

    @Override
    public Optional<Loan> findById(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Loan> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void update(Loan loan) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            mapper.update(loan);
            session.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }

    @Override
    public List<Loan> findByCustomerId(long customerId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LoanMapper mapper = session.getMapper(LoanMapper.class);
            return mapper.findByCustomerId(customerId);
        }
    }
}
