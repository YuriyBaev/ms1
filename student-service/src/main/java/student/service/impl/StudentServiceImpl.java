package student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.model.Student;
import student.repository.StudentRepository;
import student.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService
{
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll()
    {
        return studentRepository.findAll();
    }

    @Override
    public Student findByStudentNumber(long studentNumber)
    {
        return studentRepository.findByStudentNumber(studentNumber);
    }

    @Override
    public Student findByEmail(String email)
    {
        return studentRepository.findByEmail(email);
    }

    @Override
    public List<Student> findAllByOrderByGpaDesc()
    {
        return studentRepository.findAllByOrderByGpaDesc();
    }

    @Override
    public Student saveOrUpdateStudent(Student student)
    {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(String id)
    {
        studentRepository.deleteById(id);

    }
}
