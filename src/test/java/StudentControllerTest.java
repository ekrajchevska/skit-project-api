import com.fasterxml.jackson.databind.ObjectMapper;
import mk.finki.ukim.wp.studentsapi.StudentsApiApplication;
import mk.finki.ukim.wp.studentsapi.controller.StudentController;
import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.service.impl.StudentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
@ContextConfiguration(classes={StudentsApiApplication.class})
public class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetAllStudentsReturnJsonArrayIfStudentsExist() throws Exception {

        StudentInput student = new StudentInput();
        student.setIndex("161501");
        student.setName("Ana");
        student.setLastName("Todorovska");
        student.setStudyProgram("KNIA");

        List<StudentInput> studentsList = Arrays.asList(student);

        given(service.getAllStudents()).willReturn(studentsList);

        mvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllStudentsReturnEmptyJsonArrayIfNoStudents() throws Exception {

        given(service.getAllStudents()).willReturn(new ArrayList<>());

        mvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStudentByIndexReturnJsonIfStudentWithIndexExists() throws Exception {

        StudentInput student = new StudentInput();
        student.setIndex("161501");

        given(service.getStudentById("161501")).willReturn(Optional.of(student));

        mvc.perform(get("/api/students/{index}", "161501")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'index': '161501'}"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStudentByIndexReturnStatusNotFoundIfNoStudentWithIndex() throws Exception {

        StudentInput student = new StudentInput();
        student.setIndex("161501");

        given(service.getStudentById("161501")).willReturn(Optional.of(student));

        mvc.perform(get("/api/students/{index}", "161527")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetStudentsByStudyProgramReturnJsonArrayIfStudentsExist() throws Exception {

        Student student = new Student();
        student.setIndex("161501");
        student.setName("Ana");
        student.setLastName("Todorovska");
        student.setStudyProgram(1L);

        List<Student> studentsList = Arrays.asList(student);

        given(service.findAllByStudyProgram(1L)).willReturn(studentsList);

        mvc.perform(get("/api/students/by_study_program/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStudentsByStudyProgramReturnEmptyJsonArrayIfNoStudents() throws Exception {

        given(service.findAllByStudyProgram(1L)).willReturn(new ArrayList<>());

        mvc.perform(get("/api/students/by_study_program/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());
    }


    @Test
    public void whenAddStudentReturnStatusOkIfAdded() throws Exception {

        given(service.addStudent("161501", "Ana", "Todorovska", "KNIA")).willReturn(true);

        given(service.getStudentById("161501")).willReturn(Optional.of(new StudentInput("161501", "Ana", "Todorovska", "KNIA")));

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new StudentInput("161501", "Ana", "Todorovska", "KNIA"))))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddStudentReturnStatusNotFoundIfNotAdded() throws Exception {

        given(service.addStudent("161501", "Ana", "Todorovska", "KNIA")).willReturn(false);

        mvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new StudentInput("161501", "Ana", "Todorovska", "KNIA"))))
                .andExpect(status().isNotFound());

    }

    @Test
    public void whenDeleteStudentReturnStatusOkIfStudent() throws Exception {

        StudentInput student = new StudentInput();
        student.setIndex("161501");

        given(service.getStudentById("161501")).willReturn(Optional.of(student));

        mvc.perform(delete("/api/students/{index}", "161501")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteStudentReturnStatusNotFoundIfStudentNotExists() throws Exception {

        StudentInput student = new StudentInput();
        student.setIndex("161501");

        given(service.getStudentById("161501")).willReturn(Optional.of(student));

        mvc.perform(delete("/api/students/{index}", "161514")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenModifyStudyProgramReturnStatusOkIfIdExists() throws Exception {

        StudentInput studentInput = new StudentInput("161501", "Ana", "Todorovska", "KNIA");

        given(service.modifyStudent("161501", studentInput)).willReturn(true);

        mvc.perform(put("/api/students/{index}", "161501")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInput)))
                .andExpect(status().isOk());

    }

    @Test
    public void whenModifyStudyProgramReturnStatusNotFoundIdExists() throws Exception {

        StudentInput studentInput = new StudentInput("161501", "Ana", "Todorovska", "KNIA");

        given(service.modifyStudent("161501", studentInput)).willReturn(false);

        mvc.perform(put("/api/students/{index}", "161501")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInput)))
                .andExpect(status().isNotFound());

    }

}
