package io.choerodon.oauth.core.password.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.oauth.core.password.domain.BaseUserDTO;
import io.choerodon.oauth.core.password.mapper.BaseUserMapper;
import io.choerodon.oauth.core.password.service.BaseUserService;

/**
 * @author wuguokai
 */
@Service
public class BaseUserServiceImpl implements BaseUserService {

    private BaseUserMapper baseUserMapper;

    public BaseUserServiceImpl(BaseUserMapper baseUserMapper) {
        this.baseUserMapper = baseUserMapper;
    }

    @Override
    public BaseUserDTO lockUser(Long userId, long lockExpireTime) {
        BaseUserDTO user = baseUserMapper.selectByPrimaryKey(userId);
        user.setLocked(true);
        user.setLockedUntilAt(new Date(System.currentTimeMillis() + lockExpireTime * 1000));
        if (baseUserMapper.updateByPrimaryKeySelective(user) != 1) {
            throw new CommonException("error.user.lock");
        }
        return baseUserMapper.selectByPrimaryKey(userId);
    }
}
