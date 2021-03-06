package io.choerodon.mybatis.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import io.choerodon.mybatis.entity.Criteria;
import io.choerodon.mybatis.provider.UpdateByPrimaryKeyOptionsProvider;

/**
 * Created by jialong.zuo@hand-china.com on 2017/5/22.
 */
public interface UpdateByPrimaryKeyOptionsMapper<T> {

    /**
     * 根据主键更新选定字段的值
     * @return
     */
    @UpdateProvider(type = UpdateByPrimaryKeyOptionsProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeyOptions(@Param(SelectOptionsMapper.OPTIONS_DTO) T record, @Param(SelectOptionsMapper.OPTIONS_CRITERIA) Criteria criteria);

}
