package io.choerodon.mybatis

import com.fasterxml.jackson.databind.ObjectMapper
import io.choerodon.liquibase.LiquibaseConfig
import io.choerodon.liquibase.LiquibaseExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

import javax.annotation.PostConstruct

/**
 * @author superlee
 */

@TestConfiguration
@Import(LiquibaseConfig)
class IntegrationTestConfiguration {

    final ObjectMapper objectMapper = new ObjectMapper()

    @Autowired
    LiquibaseExecutor liquibaseExecutor

    @PostConstruct
    void init() {
        //通过liquibase初始化h2数据库
        liquibaseExecutor.execute()
    }

}