package mk.finki.ukim.wp.studentsapi.service.impl;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.repository.StudentRepository;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import mk.finki.ukim.wp.studentsapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private StudyProgramRepository studyProgramRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudyProgramRepository studyProgramRepository){
        this.studentRepository = studentRepository;
        this.studyProgramRepository = studyProgramRepository;
    }

    public List<StudentInput> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentInput> studentInputs = new ArrayList<>(students.size());
        for(int i=0;i<students.size();i++) {
            Optional<StudyProgram> sp = this.studyProgramRepository.findById(students.get(i).getStudyProgram());
            if(sp.isPresent()) {
                studentInputs.add(new StudentInput(
                        students.get(i).getIndex(),
                        students.get(i).getName(),
                        students.get(i).getLastName(),
                        sp.get().getName()));
            }
        }
        return studentInputs;
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

    public void modifyStudent(Student modified){
        Optional<Student> s = this.studentRepository.findById(modified.getIndex());
        if(s.isPresent()){
            Student student = s.get();
            if(modified.getName()==null)
                modified.setName(student.getName());
            if(modified.getLastName()==null)
                modified.setLastName(student.getLastName());
            if(modified.getStudyProgram()==null)
                modified.setStudyProgram(student.getStudyProgram());

            this.studentRepository.save(modified);
        }
    }

    public List<Student> findAllByStudyProgram(Long id){
        return this.studentRepository.findByStudyProgram(id);
    }

    public StudyProgram findByName(String nameStudyProgram){
        return this.studyProgramRepository.findByName(nameStudyProgram);
    }




}
