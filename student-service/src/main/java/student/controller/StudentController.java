package student.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import student.model.Student;
import student.model.dto.LessonDto;
import student.service.StudentService;

import javax.xml.bind.DatatypeConverter;
import java.util.List;

@RestController
@RequestMapping("/students")
@RefreshScope
public class StudentController
{
    private final StudentService studentService;
    private final RestTemplate restTemplate;

    @Autowired
    public StudentController(StudentService studentService, RestTemplate restTemplate)
    {
        this.studentService = studentService;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/")
    public List<Student> getAllStudents()
    {
        return studentService.findAll(); //ObjectMapperUtils.mapAll(studentService.findAll(), StudentDTO.class);
    }

    @GetMapping(value = "/byStudentNumber/{studentNumber}")
    public Student getStudentByStudentNumber(@PathVariable("studentNumber") Long studentNumber)
    {
        return studentService.findByStudentNumber(studentNumber);//ObjectMapperUtils.map(studentService.findByStudentNumber(studentNumber), StudentDTO.class);
    }

    @GetMapping(value = "/lessonsByStudentNumber/{studentNumber}")
    public ResponseEntity<LessonDto> getLessonsByStudentNumber(@PathVariable("studentNumber") Long studentNumber)
    {
        String url = "http://localhost:8083/lessons/byStudentNumber/" + studentNumber;
        return restTemplate.exchange(url, HttpMethod.GET, null, LessonDto.class);
    }

}
