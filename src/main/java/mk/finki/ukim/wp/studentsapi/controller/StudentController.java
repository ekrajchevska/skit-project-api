package mk.finki.ukim.wp.studentsapi.controller;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudentInput;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.model.exceptions.InvalidIndexFormatException;
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
import java.util.Optional;

@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentServiceImpl studentServiceImpl;

    public StudentController(){}

    @Autowired
    public StudentController(StudentServiceImpl studentServiceImpl){this.studentServiceImpl = studentServiceImpl;}

    // 1)
    @GetMapping("/students")
    public ResponseEntity<List<StudentInput>> getAllStudents(){
        List<StudentInput> students = this.studentServiceImpl.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK); //200
    }

    // 2)
    @GetMapping("/students/{index}")
    public ResponseEntity<Student> getStudentByIndex(@PathVariable String index){
        Optional<Student> student = this.studentServiceImpl.getStudentById(index);
        if(student.isPresent())
            return new ResponseEntity<>(student.get(),HttpStatus.OK); //200
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  //404
    }

    // 3)
    @GetMapping("/students/by_study_program/{id}")
    public ResponseEntity<List<Student>> getStudentsByStudyProgram(@PathVariable Long id){
        List<Student> students = this.studentServiceImpl.findAllByStudyProgram(id);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 4)
    @PostMapping("/students")
    public void addStudent(HttpServletResponse response, @RequestBody StudentInput s) throws ParameterMissingException{
        if(s.index==null || s.name==null || s.lastName==null || s.studyProgram==null){
            response.setStatus(400);
            throw new ParameterMissingException();
        }

        int index = Integer.parseInt(s.index);
        if(index/100000==0) {
            response.setStatus(400);
            throw new InvalidIndexFormatException();
        }

        StudyProgram studyProgram = this.studentServiceImpl.findByName(s.studyProgram);
        if(studyProgram==null) {
            response.setStatus(400);
            throw new StudyProgramNotFoundException();
        }

        if(this.studentServiceImpl.addStudent(s.index,s.name,s.lastName,studyProgram.getId())) {
            response.setHeader("Location", "localhost:8080/students/"+s.index);
            response.setStatus(201);
        }else{
            response.setStatus(409); // HttpStatus.CONFLICT
        }
    }

    // 5)
    @PutMapping("/students/{index}")
    public ResponseEntity<Student> modifyStudent(HttpServletResponse response,
                                                 @PathVariable String index,
                                                 @RequestBody StudentInput student){

        Optional<Student> s = this.studentServiceImpl.getStudentById(index);
        if(s.isPresent()){
            Student studentToModify = s.get();

            studentToModify.setIndex(index);
            if(student.name!=null) studentToModify.setName(student.name);
            if(student.lastName!=null) studentToModify.setLastName(student.lastName);
            if(student.studyProgram!=null){
                StudyProgram studyProgram = this.studentServiceImpl.findByName(student.studyProgram);
                if(studyProgram==null) {
                    response.setStatus(400);
                    throw new StudyProgramNotFoundException();
                }
                else studentToModify.setStudyProgram(studyProgram.getId());
            }
            this.studentServiceImpl.modifyStudent(studentToModify);
            return new ResponseEntity<>(studentToModify,HttpStatus.OK);
        }
        else{
            response.setStatus(404);
           throw new StudentNotFoundException();
        }
    }

    // 6)
    @DeleteMapping("/students/{index}")
    public ResponseEntity<Student> deleteStudentByIndex(@PathVariable String index){
        Optional<Student> student = this.studentServiceImpl.getStudentById(index);
        if(student.isPresent()) {
            this.studentServiceImpl.deleteStudentById(index);
            return new ResponseEntity<>(student.get(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
