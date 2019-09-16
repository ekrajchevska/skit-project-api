package mk.finki.ukim.wp.studentsapi.service;

import mk.finki.ukim.wp.studentsapi.model.StudyProgram;

import java.util.List;

public interface StudyProgramService {

    List<StudyProgram> getAllStudyPrograms();

    boolean addStudyProgram(String name);

    boolean deleteStudyProgram(Long id);

    boolean updateStudyProgram(Long id, String studyProgram);

}
