package com.wch.redisTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Jedis api test
 *
 */
public class JedisTest {

    private Jedis jedis;

    @Before
    public void before() {
        jedis = new Jedis("127.0.0.1", 6384);
    }

    /**
     * 字符串
     * @String
     */
    @Test
    public void stringTest() {
        jedis.flushDB();
        jedis.set("language", "java");

        jedis.append("language", "c++");
        String language = jedis.get("language");
        Assert.assertEquals(language, "javac++");

        jedis.incr("count");
        jedis.incrBy("count", 10);
        Double price = jedis.incrByFloat("price", 1.2);
        Assert.assertEquals(1.2, price, 0.0);

        jedis.decrBy("count", 5);
        Assert.assertEquals(jedis.get("count"), "5");
    }
}
