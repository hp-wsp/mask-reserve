package com.ts.server.mask.dao;

import com.ts.server.mask.common.utils.DaoUtils;
import com.ts.server.mask.domain.Reserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 预约数据操作
 *
 * @author TS Group
 */
@Repository
public class ReserveDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reserve> mapper = (r, i) -> {
        Reserve t = new Reserve();

        t.setId(r.getLong("id"));
        t.setTarId(r.getString("tra_id"));
        t.setName(r.getString("name"));
        t.setAddress(r.getString("address"));
        t.setMobile(r.getString("mobile"));
        t.setPhaId(r.getInt("pha_id"));
        t.setPharmacy(r.getString("pharmacy"));
        t.setPhaAddress(r.getString("pha_address"));
        t.setIdCard(r.getString("id_card"));
        t.setCycle(r.getInt("cycle"));
        t.setCount(r.getInt("count"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ReserveDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Reserve t){
        final String sql = "INSERT INTO t_reserve (tra_id, name, address, mobile, pha_id, pharmacy, pha_address, id_card, " +
                "cycle, count, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getTarId(), t.getName(), t.getAddress(), t.getMobile(), t.getPhaId(), t.getPharmacy(),
                t.getPhaAddress(), t.getIdCard(), t.getCycle(), t.getCount());
    }

    public boolean hasCycle(String idCard, int cycle){
        final String sql = "SELECT COUNT(id) FROM t_reserve WHERE id_card = ? AND cycle = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{idCard, cycle}, Integer.class);
        return count != null && count > 0;
    }

    public Reserve findOne(String id){
        final String sql = "SELECT * FROM t_reserve WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String name, String pharmacy, Date fromDate, Date toDate){
        final String sql = "SELECT COUNT(id) FROM t_reserve WHERE name LIKE ? AND pharmacy LIKE ? " +
                "AND create_time BETWEEN ? AND ?";

        String nameLike = DaoUtils.like(name);
        String pharmacyLike = DaoUtils.like(pharmacy);

        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike, pharmacyLike,fromDate, toDate}, Long.class);
    }

    public List<Reserve> find(String name, String pharmacy, Date fromDate, Date toDate, int offset, int limit){
        final String sql = "SELECT * FROM t_reserve WHERE name LIKE ? AND pharmacy LIKE ? " +
                "AND create_time BETWEEN ? AND ? ORDER BY create_time ASC LIMIT ? OFFSET ?";

        String nameLike = DaoUtils.like(name);
        String pharmacyLike = DaoUtils.like(pharmacy);

        return jdbcTemplate.query(sql, new Object[]{nameLike, pharmacyLike,fromDate, toDate, limit, offset}, mapper);
    }

    public Optional<Reserve> findLast(String idCard){
        final String sql = "SELECT * FROM t_reserve WHERE id_card = ? ORDER BY cycle DESC LIMIT 1 OFFSET 0";
        List<Reserve> data = jdbcTemplate.query(sql, new Object[]{idCard}, mapper);
        return data.isEmpty()? Optional.empty(): Optional.of(data.get(0));
    }
}
