package com.example.account.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

/**
 * @author huangxiuqi
 */
@Repository
public class AccountDAO {

    private final JdbcTemplate jdbcTemplate;

    public AccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(int userId, int money) {
        String sql = "insert into `account_tbl`(`user_id`, `money`) value(?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatement ps) -> {
            ps.setInt(1, userId);
            ps.setInt(2, money);
            return ps.execute();
        });
    }
}
