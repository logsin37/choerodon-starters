package io.choerodon.resource.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义auth异常处理端点
 *
 * @author superlee
 * @since 2019-08-08
 */
public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        final Map<String, Object> map = new HashMap<>(10);
        map.put("error", "unauthorized");
        map.put("message", authException.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), map);
    }
}
