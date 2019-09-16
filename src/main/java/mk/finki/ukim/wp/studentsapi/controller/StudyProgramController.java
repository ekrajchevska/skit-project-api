package mk.finki.ukim.wp.studentsapi.controller;

import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.service.impl.StudyProgramServiceImpl;
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
public class StudyProgramController {

    private StudyProgramServiceImpl studyProgramServiceImpl;

    public StudyProgramController(){}

    @Autowired
    public StudyProgramController(StudyProgramServiceImpl studyProgramServiceImpl) {
        this.studyProgramServiceImpl = studyProgramServiceImpl;
    }

    // TESTED
    @GetMapping("/study_programs/{id}")
    public ResponseEntity<StudyProgram> getStudyProgram(@PathVariable Long id){
        return this.studyProgramServiceImpl.getStudyProgramById(id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TESTED
    @PutMapping("/study_programs/{id}")
    public ResponseEntity<StudyProgram> modifyStudyProgram(@PathVariable Long id,
                                                           @RequestBody String studyProgram){
        if(this.studyProgramServiceImpl.updateStudyProgram(id,studyProgram))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    // 7)
    // TESTED
    @GetMapping("/study_programs")
    public ResponseEntity<List<StudyProgram>> getStudyPrograms(){
        List<StudyProgram> studyPrograms = this.studyProgramServiceImpl.getAllStudyPrograms();
        return new ResponseEntity<>(studyPrograms, HttpStatus.OK);
    }

    // 8)
    // TESTED
    @PostMapping("/study_programs")
    public void addStudyProgram(HttpServletResponse response, @RequestBody StudyProgram studyProgram){
        if(this.studyProgramServiceImpl.addStudyProgram(studyProgram.getName()))
            response.setStatus(200);
        else {
            response.setStatus(403);
            response.setHeader("statusText","Study program with given name already exists");
        }
    }

    // 9)
    // TESTED
    @DeleteMapping("/study_programs/{id}")
    public void deleteStudyProgram(HttpServletResponse response, @PathVariable Long id){

        if(this.studyProgramServiceImpl.deleteStudyProgram(id))
            response.setStatus(200);
        else {
            response.setStatus(403);
            response.setHeader("statusText","Deleting program with enrolled students is forbidden");
        }

    }
}
