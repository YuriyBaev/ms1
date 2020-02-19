package lesson.listener;

import lesson.enums.Action;
import lesson.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageConsumer
{
    @Autowired
    LessonService lessonService;

    @JmsListener(destination = "test-queue")
    public void listener(String message) throws Exception {
        LOG.debug("Message received {} ", message);
        System.out.println("Message received: "+ message);

        if(Action.DELETED.getValue().equals(message.substring(0, message.indexOf('#')))){
            System.out.println("inside of deletion lesson: "+message.substring(message.indexOf('#')+1));
            lessonService.deleteLesson(message.substring(message.indexOf('#')+1));
        }
    }
}
