package com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocDao;
import com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper;
import com.lavy.redbook.distributed.id.generator.biz.core.segment.model.LeafAlloc;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */

public class IDAllocDaoImpl implements IDAllocDao {

    SqlSessionFactory sqlSessionFactory;

    public IDAllocDaoImpl(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(IDAllocMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            return sqlSession.selectList(
                    "com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper.getAllLeafAllocs");
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update(
                    "com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper.updateMaxId", tag);
            LeafAlloc result = sqlSession.selectOne(
                    "com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper.getLeafAlloc", tag);
            sqlSession.commit();
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update(
                    "com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper"
                            + ".updateMaxIdByCustomStep",
                    leafAlloc);
            LeafAlloc result = sqlSession.selectOne(
                    "com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper.getLeafAlloc",
                    leafAlloc.getKey());
            sqlSession.commit();
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<String> getAllTags() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            return sqlSession.selectList(
                    "com.lavy.redbook.distributed.id.generator.biz.core.segment.dao.IDAllocMapper.getAllTags");
        } finally {
            sqlSession.close();
        }
    }
}
