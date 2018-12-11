package mk.finki.ukim.wp.studentsapi.repository;

import mk.finki.ukim.wp.studentsapi.model.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyProgramRepository extends JpaRepository<StudyProgram,Long> {

    StudyProgram findByName(String studyProgram);

}
