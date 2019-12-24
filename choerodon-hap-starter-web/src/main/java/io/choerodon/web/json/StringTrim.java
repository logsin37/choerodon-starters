package io.choerodon.web.json;

import java.lang.annotation.*;

/**
 * 字符串反序列化去空格.
 *
 * @author peng.jiang@hand-china.com
 * @since 2018/1/16
 **/
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringTrim {
}
