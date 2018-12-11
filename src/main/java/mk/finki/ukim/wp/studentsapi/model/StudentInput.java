package mk.finki.ukim.wp.studentsapi.model;

public class StudentInput {
    public String index;
    public String name;
    public String lastName;
    public String studyProgram;

    public StudentInput(){}

    public StudentInput(String index, String name, String lastName, String studyProgram) {
        this.index = index;
        this.name = name;
        this.lastName = lastName;
        this.studyProgram = studyProgram;
    }

}
