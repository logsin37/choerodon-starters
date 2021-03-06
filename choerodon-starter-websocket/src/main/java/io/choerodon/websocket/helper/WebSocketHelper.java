package io.choerodon.websocket.helper;

import org.springframework.web.socket.WebSocketSession;

import io.choerodon.websocket.send.MessageSender;
import io.choerodon.websocket.send.SendMessagePayload;
import io.choerodon.websocket.send.relationship.BrokerKeySessionMapper;

/**
 * Created by hailuo.liu@choerodon.io on 2019/7/2.
 */
public class WebSocketHelper {
    private MessageSender sender;
    private BrokerKeySessionMapper brokerKeySessionMapper;

    public WebSocketHelper(MessageSender sender, BrokerKeySessionMapper brokerKeySessionMapper){
        this.sender = sender;
        this.brokerKeySessionMapper = brokerKeySessionMapper;
    }

    /**
     * 通过 Key 发送消息
     * @param key 消息 Key
     * @param payload 消息体
     */
    public void sendMessageByKey(String key, SendMessagePayload payload){
        sender.sendByKey(key, payload);
    }

    public void closeSessionByKey(String key) {
        sender.closeSessionByKey(key);
    }

    /**
     * 直接使用 Session 发送消息
     * @param session Session
     * @param payload 消息体
     */
    public void sendMessageBySession(WebSocketSession session, SendMessagePayload payload){
        sender.sendBySession(session, payload);
    }

    /**
     * key与 web socket session 关联
     * @param key 消息 key
     * @param session web socket session
     */
    public void subscribe(String key, WebSocketSession session){
        brokerKeySessionMapper.subscribe(key, session);
    }

    /**
     * 解除key,webSocket的关联
     * @param key message key
     * @param session web socket session
     */
    public void unsubscribe(String key, WebSocketSession session){
        brokerKeySessionMapper.unsubscribe(key, session);
    }

}
