import com.fasterxml.jackson.databind.ObjectMapper;
import mk.finki.ukim.wp.studentsapi.StudentsApiApplication;
import mk.finki.ukim.wp.studentsapi.controller.StudyProgramController;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.service.StudyProgramService;
import mk.finki.ukim.wp.studentsapi.service.impl.StudyProgramServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@WebMvcTest(StudyProgramController.class)
@ContextConfiguration(classes={StudentsApiApplication.class})
public class StudyProgramControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudyProgramServiceImpl service;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetStudyProgramsReturnJsonArrayIfStudyProgramsExist() throws Exception {

        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setId(1L);
        studyProgram.setName("KNIA");

        List<StudyProgram> studyProgramsList = Arrays.asList(studyProgram);

        given(service.getAllStudyPrograms()).willReturn(studyProgramsList);

        mvc.perform(get("/api/study_programs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStudyProgramsReturnEmptyJsonArrayIfNoStudyPrograms() throws Exception {

        given(service.getAllStudyPrograms()).willReturn(new ArrayList<>());

        mvc.perform(get("/api/study_programs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStudyProgramWithIdReturnJsonIfStudyProgramWithIdExists() throws Exception {

        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setId(1L);

        given(service.getStudyProgramById(1L)).willReturn(Optional.of(studyProgram));

        mvc.perform(get("/api/study_programs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id': 1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetStudyProgramWithIdReturnStatusNotFoundIfNoStudyProgramWithId() throws Exception {

        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setId(1L);

        given(service.getStudyProgramById(1L)).willReturn(Optional.of(studyProgram));

        mvc.perform(get("/api/study_programs/{id}", 7L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenAddStudyProgramReturnStatusOkIfNew() throws Exception {

        given(service.addStudyProgram("KNIA")).willReturn(true);

        mvc.perform(post("/api/study_programs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new StudyProgram("KNIA"))))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddStudyProgramReturnStatusForbiddenIfOld() throws Exception {

        given(service.addStudyProgram("KNIA")).willReturn(false);

        mvc.perform(post("/api/study_programs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new StudyProgram("KNIA"))))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteStudyProgramReturnStatusOkIfHasNoStudents() throws Exception {

        given(service.deleteStudyProgram(1L)).willReturn(true);

        mvc.perform(delete("/api/study_programs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteStudyProgramReturnStatusForbiddenIfHasStudents() throws Exception {

        given(service.deleteStudyProgram(1L)).willReturn(false);

        mvc.perform(delete("/api/study_programs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenModifyStudyProgramReturnStatusOkIfIdExists() throws Exception {

        given(service.updateStudyProgram(1L, "KNI")).willReturn(true);

        mvc.perform(put("/api/study_programs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("KNI"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenModifyStudyProgramReturnStatusNotFoundIfNoId() throws Exception {

        given(service.updateStudyProgram(7L, "KNIA")).willReturn(false);

        mvc.perform(put("/api/study_programs/{id}", 7L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new StudyProgram("KNIA"))))
                .andExpect(status().isNotFound());
    }

}
