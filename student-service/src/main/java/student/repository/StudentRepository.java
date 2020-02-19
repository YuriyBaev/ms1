package student.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import student.model.Student;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "college_students", path = "students")
public interface StudentRepository extends MongoRepository<Student, String>
{
    Student findByStudentNumber(String studentNumber);

    Student findByEmail(String email);

    List<Student> findAllByOrderByGpaDesc();
}
