package lesson.controller;

import lesson.model.Lesson;
import lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController
{
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService)
    {
        this.lessonService = lessonService;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Lesson addOrUpdateLesson(@RequestBody final Lesson lesson)
    {
        return lessonService.saveOrUpdateLesson(lesson);
    }

}
