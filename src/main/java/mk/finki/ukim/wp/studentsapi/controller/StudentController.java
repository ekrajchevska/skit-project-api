package mk.finki.ukim.wp.studentsapi.controller;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.exceptions.ParameterMissingException;
import mk.finki.ukim.wp.studentsapi.model.exceptions.StudentNotFoundException;
import mk.finki.ukim.wp.studentsapi.model.exceptions.StudyProgramNotFoundException;
import mk.finki.ukim.wp.studentsapi.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentServiceImpl studentServiceImpl;

    public StudentController(){}

    @Autowired
    public StudentController(StudentServiceImpl studentServiceImpl){this.studentServiceImpl = studentServiceImpl;}

    // 1)
    // TESTED
    @GetMapping("/students")
    public ResponseEntity<List<StudentInput>> getAllStudents(){
        return new ResponseEntity<>(this.studentServiceImpl.getAllStudents(), HttpStatus.OK); //200
    }

    // 2)
    // TESTED
    @GetMapping("/students/{index}")
    public ResponseEntity<StudentInput> getStudentByIndex(@PathVariable String index){
        return this.studentServiceImpl.getStudentById(index)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3)
    // TESTED
    @GetMapping("/students/by_study_program/{id}")
    public ResponseEntity<List<Student>> getStudentsByStudyProgram(@PathVariable Long id){
        List<Student> students = this.studentServiceImpl.findAllByStudyProgram(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 4)
    // TESTED
    @PostMapping("/students")
    public ResponseEntity addStudent(HttpServletResponse response, @RequestBody StudentInput s) throws ParameterMissingException,
            StudentNotFoundException, StudyProgramNotFoundException{

        if(this.studentServiceImpl.addStudent(s.index, s.name, s.lastName, s.studyProgram)) {
            response.setStatus(200);
        }else{
            response.setHeader("Location", "localhost:8080/api/students/{index}");
            response.setStatus(201);
        }

        return this.studentServiceImpl.getStudentById(s.index).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5)
    // TESTED
    @PutMapping("/students/{index}")
    public ResponseEntity<Student> modifyStudent(@PathVariable String index, @RequestBody StudentInput student){
        if(this.studentServiceImpl.modifyStudent(index, student))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    // 6)
    // TESTED
    @DeleteMapping("/students/{index}")
    public ResponseEntity<StudentInput> deleteStudentByIndex(@PathVariable String index){

       if(this.studentServiceImpl.getStudentById(index).isPresent()) {
           StudentInput deleted = this.studentServiceImpl.getStudentById(index).get();
           this.studentServiceImpl.deleteStudentById(index);
           return new ResponseEntity<>(deleted,HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
