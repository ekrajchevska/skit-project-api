package mk.finki.ukim.wp.studentsapi.controller;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.model.exceptions.InvalidIndexFormatException;
import mk.finki.ukim.wp.studentsapi.model.exceptions.ParameterMissingException;
import mk.finki.ukim.wp.studentsapi.model.exceptions.StudyProgramNotFoundException;
import mk.finki.ukim.wp.studentsapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    public StudentController(){}

    @Autowired
    public StudentController(StudentService studentService){this.studentService = studentService;}

    // 1)
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = this.studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK); //200
    }

    // 2)
    @GetMapping("/students/{index}")
    public ResponseEntity<Student> getStudentByIndex(@PathVariable String index){
        Optional<Student> student = this.studentService.getStudentById(index);
        if(student.isPresent())
            return new ResponseEntity<>(student.get(),HttpStatus.OK); //200
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  //404
    }

    // 3)
    @GetMapping("/students/by_study_program/{id}")
    public ResponseEntity<List<Student>> getStudentsByStudyProgram(@PathVariable Long id){
        List<Student> students = this.studentService.findAllByStudyProgram(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 4)
    @PostMapping("/students")
    public void addStudent(HttpServletResponse response, @RequestBody StudentInput s){
        if(s.index==null || s.name==null || s.lastName==null || s.studyProgram==null){
            response.setStatus(400);
            throw new ParameterMissingException();
        }

        int index = Integer.parseInt(s.index);
        if(index/100000==0) {
            response.setStatus(400);
            throw new InvalidIndexFormatException();
        }

        StudyProgram studyProgram = this.studentService.findByName(s.studyProgram);
        if(studyProgram==null) {
            response.setStatus(400);
            throw new StudyProgramNotFoundException();
        }

        if(this.studentService.addStudent(s.index,s.name,s.lastName,studyProgram.getId())) {
            response.setHeader("Location", "localhost:8080/students/"+s.index);
            response.setStatus(201);
        }else{
            response.setStatus(409); // HttpStatus.CONFLICT
        }
    }

    // 6)
    @DeleteMapping("/students/{index}")
    public ResponseEntity<Student> deleteStudentByIndex(@PathVariable String index){
        Optional<Student> student = this.studentService.getStudentById(index);
        if(student.isPresent()) {
            this.studentService.deleteStudentById(index);
            return new ResponseEntity<>(student.get(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
