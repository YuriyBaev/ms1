package lesson.controller;

import lesson.model.Lesson;
import lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value= "/byStudentNumber/{studentNumber}")
    public Lesson getLessonsByStudentNumber(@PathVariable("studentNumber") Long studentNumber)
    {
        return lessonService.findLessonByStudentNumber(studentNumber);
    }

}
