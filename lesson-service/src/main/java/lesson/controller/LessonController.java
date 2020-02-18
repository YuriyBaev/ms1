package lesson.controller;

import lesson.model.Lesson;
import lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.jms.Queue;

@RestController
@RequestMapping("/lessons")
public class LessonController
{
    private final LessonService lessonService;

    private final Queue queue;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public LessonController(LessonService lessonService, Queue queue, JmsTemplate jmsTemplate)
    {
        this.lessonService = lessonService;
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/")
    public List<Lesson> getAllLessons()
    {
        return lessonService.findAll();
    }

    @GetMapping(value = "/byStudentNumber/{studentNumber}")
    public Lesson getLessonsByStudentNumber(@PathVariable("studentNumber") Long studentNumber)
    {
        return lessonService.findLessonByStudentNumber(studentNumber);
    }

    @RequestMapping(value = "/add/{message}", method = RequestMethod.POST)
    public Lesson addOrUpdateLesson(@RequestBody final Lesson lesson, @PathVariable("message") final String message)
    {
        jmsTemplate.convertAndSend(queue, message);
        return lessonService.saveOrUpdateLesson(lesson);
    }


}
