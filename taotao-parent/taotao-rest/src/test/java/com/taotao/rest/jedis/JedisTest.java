package com.taotao.rest.jedis;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/31
 */

/**
 *
 @BeforeClass：针对所有测试，只执行一次，且必须为static void
 @Before：初始化方法
 @Test：测试方法，在这里可以测试期望异常和超时时间
 @After：释放资源
 @AfterClass：针对所有测试，只执行一次，且必须为static void
 @Ignore：忽略的测试方法@BeforeClass：针对所有测试，只执行一次，且必须为static void
 @Before：初始化方法
 @Test：测试方法，在这里可以测试期望异常和超时时间
 @After：释放资源
 @AfterClass：针对所有测试，只执行一次，且必须为static void
 @Ignore：忽略的测试方法
 */


public class JedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTest.class);


    @BeforeClass
    public static void beforeClass1(){
        System.out.println("@beforeClass1");
    }
    @BeforeClass
    public static void beforeClass2(){
        System.out.println("@beforeClass2");
    }

    @Before
    public void before1() throws Exception {
        System.out.println("@before1");
    }

    @Before
    public void before2() throws Exception {
        System.out.println("@before2");
    }


    @Test
    public void testJedisSingle(){
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set("key1", "test jedis");
        String result = jedis.get("key1");
        System.out.println(result);
        jedis.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool(){
        //创建连接池
        JedisPool jedisPool = new JedisPool("localhost",6379);
        //从连接池中获取连接
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        jedisPool.close();
    }

    /**
     * 集群版测试
     * <p>Title: testJedisCluster</p>
     * <p>Description: </p>
     */
    @Test
    public void testJedisCluster() {
        LOGGER.debug("调用redisCluster开始");
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.153", 7001));
        nodes.add(new HostAndPort("192.168.25.153", 7002));
        nodes.add(new HostAndPort("192.168.25.153", 7003));
        nodes.add(new HostAndPort("192.168.25.153", 7004));
        nodes.add(new HostAndPort("192.168.25.153", 7005));
        nodes.add(new HostAndPort("192.168.25.153", 7006));
        LOGGER.info("创建一个JedisCluster对象");
        JedisCluster cluster = new JedisCluster(nodes);
        LOGGER.debug("设置key1的值为1000");
        cluster.set("key1", "1000");

        LOGGER.debug("从Redis中取key1的值");
        String string = cluster.get("key1");
        System.out.println(string);
        cluster.close();
        try {
            int a = 1/0;
        } catch (Exception e) {
            LOGGER.error("系统发送异常", e);
        }
    }

    /**
     * 单机版测试
     * <p>Title: testSpringJedisSingle</p>
     * <p>Description: </p>
     */
    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        pool.close();
    }

    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("key1");
        System.out.println(string);
        jedisCluster.close();
    }
}
