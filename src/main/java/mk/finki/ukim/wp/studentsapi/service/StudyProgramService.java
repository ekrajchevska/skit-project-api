package mk.finki.ukim.wp.studentsapi.service;

import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyProgramService {

    private StudyProgramRepository studyProgramRepository;

    public StudyProgramService(){}

    @Autowired
    public StudyProgramService(StudyProgramRepository studyProgramRepository) {
        this.studyProgramRepository = studyProgramRepository;
    }

    public List<StudyProgram> getAllStudyPrograms(){
        return this.studyProgramRepository.findAll();
    }

    public void addStudyProgram(String name){
        StudyProgram exists = this.studyProgramRepository.findByName(name);
        if(exists!=null) return;
        this.studyProgramRepository.save(new StudyProgram(name));
    }

    public void deleteStudyProgram(Long id){
        this.studyProgramRepository.deleteById(id);
    }


}
