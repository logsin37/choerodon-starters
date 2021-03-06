package io.choerodon.mybatis.dto;

import java.util.Date;

import javax.persistence.*;

import io.choerodon.mybatis.annotation.EnableExtensionAttribute;
import io.choerodon.mybatis.annotation.MultiLanguage;
import io.choerodon.mybatis.annotation.MultiLanguageField;
import io.choerodon.mybatis.common.query.Comparison;
import io.choerodon.mybatis.common.query.Where;
import io.choerodon.mybatis.entity.BaseDTO;

/**
 * 角色DTO.
 *
 * @author shengyang.zhou@hand-china.com
 * @date 2016/6/9
 */
@MultiLanguage
@Table(name = "sys_role_b")
@EnableExtensionAttribute
public class RoleTL extends BaseDTO {

    public static final String FIELD_ROLE_ID = "roleId";
    public static final String FIELD_ROLE_CODE = "roleCode";
    public static final String FIELD_ROLE_NAME = "roleName";
    public static final String FIELD_ROLE_DESCRIPTION = "roleDescription";
    public static final String FIELD_START_ACTIVE_DATE = "startActiveDate";
    public static final String FIELD_END_ACTIVE_DATE = "endActiveDate";
    public static final String FIELD_ENABLE_FLAG = "enableFlag";

    @Id
    @Column
    @Where
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy("DESC")
    private Long roleId;

    @Column
    @OrderBy("ASC")
    @Where
    private String roleCode;

    @Column
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    @OrderBy
    private String roleName;

    @Column
    @MultiLanguageField
    @Where
    private String roleDescription;

    @Column
    @Where(comparison = Comparison.GREATER_THAN_OR_EQUALTO)
    @OrderBy
    private Date startActiveDate;

    @Column
    @Where(comparison = Comparison.LESS_THAN_OR_EQUALTO)
    @OrderBy
    private Date endActiveDate;

    @Column
    private String enableFlag;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean isEnabled() {
        return YES.equals(enableFlag);
    }

    public boolean isActive() {
        return (startActiveDate == null || startActiveDate.getTime() <= System.currentTimeMillis())
                && (endActiveDate == null || endActiveDate.getTime() >= System.currentTimeMillis());
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
