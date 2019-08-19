package mk.finki.ukim.wp.studentsapi.service;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentInput> getAllStudents();

    Optional<StudentInput> getStudentById(String id);

    void deleteStudentById(String id);

    boolean addStudent(String index, String name, String lastName, String studyProgram);

    boolean modifyStudent(String index, StudentInput modified);

    List<Student> findAllByStudyProgram(Long id);
}
