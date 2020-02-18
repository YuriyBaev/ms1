package lesson.service;

import lesson.model.Lesson;

import java.util.List;

public interface LessonService
{
    List<Lesson> findAll();

    Lesson findLessonByStudentNumber(long studentNumber);

    Lesson saveOrUpdateLesson(Lesson lesson);

    void deleteLesson(long studentNumber);

}
