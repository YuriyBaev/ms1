package lesson.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lesson.model.Lesson;
import lesson.service.LessonService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import javax.jms.Queue;

@RunWith(SpringRunner.class)
@WebMvcTest
class LessonControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Queue queue;

    @MockBean
    private LessonService lessonService;

    @MockBean
    JmsTemplate jmsTemplate;

    private Lesson lesson1, lesson2;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp()
    {
        lesson1 = Lesson.builder().id("1").studentNumber(12L).courseList(Arrays.asList("Math", "English")).build();
        lesson2 = Lesson.builder().id("2").studentNumber(22L).courseList(Arrays.asList("Physic", "Russian")).build();
    }

    @Test
    void getAllLessons() throws Exception
    {
        BDDMockito.given(lessonService.findAll()).willReturn(Arrays.asList(lesson1, lesson2));
        mockMvc.perform(get("/lessons/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    void getLessonsByStudentNumber() throws Exception
    {
        BDDMockito.given(lessonService.findLessonByStudentNumber(lesson1.getStudentNumber())).willReturn(lesson1);
        mockMvc.perform(get("/lessons/byStudentNumber/{studentNumber}", lesson1.getStudentNumber())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(lesson1.getId())));
    }

    @Test
    void addOrUpdateLesson() throws Exception
    {
        BDDMockito.given(lessonService.saveOrUpdateLesson(lesson2)).willReturn(lesson2);
        String content = mapper.writeValueAsString(lesson2);
        mockMvc.perform(post("/lessons/add/{message}", "message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());
    }
}