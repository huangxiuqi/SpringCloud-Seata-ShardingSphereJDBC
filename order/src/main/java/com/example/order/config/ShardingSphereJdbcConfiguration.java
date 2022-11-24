package com.example.order.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.transaction.config.TransactionRuleConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author huangxiuqi
 */
//@Configuration
public class ShardingSphereJdbcConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        // 配置数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", createDataSource("seata_order0"));
        dataSourceMap.put("ds1", createDataSource("seata_order1"));

        List<RuleConfiguration> rules = Arrays.asList(
                createShardingRuleConfiguration(),
                createTransactionRuleConfiguration());

        return ShardingSphereDataSourceFactory.createDataSource(
                dataSourceMap, rules, new Properties());
    }

    private DataSource createDataSource(String name) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/" + name + "?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
        // 返回Seata代理数据源
//        return new DataSourceProxy(dataSource);
    }

    private ShardingRuleConfiguration createShardingRuleConfiguration() {
        ShardingRuleConfiguration result = new ShardingRuleConfiguration();

        // 添加分库分表规则
        result.getTables().add(getOrderTableRuleConfiguration());

        // 添加order_database_rule规则，按照user_id取余
        Properties props1 = new Properties();
        props1.setProperty("algorithm-expression", "ds${user_id % 2}");
        result.getShardingAlgorithms().put(
                "order_database_rule",
                new AlgorithmConfiguration("INLINE", props1));

        // 添加order_table_rule规则，按照order_id取余
        Properties props2 = new Properties();
        props2.setProperty("algorithm-expression", "order_tbl${id % 2}");
        result.getShardingAlgorithms().put(
                "order_table_rule",
                new AlgorithmConfiguration("INLINE", props2));

        // 添加snowflake规则，雪花算法生成id
        result.getKeyGenerators().put(
                "snowflake",
                new AlgorithmConfiguration("SNOWFLAKE", new Properties()));

        // 添加sharding_key_required_auditor规则，要求DML语句必须包含分片键
        result.getAuditors().put(
                "sharding_key_required_auditor",
                new AlgorithmConfiguration("DML_SHARDING_CONDITIONS", new Properties()));
        return result;
    }

    private TransactionRuleConfiguration createTransactionRuleConfiguration() {
        return new TransactionRuleConfiguration("BASE", "Seata", new Properties());
    }

    private ShardingTableRuleConfiguration getOrderTableRuleConfiguration() {
        // 分库分表命名规则  分ds0，ds1两个数据库，每个数据库各分t_order0，t_order1两个表
        ShardingTableRuleConfiguration result = new ShardingTableRuleConfiguration("order_tbl", "ds${0..1}.order_tbl${0..1}");
        // 根据user_id分库，规则名称order_database_rule
        result.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "order_database_rule"));
        // 根据order_id分表，规则名称order_table_rule
        result.setTableShardingStrategy(new StandardShardingStrategyConfiguration("id", "order_table_rule"));
        // order_id主键生成规则，规则名称snowflake
        result.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "snowflake"));
//        result.setAuditStrategy(new ShardingAuditStrategyConfiguration(Collections.singleton("sharding_key_required_auditor"), true));
        return result;
    }
}
