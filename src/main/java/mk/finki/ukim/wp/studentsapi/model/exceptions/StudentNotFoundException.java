package mk.finki.ukim.wp.studentsapi.model.exceptions;

public class StudentNotFoundException extends RuntimeException {

    private String message = "No student with such index exists";

    public StudentNotFoundException(){}

    public String getMessage(){
        return message;
    }
}
