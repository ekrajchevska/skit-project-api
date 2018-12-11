package mk.finki.ukim.wp.studentsapi.repository;

import mk.finki.ukim.wp.studentsapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findAllByStudyProgram(Long id);

}
