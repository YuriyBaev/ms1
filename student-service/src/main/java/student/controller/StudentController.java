package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import student.enums.Action;
import student.model.Student;
import student.model.dto.LessonDto;
import student.service.StudentService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.Queue;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private final StudentService studentService;
    private final RestTemplate restTemplate;
    private final Queue queue;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public StudentController(StudentService studentService, RestTemplate restTemplate, Queue queue, JmsTemplate jmsTemplate)
    {
        this.studentService = studentService;
        this.restTemplate = restTemplate;
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping(value = "/")
    public List<Student> getAllStudents()
    {
        return studentService.findAll();
    }

    @GetMapping(value = "/byStudentNumber/{studentNumber}")
    public Student getStudentByStudentNumber(@PathVariable("studentNumber") Long studentNumber)
    {
        return studentService.findByStudentNumber(studentNumber);
    }

    @GetMapping(value = "/lessonsByStudentNumber/{studentNumber}")
    public ResponseEntity<LessonDto> getLessonsByStudentNumber(@PathVariable("studentNumber") Long studentNumber) throws Exception
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
        System.out.println("path: " + path);

        ResponseEntity<LessonDto> responseEntity = restTemplate.exchange(path, HttpMethod.GET, httpEntity, LessonDto.class);
        if (responseEntity.getBody() == null)
        {
            throw new Exception(String.format("Not found any lessons by %s student id ", studentNumber));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/delete/{studentNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStudent(@PathVariable("studentNumber") final long studentNumber) throws Exception
    {
        Student student = studentService.findByStudentNumber(studentNumber);
        if (student == null)
            throw new Exception(String.format("Student with number %s not found", studentNumber));

        studentService.deleteStudentById(studentNumber);
        jmsTemplate.convertAndSend(queue, Action.DELETED.getValue()+"#"+studentNumber);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/add/{message}", method = RequestMethod.POST)
    public Student addOrUpdateLesson(@RequestBody final Student student, @PathVariable("message") final String message)
    {
        //jmsTemplate.convertAndSend(queue, message);
        return studentService.saveOrUpdateStudent(student);
    }

}
