package com.example.rediscache.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    @PostMapping("/session/increment")
    public Map<String, Object> increment(HttpSession session) {
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) count = 0;
        count++;
        session.setAttribute("count", count);
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", count);
        resp.put("sessionId", session.getId());
        return resp;
    }

    @GetMapping("/session/value")
    public Map<String, Object> value(HttpSession session) {
        Integer count = (Integer) session.getAttribute("count");
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", count == null ? 0 : count);
        resp.put("sessionId", session.getId());
        return resp;
    }

    @PostMapping("/session/reset")
    public Map<String, Object> reset(HttpSession session) {
        session.setAttribute("count", 0);
        Map<String, Object> resp = new HashMap<>();
        resp.put("count", 0);
        resp.put("sessionId", session.getId());
        return resp;
    }
}