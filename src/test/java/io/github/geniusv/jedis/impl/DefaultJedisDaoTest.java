package io.github.geniusv.jedis.impl;

import io.github.geniusv.jedis.JedisDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

/**
 * DefaultJedisDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 9, 2017</pre>
 */
public class DefaultJedisDaoTest {

    private JedisDao jedisDao;

    @Before
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

        Assert.assertEquals("test", userBack.getPassword());
    }


}


