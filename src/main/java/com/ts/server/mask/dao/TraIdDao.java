package com.ts.server.mask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 交易编号数据操作
 *
 * @author TS Group
 */
@Repository
public class TraIdDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TraIdDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(int phaId, int day){
        final String sql = "INSERT INTO t_tra_id (pha_id, day, count) VALUES(?, ?, 0)";
        jdbcTemplate.update(sql, phaId, day);
    }

    public boolean update(int phaId, int day, int count){
        final String sql = "UPDATE t_tra_id SET count = ? WHERE pha_id = ? AND day = ?";
        return jdbcTemplate.update(sql, count, phaId, day) > 0;
    }

    public Integer findCount(int phaId, int day){
        final String sql = "SELECT count FROM t_tra_id WHERE pha_id = ? AND day = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{phaId, day}, Integer.class);
    }
}
