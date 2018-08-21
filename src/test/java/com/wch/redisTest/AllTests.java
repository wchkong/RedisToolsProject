package com.wch.redisTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JedisTest.class,
        JunitTest.class
})
public class AllTests {
}
