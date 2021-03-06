import investigations.activemq.Sender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");

        HashMap<Object, Object> map = new HashMap<>();
        map.put("message", "test");

        Sender sender = applicationContext.getBean(Sender.class);
        sender.send(map);
    }
}
