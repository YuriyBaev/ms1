package student.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import student.model.Student;
import student.service.StudentService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class StudentControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp()
    {
        student1 = Student.builder()
                .id("1")
                .studentNumber(11L)
                .name("Sam")
                .gpa(2.1f).build();

        student2 = Student.builder()
                .id("2")
                .studentNumber(12L)
                .name("Bob")
                .gpa(2.2f).build();
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
}