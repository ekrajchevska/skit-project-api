package mk.finki.ukim.wp.studentsapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="programs")
public class StudyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    public StudyProgram(){}

    public StudyProgram(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudyProgram{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
