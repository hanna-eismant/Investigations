package investigations.activemq;

import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

public class Sender {

    private JmsTemplate jmsTemplate;

    public Sender(final JmsTemplate _jmsTemplate) {
        jmsTemplate = _jmsTemplate;
    }

    public void send(final Map _map) {
        jmsTemplate.convertAndSend(_map);
    }
}
