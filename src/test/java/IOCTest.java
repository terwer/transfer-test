import com.test.transfer.SpringConfig;
import com.test.transfer.dao.AccountDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: terwer
 * @date: 2021/12/15 23:48
 * @description:
 */
public class IOCTest {
    @Test
    public void testIoc() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
        System.out.println("accountDao=>" + accountDao);
    }
}
