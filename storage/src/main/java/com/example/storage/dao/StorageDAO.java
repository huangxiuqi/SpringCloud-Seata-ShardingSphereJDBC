package com.example.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

/**
 * @author huangxiuqi
 */
@Repository
public class StorageDAO {

    private final JdbcTemplate jdbcTemplate;

    public StorageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(String commodityCode, int count) {
        String sql = "insert into `storage_tbl`(`commodity_code`, `count`) value(?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatement ps) -> {
            ps.setString(1, commodityCode);
            ps.setInt(2, count);
            return ps.execute();
        });
    }
}
