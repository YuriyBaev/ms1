package student.service;

import student.model.Student;

import java.util.List;

public interface StudentService
{
    List<Student> findAll();

    Student findByStudentNumber(String studentNumber);

    Student findByEmail(String email);

    List<Student> findAllByOrderByGpaDesc();

    Student saveOrUpdateStudent(Student student);

    void deleteStudentById(String id);

    void deleteStudent(Student student);
}
