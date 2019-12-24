package io.choerodon.redis;

import org.springframework.stereotype.Component;

import io.choerodon.redis.impl.HashStringRedisCacheGroup;

@Component
public class TestCache extends HashStringRedisCacheGroup<TestEntity> {
}
