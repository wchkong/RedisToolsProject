package com.wch.redisTest;

import org.junit.*;

public class JunitTest {

    @BeforeClass
    public static void beforeClass() {
        // 对整个类来说只会在开始执行用例之前执行一次
    }

    @AfterClass
    public static void afterClass() {
        // 对整个类来说只会在开始执行用例之后执行一次
    }

    @Before
    public void setUp() {
        // 每个测试用例执行之前都会执行
    }

    @After
    public void tearDown() {
        // 每个测试用例执行完后都会执行
    }

    @Test
    public void test1() {

        try {
            int z = 0;
            int value = 10 * 1 / z;
        } catch (ArithmeticException a) {
            return;
        }
        Assert.fail();
    }

    @Test(expected = ArithmeticException.class)
    public void testEvaluateWrongExpression() {
        int value = 10 * 1 / 0;
    }


}
