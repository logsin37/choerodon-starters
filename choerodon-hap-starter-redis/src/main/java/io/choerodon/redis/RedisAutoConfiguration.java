package io.choerodon.redis;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import io.choerodon.redis.impl.RedisNodeAutoConfig;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan
@PropertySource("classpath:default-choerodon-redis-config.properties")
public class RedisAutoConfiguration {

    @Value("${redis.cluster:}")
    private String[] clusters;

    @Value("${redis.sentinel}")
    private String[] sentinels;

    @Value("${redis.useSentinel}")
    private boolean useSentinel;

    @Value("${redis.useCluster:false}")
    private boolean useCluster;

    @Value("${redis.ip:localhost}")
    private String hostName;

    @Value("${redis.port:6379}")
    private Integer port;

    @Value("${redis.db:10}")
    private Integer database;

    @Value("${redis.password:}")
    private String password;

    @Bean(value = "v2redisConnectionFactory")
    public JedisConnectionFactory v2redisConnectionFactory() throws Exception {
        JedisConnectionFactory cacheObject;
        if(useCluster){
            cacheObject = new JedisConnectionFactory(redisClusterConfiguration());
        }else if (useSentinel) {
            cacheObject = new JedisConnectionFactory(redisSentinelConfiguration());
        } else {
            cacheObject = new JedisConnectionFactory();
            cacheObject.setHostName(hostName);
            cacheObject.setPort(port);
        }
        if(!StringUtils.isEmpty(password)) {
            cacheObject.setPassword(password);
        }
        cacheObject.setDatabase(database);
        cacheObject.setUsePool(true);
        cacheObject.setPoolConfig(jedisPoolConfig());
        cacheObject.afterPropertiesSet();
        return cacheObject;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(999);
        config.setMaxTotal(9999);
        config.setMinIdle(20);
        return config;
    }

    @Bean(value = "stringRedisSerializer")
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }



    @Bean(value = "redisSentinelConfiguration")
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        configuration.setMaster("mymaster");
        configuration.setSentinels(redisNodes());
        return configuration;
    }

    @Bean(value = "redisClusterConfiguration")
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.setClusterNodes(clusterRedisNodes());
        return clusterConfiguration;
    }

    @Bean(value = "clusterRedisNodes")
    public RedisNodeAutoConfig clusterRedisNodes() {
        RedisNodeAutoConfig config = new RedisNodeAutoConfig();
        config.setSentinels(clusters);
        return config;
    }

    @Bean(value = "mapSerializer")
    public Jackson2JsonRedisSerializer mapSerializer() {
        return new Jackson2JsonRedisSerializer(HashMap.class);
    }

    @Bean(value = "redisNodes")
    public RedisNodeAutoConfig redisNodes() {
        RedisNodeAutoConfig config = new RedisNodeAutoConfig();
        config.setSentinels(sentinels);
        return config;
    }

    @Bean(value = "v2redisTemplate")
    public RedisTemplate v2redisTemplate() throws Exception {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(v2redisConnectionFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(stringRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(stringRedisSerializer());
        return redisTemplate;
    }
}
