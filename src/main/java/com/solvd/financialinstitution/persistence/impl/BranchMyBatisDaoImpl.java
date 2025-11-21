package com.solvd.financialinstitution.persistence.impl;

import com.solvd.financialinstitution.domain.Branch;
import com.solvd.financialinstitution.persistence.BranchDao;
import com.solvd.financialinstitution.persistence.MyBatisUtil;
import com.solvd.financialinstitution.persistence.mybatis.BranchMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class BranchMyBatisDaoImpl implements BranchDao {

    @Override
    public void create(Branch branch) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BranchMapper mapper = session.getMapper(BranchMapper.class);
            mapper.create(branch);
            session.commit();
        }
    }

    @Override
    public Optional<Branch> findById(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BranchMapper mapper = session.getMapper(BranchMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    @Override
    public List<Branch> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BranchMapper mapper = session.getMapper(BranchMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void update(Branch branch) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BranchMapper mapper = session.getMapper(BranchMapper.class);
            mapper.update(branch);
            session.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BranchMapper mapper = session.getMapper(BranchMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }
}
