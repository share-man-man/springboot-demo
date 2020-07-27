package com.xiaoman.springboot.config.websocket;

import com.xiaoman.springboot.code.RedisCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * @description: websocket配置
 * @author: shuxiaoman
 * @time: 2020/7/24 9:52 上午
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 应用请求前缀
        config.setApplicationDestinationPrefixes("/app");
        // 消息推送前缀
//        config.enableSimpleBroker("/topic","/queue");
//        config.setUserDestinationPrefix() // 推送用户前缀
    }

    /**
     * @description: 建立连接的端点
     * @author: shuxiaoman
     * @time: 2020/7/23 11:53 上午
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册了gs-guide-websocket端点后，websocket的第一个http请求(例如：/gs-guide-websocket/info?t=1595483384774)不会被mvc拦截
        registry.addEndpoint("/grain-full-websocket").setAllowedOrigins("*").withSockJS();

    }

//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registry){
//        registry.setTimeToFirstMessage()
//        registry.addDecoratorFactory()
//        registry.setDecoratorFactories()
//        registry.setMessageSizeLimit()
//        registry.setSendBufferSizeLimit()
//        registry.setSendTimeLimit()
//    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                StompCommand command = accessor.getCommand();
                if (StompCommand.CONNECT.equals(command)) {
                    String authToken = accessor.getFirstNativeHeader("Auth-Token");
                    redisTemplate.opsForSet().add(RedisCode.websocketToken.toString(), authToken);
                    Principal user = new Principal() {
                        @Override
                        public String getName() {
                            return authToken;
                        }
                    }; // access authentication header(s)
                    accessor.setUser(user);
                }
                if (StompCommand.DISCONNECT.equals(command)) {
                    redisTemplate.opsForSet().remove(RedisCode.websocketToken.toString(), accessor.getUser().getName());
                    // 有人退出，重新发送当前
                    simpMessagingTemplate.convertAndSend("/topic/disConnect", accessor.getUser().getName());
                }
                return message;
            }
        });
//        registration.interceptors();
//        registration.taskExecutor();
//        registration.taskExecutor()
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.interceptors()
//        registration.taskExecutor()
//        registration.taskExecutor()
    }

    /**
     * @description:
     * @author: shuxiaoman
     * @time: 2020/7/23 11:43 上午
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        System.out.println("argumentResolvers------------------");
//        System.out.println(argumentResolvers.toString());
    }


    /**
     * @description: 返回值
     * @author: shuxiaoman
     * @time: 2020/7/23 11:43 上午
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//        System.out.println("returnValueHandlers------------------");
//        System.out.println(returnValueHandlers.toString());
    }

    /**
     * @description: 配置消息转换
     * @author: shuxiaoman
     * @time: 2020/7/23 11:41 上午
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        System.out.println("configureMessageConverters------------------");
//        System.out.println(messageConverters.toString());
        return true;
    }

}
