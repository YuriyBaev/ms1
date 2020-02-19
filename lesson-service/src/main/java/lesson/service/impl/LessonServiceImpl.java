package lesson.service.impl;

import lesson.model.Lesson;
import lesson.repository.LessonRepository;
import lesson.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findLessonByStudentNumber(String studentNumber) throws Exception {
        return lessonRepository.findLessonByStudentNumber(studentNumber).orElseThrow(()->new Exception(String.format("Lessons belonging to %s not found", studentNumber)));
    }

    @Override
    public Lesson saveOrUpdateLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(String studentNumber) throws Exception {
        Lesson lesson = lessonRepository.findLessonByStudentNumber(studentNumber).orElseThrow(()->new Exception(String.format("Lessons belonging to %s not found", studentNumber)));
        System.out.println("lesson.toString(): "+ lesson.toString());
        lessonRepository.delete(lesson);
    }

}
