package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import student.model.Student;
import student.model.dto.LessonDto;
import student.service.StudentService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("studentNumber", studentNumber);

        String path = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost:8083")
                .path("/lessons/byStudentNumber/{studentNumber}")
                .buildAndExpand(uriVariables)
                .toUriString();
        System.out.println("path: "+path);

        return restTemplate.exchange(path, HttpMethod.GET, httpEntity, LessonDto.class);
    }

}
