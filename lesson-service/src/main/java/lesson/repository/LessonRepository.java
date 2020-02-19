package lesson.repository;

import lesson.model.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "lessons", path = "lessons")
public interface LessonRepository extends MongoRepository<Lesson, String>
{
    @Override
    List<Lesson> findAll();

    Optional<Lesson> findLessonByStudentNumber(String studentNumber);
}
