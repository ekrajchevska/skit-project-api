package mk.finki.ukim.wp.studentsapi.service.impl;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.model.exceptions.StudentNotFoundException;
import mk.finki.ukim.wp.studentsapi.model.exceptions.StudyProgramNotFoundException;
import mk.finki.ukim.wp.studentsapi.repository.StudentRepository;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import mk.finki.ukim.wp.studentsapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StudyProgramRepository studyProgramRepository;

    public StudentServiceImpl(){}

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudyProgramRepository studyProgramRepository){
        this.studentRepository = studentRepository;
        this.studyProgramRepository = studyProgramRepository;
    }

    // TESTED
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

    // TESTED
    public Optional<StudentInput> getStudentById(String id){
        Optional<Student> student = this.studentRepository.findById(id);
        if(student.isPresent()){
            Optional<StudyProgram> studyProgram = this.studyProgramRepository.findById(student.get().getStudyProgram());
            if(studyProgram.isPresent())
                return Optional.of(new StudentInput(student.get().getIndex(),student.get().getName(),
                        student.get().getLastName(), studyProgram.get().getName()));
        }
        return Optional.empty();
    }

    // NOT TESTED
    public void deleteStudentById(String id){
        this.studentRepository.deleteById(id);
    }

    // TESTED
    public boolean addStudent(String index, String name, String lastName, String studyProgram) {

        List<Character> indexChars = index.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        if(indexChars.size()!=6) return false;
        if(indexChars.stream().anyMatch(Character::isAlphabetic)) return false;

        if(this.studentRepository.findById(index).isPresent()) throw new StudentNotFoundException();
        StudyProgram studyProgramOfNewStudent = this.studyProgramRepository.findByName(studyProgram);
        if(studyProgramOfNewStudent==null) throw new StudyProgramNotFoundException();
        this.studentRepository.save(new Student(index,name,lastName,studyProgramOfNewStudent.getId()));
        return true;

    }

    // TESTED
    public boolean modifyStudent(String index, StudentInput modified){
        Optional<Student> s = this.studentRepository.findById(index);
        if(s.isPresent()){
            Student student = s.get();
            if(modified.name!=null)
                student.setName(modified.name);
            if(modified.lastName!=null)
                student.setLastName(modified.lastName);
            if(modified.studyProgram!=null)
                student.setStudyProgram(this.studyProgramRepository.findByName(modified.studyProgram).getId());

            this.studentRepository.save(student);
            return true;
        }
        return false;
    }

    // TESTED
    public List<Student> findAllByStudyProgram(Long id){
      if(this.studentRepository.findAllByStudyProgram(id).isPresent())
          return this.studentRepository.findAllByStudyProgram(id).get();
      return null;
    }

}
