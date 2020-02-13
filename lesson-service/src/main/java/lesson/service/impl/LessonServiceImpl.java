package lesson.service.impl;

import lesson.model.Lesson;
import lesson.repository.LessonRepository;
import lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService
{
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository)
    {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> findAll()
    {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findLessonByStudentNumber(long studentNumber)
    {
        return lessonRepository.findLessonByStudentNumber(studentNumber);
    }
}
