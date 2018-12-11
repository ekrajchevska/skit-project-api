package mk.finki.ukim.wp.studentsapi.model.exceptions;

public class ParameterMissingException extends RuntimeException{

    private String message = "Missing request parameter";

    public ParameterMissingException(){}

    public String getMessage(){
        return message;
    }
}
