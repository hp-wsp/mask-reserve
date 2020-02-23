package com.ts.server.mask.dao;

import com.ts.server.mask.common.utils.DaoUtils;
import com.ts.server.mask.domain.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * 店铺数据操作
 *
 * @author TS Group
 */
@Repository
public class PharmacyDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Pharmacy> mapper = (r, i) -> {
        Pharmacy t = new Pharmacy();

        t.setId(r.getInt("id"));
        t.setArea(r.getString("area"));
        t.setName(r.getString("name"));
        t.setAddress(r.getString("address"));
        t.setStack(r.getInt("stack"));
        t.setSell(r.getInt("sell"));
        t.setVersion(r.getInt("version"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public PharmacyDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int insert(Pharmacy t){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql =  "INSERT INTO t_pharmacy (area, name, address, stack, sell, version, update_time, create_time) " +
                "VALUES (?, ?, ?, 0, 0, 0, now(), now())";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getArea());
            ps.setString(2, t.getName());
            ps.setString(3, t.getAddress());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public boolean update(Pharmacy t){
        final String sql = "UPDATE t_pharmacy SET area = ?, name = ?, address = ?, update_time = now() WHERE id = ?";
        return jdbcTemplate.update(sql, t.getArea(), t.getName(), t.getAddress(), t.getId()) > 0;
    }

    public boolean incStack(int id, int stack, int version){
        final String sql = "UPDATE t_pharmacy SET stack = stack + ?, version = version +1 WHERE id = ? AND version = ?";
        return jdbcTemplate.update(sql, stack, id, version) > 0;
    }

    public boolean delete(int id){
        final String sql = "DELETE FROM t_pharmacy WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Pharmacy findOne(int id){
        final String sql = "SELECT * FROM t_pharmacy WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<Pharmacy> findOfArea(String area, int min){
        final String sql = "SELECT * FROM t_pharmacy WHERE area = ? AND stack >= ?";
        return jdbcTemplate.query(sql, new Object[]{area, min}, mapper);
    }

    public Long count(String area, String name){
        final String sql = "SELECT COUNT(id) FROM t_pharmacy WHERE area LIKE ? AND name LIKE ?";

        String areaLike = DaoUtils.like(area);
        String nameLike = DaoUtils.like(name);

        return jdbcTemplate.queryForObject(sql, new Object[]{areaLike, nameLike}, Long.class);
    }

    public List<Pharmacy> find(String area, String name, int offset, int limit){
        final String sql = "SELECT * FROM t_pharmacy WHERE area LIKE ? AND name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";

        String areaLike = DaoUtils.like(area);
        String nameLike = DaoUtils.like(name);

        return jdbcTemplate.query(sql, new Object[]{areaLike, nameLike, limit, offset}, mapper);
    }
}
