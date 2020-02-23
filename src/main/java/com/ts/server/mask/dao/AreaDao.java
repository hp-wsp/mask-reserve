package com.ts.server.mask.dao;

import com.ts.server.mask.common.utils.DaoUtils;
import com.ts.server.mask.domain.Area;
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
 * 区域数据操作
 *
 * @author TS Group
 */
@Repository
public class AreaDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Area> mapper = (r, i) -> {
        Area t = new Area();

        t.setId(r.getInt("id"));
        t.setName(r.getString("name"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public AreaDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int insert(Area t){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO t_area (name, create_time) VALUES (?, now())";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getName());
            return ps;
            }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public boolean update(Area t){
        final String sql = "UPDATE t_area SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getId()) > 0;
    }

    public boolean delete(int id){
        final String sql = "DELETE FROM t_area WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Area findOne(int id){
        final String sql = "SELECT * FROM t_area WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String name){
        String nameLike = DaoUtils.like(name);
        final String sql = "SELECT COUNT(id) FROM t_area WHERE name LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike}, Long.class);
    }

    public List<Area> find(String name, int offset, int limit){
        String nameLike = DaoUtils.like(name);
        final String sql = "SELECT * FROM t_area WHERE name LIKE ? ORDER BY create_time ASC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{nameLike, limit, offset}, mapper);
    }
}
