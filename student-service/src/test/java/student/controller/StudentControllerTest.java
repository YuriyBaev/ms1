package student.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import student.model.Student;
import student.model.dto.LessonDto;
import student.service.StudentService;

import java.net.URI;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest
class StudentControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private StudentService studentService;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp()
    {
        student1 = Student.builder()
                .id("1")
                .studentNumber("11")
                .name("Sam")
                .gpa(2.1f).build();

        student2 = Student.builder()
                .id("2")
                .studentNumber("12")
                .name("Bob")
                .gpa(2.2f).build();

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getAllStudents_whenGetAllStudents_thenReturnList() throws Exception
    {
        BDDMockito.given(studentService.findAll()).willReturn(Arrays.asList(student1, student2));
        mockMvc.perform(get("/students/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));

    }

    @Test
    void getStudentByStudentNumber() throws Exception
    {
        BDDMockito.given(studentService.findByStudentNumber(student1.getStudentNumber())).willReturn(student1);
        mockMvc.perform(get("/students/byStudentNumber/{studentNumber}", student1.getStudentNumber())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(student1.getName())));
    }

    @Test void getLessonsByStudentNumber() throws Exception
    {
        LessonDto lessonDto = new LessonDto("1", "12", Arrays.asList("English"));
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8083/lessons/byStudentNumber/12")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(lessonDto))
                );

       /*BDDMockito.given(studentService.findByStudentNumber(student1.getStudentNumber())).willReturn(student1);
        mockMvc.perform(get("/lessonsByStudentNumber/{studentNumber}", student1.getStudentNumber())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));*/

    }
}