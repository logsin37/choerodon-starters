package io.choerodon.websocket.connect;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Created by hailuo.liu@choerodon.io on 2019/7/2.
 */
public class HandshakeCheckerHandler implements HandshakeInterceptor {

    private SocketHandlerRegistration registration;

    public HandshakeCheckerHandler(SocketHandlerRegistration registration){
        this.registration = registration;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return registration.beforeHandshake(request, response);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        //啥都不干
    }
}
