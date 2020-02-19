package lesson.service;

import lesson.model.Lesson;

import java.util.List;

public interface LessonService
{
    List<Lesson> findAll();

    Lesson findLessonByStudentNumber(String studentNumber) throws Exception;

    Lesson saveOrUpdateLesson(Lesson lesson);

    void deleteLesson(String studentNumber) throws Exception;


}
