package com.taotao.search;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Title: spring-test的基础类</p>
 * <p>Description: 测试类继承自此接口可以使用@Autowired注入</p>
 * <p>@ContextConfiguration中标定的配置文件中的bean</p>
 *
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/applicationContext-dao.xml",
        "classpath:spring/applicationContext-service.xml",
        "classpath:spring/applicationContext-solr.xml"
})
public class BaseTaotaoSearchTestCase {
}
