package mk.finki.ukim.wp.studentsapi.model.exceptions;

public class StudyProgramNotFoundException extends RuntimeException{

    private String message = "Study program does not exist";

    public StudyProgramNotFoundException(){}

    public String getMessage(){
        return message;
    }
}
