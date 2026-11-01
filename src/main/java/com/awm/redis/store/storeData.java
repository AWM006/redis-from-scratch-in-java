package com.awm.redis.store;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class storeData {

    
    private final Map<String, String> data = new ConcurrentHashMap<>();

    public String put(String key, String value) {
        data.put(key, value);
        return "ok";
    }
    
    public String delete(String key) {
        String removed = data.remove(key);
        return removed != null ? "ok" : "not found";
    }

    public String get(String key) {
        return data.get(key);
    }
}
