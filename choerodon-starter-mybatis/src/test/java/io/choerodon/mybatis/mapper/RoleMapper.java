package io.choerodon.mybatis.mapper;


import java.util.Map;

import io.choerodon.mybatis.common.Mapper;
import io.choerodon.mybatis.dto.Role;

/**
 * @author superlee
 */
public interface RoleMapper extends Mapper<Role> {
    Map selectTest();
}
