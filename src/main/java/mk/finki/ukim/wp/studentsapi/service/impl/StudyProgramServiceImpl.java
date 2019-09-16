package mk.finki.ukim.wp.studentsapi.service.impl;

import mk.finki.ukim.wp.studentsapi.model.Student;
import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import mk.finki.ukim.wp.studentsapi.repository.StudentRepository;
import mk.finki.ukim.wp.studentsapi.repository.StudyProgramRepository;
import mk.finki.ukim.wp.studentsapi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyProgramServiceImpl implements StudyProgramService {

    private StudyProgramRepository studyProgramRepository;
    private StudentRepository studentRepository;

    public StudyProgramServiceImpl(){}

    @Autowired
    public StudyProgramServiceImpl(StudyProgramRepository studyProgramRepository,
                                   StudentRepository studentRepository) {
        this.studyProgramRepository = studyProgramRepository;
        this.studentRepository = studentRepository;
    }

    // TESTED
    public Optional<StudyProgram> getStudyProgramById(Long id){
        return this.studyProgramRepository.findById(id);
    }

    // TESTED
    public List<StudyProgram> getAllStudyPrograms(){
        return this.studyProgramRepository.findAll();
    }

    // TESTED
    public boolean addStudyProgram(String name){
        StudyProgram exists = this.studyProgramRepository.findByName(name);
        if(exists!=null) return false;
        this.studyProgramRepository.save(new StudyProgram(name));
        return true;
    }

    // TESTED
    public boolean deleteStudyProgram(Long id){
        Optional<List<Student>> studentsOfSp = this.studentRepository.findAllByStudyProgram(id);
        if(!studentsOfSp.isPresent())
            return false;

        if(studentsOfSp.get().size() == 0)
            return false;

        this.studyProgramRepository.deleteById(id);
        return true;
    }

    // TESTED
    public boolean updateStudyProgram(Long id,String modified){
        Optional<StudyProgram> sp = this.studyProgramRepository.findById(id);
        if(!sp.isPresent()) return false;

        StudyProgram newStudyProgram = sp.get();
        if(modified == null || this.studyProgramRepository.findByName(modified) != null)
            return false;

        newStudyProgram.setName(modified);
        this.studyProgramRepository.save(newStudyProgram);
        return true;
    }


}
