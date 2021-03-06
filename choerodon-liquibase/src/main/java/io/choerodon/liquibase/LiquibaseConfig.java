package io.choerodon.liquibase;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import io.choerodon.liquibase.addition.ProfileMap;


/**
 * Created by hailuoliu@choerodon.io on 2018/7/11.
 */
public class LiquibaseConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    LiquibaseExecutor getLiquibaseExecutor() {
        return new LiquibaseExecutor(dataSource);
    }

    @Bean
    ProfileMap getProfileMap() {
        return new ProfileMap();
    }


}
