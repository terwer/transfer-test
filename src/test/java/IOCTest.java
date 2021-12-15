import com.test.transfer.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author: terwer
 * @date: 2021/12/15 23:48
 * @description:
 */
public class IOCTest {
    @Test
    public void testIoc(){
        // 通过classpath下面的application.xml启动容器（XML的SE路径推荐使用）
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        // 不推荐
        // FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("/Users/terwer/Documents/beans.xml");
        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        System.out.println(accountDao);
    }
}
