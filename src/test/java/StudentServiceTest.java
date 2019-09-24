import mk.finki.ukim.wp.studentsapi.StudentsApiApplication;
import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.repository.StudentRepository;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import mk.finki.ukim.wp.studentsapi.service.impl.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={StudentsApiApplication.class})
public class StudentServiceTest {

    @TestConfiguration
    static class StudentServiceTestContextConfiguration{

        @Bean
        public StudentServiceImpl studentService(){
            return new StudentServiceImpl();
        }
    }

    @Autowired
    private StudentServiceImpl studentService;

    @MockBean
    private StudyProgramRepository studyProgramRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Before
    public void setup(){

        Student student = new Student();
        student.setIndex("161501");
        student.setName("Ana");
        student.setLastName("Todorovska");
        student.setStudyProgram(1L);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        Mockito.when(studentRepository.findAll()).thenReturn(studentList);
        Mockito.when(studentRepository.findById(student.getIndex())).thenReturn(Optional.of(student));
        Mockito.when(studyProgramRepository.findByName("KNIA")).thenReturn(new StudyProgram("KNIA"));
        Mockito.when(studyProgramRepository.findById(1L)).thenReturn(Optional.of(new StudyProgram("KNIA")));
        Mockito.when(studentRepository.findAllByStudyProgram(1L)).thenReturn(Optional.of(studentList));
        Mockito.when(studentRepository.save(student)).thenReturn(student);
    }

    @Test
    public void whenGetStudentByIdThenStudentIfId(){

        String index = "161501";
        Optional<StudentInput> found = studentService.getStudentById(index);

        assertThat(found.get().getIndex()).isEqualTo(index);
    }

    @Test
    public void whenGetStudentByIdThenNothingIfNoId(){

        String index = "161510";
        Optional<StudentInput> found = studentService.getStudentById(index);

        assertThat(found.isPresent()).isEqualTo(false);
    }

   @Test
   public void whenGetAllStudentsThenAllStudentsIfExist(){

       StudentInput student = new StudentInput();
       student.setIndex("161501");
       student.setName("Ana");
       student.setLastName("Todorovska");
       student.setStudyProgram("KNIA");

       List<StudentInput> studentList = new ArrayList<>();
       studentList.add(student);

       List<StudentInput> found = studentService.getAllStudents();

       for(int i=0; i<found.size(); i++){
           assertEquals(found.get(i).getIndex(), studentList.get(i).getIndex());
       }
   }

   @Test
   public void whenGetAllStudentsThenNothingIfNoStudents(){

       List<Student> list = new ArrayList<>();
       Mockito.when(studentRepository.findAll()).thenReturn(list);
       List<StudentInput> found = studentService.getAllStudents();

       assertThat(found.size()).isEqualTo(0);
   }

    @Test
    public void whenAddStudentThenTrueIfValid(){

       String index = "161515";
       String name = "Angela";
       String lastName = "Todorovska";
       String studyProgram = "KNIA";
       boolean added = studentService.addStudent(index, name, lastName, studyProgram);

       assertThat(added).isEqualTo(true);
    }

    @Test
    public void whenAddStudentThenFalseIfIndexNotValidLength(){

        String index = "1615158";
        String name = "Angela";
        String lastName = "Todorovska";
        String studyProgram = "KNIA";
        boolean added = studentService.addStudent(index, name, lastName, studyProgram);

        assertThat(added).isEqualTo(false);
    }

    @Test
    public void whenAddStudentThenFalseIfIndexNotValidType(){

        String index = "16151P";
        String name = "Angela";
        String lastName = "Todorovska";
        String studyProgram = "KNIA";
        boolean added = studentService.addStudent(index, name, lastName, studyProgram);

        assertThat(added).isEqualTo(false);
    }

    @Test
    public void whenUpdateStudentThenTrueIfExistsAndValidData(){

        String index = "161501";
        String name = "Angela";
        String lastName = "Krajchevska";
        String studyProgram = "KNIA";
        boolean updated = studentService.modifyStudent(index, new StudentInput(index, name, lastName, studyProgram));

        assertThat(updated).isEqualTo(true);
    }

    @Test
    public void whenUpdateStudentThenFalseIfNotExists(){

        String index = "161520";
        String name = "Angela";
        String lastName = "Krajchevska";
        String studyProgram = "KNIA";
        boolean updated = studentService.modifyStudent(index, new StudentInput(index, name, lastName, studyProgram));

        assertThat(updated).isEqualTo(false);
    }

    @Test
    public void whenFindAllByStudyProgramThenAllStudentsIfExist(){

        Student student = new Student();
        student.setIndex("161501");
        student.setName("Ana");
        student.setLastName("Todorovska");
        student.setStudyProgram(1L);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        List<Student> found = studentService.findAllByStudyProgram(1L);

        for(int i=0; i<found.size(); i++){
            assertEquals(found.get(i).getIndex(), studentList.get(i).getIndex());
        }
    }

    @Test
    public void whenFindAllByStudyProgramThenNothingIfNoStudents(){

        List<Student> list = new ArrayList<>();
        Mockito.when(studentRepository.findAllByStudyProgram(1L)).thenReturn(Optional.of(list));
        List<Student> found = studentService.findAllByStudyProgram(1L);

        assertThat(found.size()).isEqualTo(0);
    }

}
