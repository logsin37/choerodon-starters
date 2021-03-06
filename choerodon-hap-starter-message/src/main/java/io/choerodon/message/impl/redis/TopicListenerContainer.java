/*
 * Copyright Hand China Co.,Ltd.
 */

package io.choerodon.message.impl.redis;

import java.lang.reflect.Method;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;

import io.choerodon.message.annotation.TopicMonitor;
import io.choerodon.message.impl.ChannelAndQueuePrefix;
import io.choerodon.message.impl.MethodReflectUtils;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class TopicListenerContainer extends RedisMessageListenerContainer implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(TopicListenerContainer.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public boolean isAutoStartup(){
        return false;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Map<String, Object> monitors = applicationContext.getBeansWithAnnotation(TopicMonitor.class);
        Map<MessageListener, Collection<? extends Topic>> listeners = new HashMap<>();
        monitors.forEach((k, v) -> {
            Class<?> clazz = AopUtils.getTargetClass(v);
            TopicMonitor tm = clazz.getAnnotation(TopicMonitor.class);
            String mn = MethodReflectUtils.getTopicMethodName(tm.method(), v);
            List<Method> avaMethods = MethodReflectUtils.findMethod(clazz, new MethodReflectUtils.FindDesc(mn, 2));

            if (avaMethods.isEmpty()) {
                if (logger.isErrorEnabled()) {
                    logger.error("can not find proper method of name '{}' for bean {}", mn, v);
                }
                return;
            }

            SimpleMessageListener adaptor = new SimpleMessageListener(v, avaMethods.get(0));
            List<Topic> topics = new ArrayList<>();
            for (String t : tm.channel()) {
                //添加前缀
                t = ChannelAndQueuePrefix.addPrefix(t);
                Topic topic = new PatternTopic(t);
                topics.add(topic);
            }

            listeners.put(adaptor, topics);
        });
        setMessageListeners(listeners);
    }

    private static class SimpleMessageListener implements MessageListener {
        private RedisSerializer redisSerializer;

        private Object target;
        private Method method;

        private Logger logger;

        SimpleMessageListener(Object target, Method method) {
            this.target = target;
            this.method = method;
            Class p0 = method.getParameterTypes()[0];
            redisSerializer = MethodReflectUtils.getProperRedisSerializer(p0);
            logger = LoggerFactory.getLogger(target.getClass());
        }

        @Override
        public void onMessage(Message message, byte[] pattern) {
            try {
                Object obj = redisSerializer.deserialize(message.getBody());
                String p = new String(pattern, "UTF-8");
                //去掉前缀
                p = ChannelAndQueuePrefix.removePrefix(p);
                method.invoke(target, obj, p);
            } catch (Exception e) {
                Throwable thr = e;
                while (thr.getCause() != null) {
                    thr = thr.getCause();
                }
                if (logger.isErrorEnabled()) {
                    logger.error(thr.getMessage(), thr);
                }
            }
        }
    }
}
