package com.geniusver.jedis.impl;

import com.geniusver.jedis.JedisDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DefaultJedisDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 9, 2017</pre>
 */
public class DefaultJedisDaoTest {

    private JedisDao jedisDao;

    @BeforeEach
    public void init() {
        DefaultJedisDao defaultJedisDao = new DefaultJedisDao();
        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        defaultJedisDao.setJedisPool(pool);
        this.jedisDao = defaultJedisDao;
    }

    @Test
    public void testGetAllKey() throws Exception {
        java.lang.Object data = jedisDao.getAllKeys("*", java.lang.Object.class);
        System.out.println(data.toString());
    }

    @Test
    public void testSetData() throws Exception {
        User use = new User("test", "test");
        jedisDao.saveValueByKey("user", use, 300);

        User userBack = jedisDao.getValueByKey("user", User.class);

        assertEquals("test", userBack.getPassword());
    }


}


