package com.example.order.dao;

import com.example.common.entity.OrderEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author huangxiuqi
 */
@Repository
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OrderEntity insert(OrderEntity entity) {
        String sql = "insert into `order_tbl`(`user_id`, `commodity_code`, `count`, `money`) value(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, entity.getUserId());
            ps.setString(2, entity.getCommodityCode());
            ps.setInt(3, entity.getCount());
            ps.setInt(4, entity.getMoney());
            return ps;
        }, keyHolder);
        Long key = keyHolder.getKeyAs(Long.class);
        if (key != null) {
            entity.setId(key);
//            throw new RuntimeException("order exception");
        }
        return entity;
    }
}
