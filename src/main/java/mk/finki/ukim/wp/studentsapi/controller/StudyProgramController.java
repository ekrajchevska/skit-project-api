package mk.finki.ukim.wp.studentsapi.controller;

import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudyProgramController {

    private StudyProgramService studyProgramService;

    public StudyProgramController(){}

    @Autowired
    public StudyProgramController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    // 7)
    @GetMapping("/study_programs")
    public ResponseEntity<List<StudyProgram>> getStudyPrograms(){
        List<StudyProgram> studyPrograms = this.studyProgramService.getAllStudyPrograms();
        return new ResponseEntity<>(studyPrograms, HttpStatus.OK);
    }

    // 8)
    @PostMapping("/study_programs")
    public void addStudyProgram(@RequestBody StudyProgram studyProgram){
        this.studyProgramService.addStudyProgram(studyProgram.getName());
    }

    // 9)
    @DeleteMapping("/study_programs/{id}")
    public void deleteStudyProgram(@PathVariable Long id){
        this.studyProgramService.deleteStudyProgram(id);
    }


}
