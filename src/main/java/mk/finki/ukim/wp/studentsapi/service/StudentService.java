package mk.finki.ukim.wp.studentsapi.service;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.repository.StudentRepository;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService  {

    private StudentRepository studentRepository;

    private StudyProgramRepository studyProgramRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudyProgramRepository studyProgramRepository){
        this.studentRepository = studentRepository;
        this.studyProgramRepository = studyProgramRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(String id){
        return this.studentRepository.findById(id);
    }

    public void deleteStudentById(String id){
        this.studentRepository.deleteById(id);
    }

    public boolean addStudent(String index, String name, String lastName, Long studyProgram) {

        if(this.studentRepository.findById(index).isPresent()) return false;
        this.studentRepository.save(new Student(index,name,lastName,studyProgram));
        return true;

    }

    public List<Student> findAllByStudyProgram(Long id){
        return this.studentRepository.findAllByStudyProgram(id);
    }

    public StudyProgram findByName(String nameStudyProgram){
        return this.studyProgramRepository.findByName(nameStudyProgram);
    }



}
