package lesson.controller;

import lesson.model.Lesson;
import lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import java.util.List;

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
