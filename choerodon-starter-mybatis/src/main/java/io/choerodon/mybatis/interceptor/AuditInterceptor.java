package io.choerodon.mybatis.interceptor;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.choerodon.mybatis.common.SelectOptionsMapper;
import io.choerodon.mybatis.entity.BaseDTO;
import io.choerodon.mybatis.util.OGNL;

/**
 * 在更新之前自动添加四个审计字段的值， creationDate， createdBy， lastUpdateDate， lastUpdatedBy
 */
@Order(10)
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        if (parameter instanceof MapperMethod.ParamMap) {
            Map map = ((Map) parameter);
            if (map.containsKey(SelectOptionsMapper.OPTIONS_DTO)) {
                parameter = ((Map) parameter).get(SelectOptionsMapper.OPTIONS_DTO);
            }
        }
        if (parameter instanceof BaseDTO) {
            switch (statement.getSqlCommandType()) {
                case INSERT:
                    ((BaseDTO) parameter).setCreatedBy(OGNL.principal());
                    ((BaseDTO) parameter).setCreationDate(new Date());
                case UPDATE:
                    ((BaseDTO) parameter).setLastUpdatedBy(OGNL.principal());
                    ((BaseDTO) parameter).setLastUpdateDate(new Date());
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
