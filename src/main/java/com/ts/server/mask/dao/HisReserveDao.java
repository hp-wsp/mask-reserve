package com.ts.server.mask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 历史预定信息数据操作
 *
 * @author TS Group
 */
@Repository
public class HisReserveDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HisReserveDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(String idCard, int day){
        final String sql= "INSERT INTO t_his_reserve (id_card, day) VALUES(?, ?)";
        jdbcTemplate.update(sql, idCard, day);
    }

    public void update(String idCard, int day){
        final String sql = "UPDATE t_his_reserve SET day = ? WHERE id_card = ?";
        jdbcTemplate.update(sql, day, idCard);
    }

    public boolean has(String idCard){
        final String sql = "SELECT COUNT(id) FROM t_his_reserve WHERE id_card = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{idCard}, Integer.class);
        return count != null && count > 0;
    }

    public Integer getDay(String idCard){
        final String sql = "SELECT day FROM t_his_reserve WHERE id_card = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idCard}, Integer.class);
    }
}
