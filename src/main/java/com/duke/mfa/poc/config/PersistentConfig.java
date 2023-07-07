/**
 * 
 */
package com.duke.mfa.poc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Kazi
 *
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.duke.mfa.poc.repo")
@EnableTransactionManagement
public class PersistentConfig {

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${hibernate.ejb.naming_strategy}")
    private String hibernateNamingStrategy;
    @Value("${hibernate.ejb.connectionReleaseStrategy}")
    private String connectionReleaseStrategy;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.format_sql}")
    private String formatSql;
    @Value("${hibernate.jdbc.batch_size}")
    private String batchSize;
    @Value("${hibernate.jdbc.batch_versioned_data}")
    private boolean batchVersionData;
    @Value("${hibernate.order_inserts}")
    private boolean orderInserts;
    @Value("${hibernate.order_updates}")
    private boolean orderUpdates;

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Properties jpaProterties = new Properties();
        jpaProterties.put(Environment.DIALECT, dialect);
        jpaProterties.put(Environment.FORMAT_SQL, formatSql);
        jpaProterties.put(Environment.GENERATE_STATISTICS, true);
        jpaProterties.put(Environment.HBM2DDL_AUTO, org.hibernate.tool.schema.Action.NONE);
        jpaProterties.put("hibernate.ejb.naming_strategy", hibernateNamingStrategy);
        jpaProterties.put(Environment.SHOW_SQL, showSql);
        jpaProterties.put(Environment.FORMAT_SQL, formatSql);
        jpaProterties.put(Environment.CONNECTION_HANDLING, connectionReleaseStrategy);
        jpaProterties.put(Environment.DIALECT, dialect);
        jpaProterties.put(Environment.ORDER_INSERTS, orderInserts);
        jpaProterties.put(Environment.ORDER_UPDATES, orderUpdates);
        jpaProterties.put(Environment.STATEMENT_BATCH_SIZE, batchSize);
        jpaProterties.put(Environment.BATCH_VERSIONED_DATA, batchVersionData);
        jpaProterties.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("com.duke.mfa.poc.entity");
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProterties);
        entityManagerFactoryBean.setDataSource(dataSource);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager oltpTransactionManager = new JpaTransactionManager();
        oltpTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return oltpTransactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
}