package io.choerodon.websocket

import org.junit.BeforeClass
import org.springframework.boot.test.context.TestConfiguration

/**
 * @author superlee
 */

@TestConfiguration
class IntegrationTestConfiguration {
    static int redisPort = 0
    @BeforeClass
    static void before() {

//        SpringApplicationBuilder recvie = new SpringApplicationBuilder(TestApplication.class)
//        recvie.run()
    }
}