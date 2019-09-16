import mk.finki.ukim.wp.studentsapi.StudentsApiApplication;
import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.repository.StudentRepository;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import mk.finki.ukim.wp.studentsapi.service.impl.StudyProgramServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={StudentsApiApplication.class})
public class StudyProgramServiceTest {

    @TestConfiguration
    static class MovieServiceTestContextConfiguration{

        @Bean
        public StudyProgramServiceImpl studyProgramService(){
            return new StudyProgramServiceImpl();
        }
    }

    @Autowired
    private StudyProgramServiceImpl studyProgramService;

    @MockBean
    private StudyProgramRepository studyProgramRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Before
    public void setup(){

        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setId(1L);
        studyProgram.setName("KNIA");

        List<StudyProgram> studyPrograms = new ArrayList<>();
        studyPrograms.add(studyProgram);


        Student student = new Student();
        student.setIndex("161501");

        List<Student> students = new ArrayList<>();
        students.add(student);

        Mockito.when(studyProgramRepository.findAll()).thenReturn(studyPrograms);
        Mockito.when(studyProgramRepository.findById(studyProgram.getId())).thenReturn(Optional.of(studyProgram));
        Mockito.when(studyProgramRepository.findByName(studyProgram.getName())).thenReturn(studyProgram);
        Mockito.when(studentRepository.findAllByStudyProgram(studyProgram.getId())).thenReturn(Optional.of(students));
        Mockito.when(studyProgramRepository.save(studyProgram)).thenReturn(studyProgram);
    }

    @Test
    public void whenGetStudyProgramByIdThenStudyProgramIfId(){

        Long id = 1L;
        Optional<StudyProgram> found = studyProgramService.getStudyProgramById(id);

        assertThat(found.get().getId()).isEqualTo(id);
    }

    @Test
    public void whenGetStudyProgramByIdThenNothingIfNoId(){

        Long id = 4L;
        Optional<StudyProgram> found = studyProgramService.getStudyProgramById(id);

        assertThat(found.isPresent()).isEqualTo(false);
    }

    @Test
    public void whenGetAllStudyProgramsThenAllStudyProgramsIfExist(){

        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setId(1L);
        studyProgram.setName("KNIA");

        List<StudyProgram> list = new ArrayList<>();
        list.add(studyProgram);

        List<StudyProgram> found = studyProgramService.getAllStudyPrograms();

        for(int i=0; i<found.size(); i++){
            assertEquals(found.get(i).getId(), list.get(i).getId());
        }
    }

    @Test
    public void whenGetAllStudyProgramsThenNothingIfNoStudyPrograms(){

        List<StudyProgram> list = new ArrayList<>();
        Mockito.when(studyProgramRepository.findAll()).thenReturn(list);
        List<StudyProgram> found = studyProgramService.getAllStudyPrograms();

        assertThat(found.size()).isEqualTo(0);
    }

    @Test
    public void whenAddStudyProgramThenTrueIfValid(){

        String name = "KNI";
        boolean added = studyProgramService.addStudyProgram(name);

        assertThat(added).isEqualTo(true);
    }

    @Test
    public void whenAddStudyProgramThenFalseIfAlreadyExists(){

        String name = "KNIA";
        boolean added = studyProgramService.addStudyProgram(name);

        assertThat(added).isEqualTo(false);
    }

    @Test
    public void whenDeleteStudyProgramThenTrueIfExists(){

        Long id = 1L;
        boolean deleted = studyProgramService.deleteStudyProgram(id);

        assertThat(deleted).isEqualTo(true);
    }

    @Test
    public void whenDeleteStudyProgramThenFalseIfNotExists(){

        Long id = 4L;
        boolean deleted = studyProgramService.deleteStudyProgram(id);

        assertThat(deleted).isEqualTo(false);
    }

    @Test
    public void whenUpdateStudyProgramThenTrueIfIdAndNameNotExists(){

        Long id = 1L;
        String name = "PIT";
        boolean updated = studyProgramService.updateStudyProgram(id, name);

        assertThat(updated).isEqualTo(true);
    }

    @Test
    public void whenUpdateStudyProgramThenFalseIfIdNotExists(){

        Long id = 4L;
        String name = "PIT";
        boolean updated = studyProgramService.updateStudyProgram(id, name);

        assertThat(updated).isEqualTo(false);
    }

    @Test
    public void whenUpdateStudyProgramThenFalseIfIdButNameExists(){

        Long id = 1L;
        String name = "KNIA";
        boolean updated = studyProgramService.updateStudyProgram(id, name);

        assertThat(updated).isEqualTo(false);
    }
}
