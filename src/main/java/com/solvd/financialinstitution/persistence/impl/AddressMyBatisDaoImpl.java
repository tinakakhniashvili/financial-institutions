package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Address;
import com.solvd.financialinstitution.persistence.AddressDao;
import com.solvd.financialinstitution.persistence.MyBatisUtil;
import com.solvd.financialinstitution.persistence.mybatis.AddressMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class AddressMyBatisDaoImpl implements AddressDao {

    @Override
    public void create(Address address) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AddressMapper mapper = session.getMapper(AddressMapper.class);
            mapper.create(address);
            session.commit();
        }
    }

    @Override
    public Optional<Address> findById(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AddressMapper mapper = session.getMapper(AddressMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Address> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AddressMapper mapper = session.getMapper(AddressMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void update(Address address) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AddressMapper mapper = session.getMapper(AddressMapper.class);
            mapper.update(address);
            session.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AddressMapper mapper = session.getMapper(AddressMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }
}
