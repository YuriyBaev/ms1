package student.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
@Slf4j
public class MessageConsumer
{
    @JmsListener(destination = "test-queue")
    public void listener(String message)
    {
        LOG.debug("Message received {} ", message);
        System.out.println("Message received: "+ message);
    }
}
