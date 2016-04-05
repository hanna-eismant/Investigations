package investigations.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;

public class Consumer implements MessageListener {

    @Override
    public void onMessage(final Message message) {
        System.out.println(message);
    }
}
