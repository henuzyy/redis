package com.besttone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest {

    public static void set(RedisTemplate<String, String> redisTemplate, final int id) {
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(("username" + id).getBytes(), ("zhangyangyang" + id).getBytes());
                return null;
            }
        });
    }
    
    
    public static String get(RedisTemplate<String, String> redisTemplate ,final String key){
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keys = key.getBytes();
                return new String(connection.get(keys));
            }
        });
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring.xml");
        @SuppressWarnings("unchecked")
        RedisTemplate<String, String> redisTemplate =  (RedisTemplate<String, String>) ctx.getBean("redisTemplate");
        int count = 0;
        for (int i = 0; i < 10; i++) {
            try {
                set(redisTemplate, i); //添加操作
            } catch (Exception e) {
                count++;
                System.out.println("---------------------------");
                // e.printStackTrace();
                continue;
            }

        }

        //查询操作
        String value = get(redisTemplate, "username1");
        
        System.out.println("丟失的个数为：" + count);
        System.out.println("value=" + value);

    }
}
