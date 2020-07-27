package com.xiaoman.springboot.controller;

import com.xiaoman.springboot.bean.Greeting;
import com.xiaoman.springboot.bean.WebSocketMessage;
import com.xiaoman.springboot.code.RedisCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

/**
 * @description: websocket连接
 * @author: shuxiaoman
 * @time: 2020/7/22 3:03 下午
 */
@Controller
public class WebSocketController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @description: websocket界面
     * @author: shuxiaoman
     * @time: 2020/7/22 3:52 下午
     */
    @RequestMapping("/websocket")
    public String websocket() {
        return "websocket-vue";
    }

    /**
     * @description: 返回当前所有连接人的token
     * @author: shuxiaoman
     * @time: 2020/7/24 5:08 下午
     */
    @MessageMapping("/connecting")
    @SendTo("/topic/connect")
    public Object[] connect(WebSocketMessage message) throws InterruptedException {
        Thread.sleep(1000);
        return redisTemplate.opsForSet().members(RedisCode.websocketToken.toString()).toArray();
    }

    /**
     * @description: 广播消息
     * @author: shuxiaoman
     * @time: 2020/7/24 11:13 上午
     */
    @MessageMapping("/news")
    @SendTo("/topic/news")
    public Greeting greeting(WebSocketMessage message) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    /**
     * @description: 返回给发送人
     * @author: shuxiaoman
     * @time: 2020/7/24 11:13 上午
     */
    @MessageMapping("/question")
    @SendToUser("/queue/answer")
    public String questionAndAnswer(WebSocketMessage message) throws InterruptedException {
        Thread.sleep(1000);
        String question = message.getName();
        System.out.println("问题：" + question);
        String answer = question
                .replaceAll("吗", "")
                .replaceAll("你", "我")
                .replaceAll("爸爸", "儿子")
                .replaceAll("傻逼", "沙雕")
                .replaceAll("[?]", "!")
                .replaceAll("？", "!");
        return "回答：" + HtmlUtils.htmlEscape(answer);
    }

    /**
     * @description: 消息精准推送
     * @author: shuxiaoman
     * @time: 2020/7/27 10:38 上午
     */
    @MessageMapping("/sendToUsers")
    public void sendToUsers(WebSocketMessage message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(message.getToken() + "发送给:" + message.getUserTokens().toString());
        if (ObjectUtils.isEmpty(message.getName())) {
            return;
        }
        WebSocketMessage returnMessage = new WebSocketMessage();
        returnMessage.setName(message.getName());
        returnMessage.setToken(message.getToken());
        for (String token : message.getUserTokens()) {
            simpMessagingTemplate.convertAndSendToUser(token, "/queue/message", returnMessage);
        }
    }
}
