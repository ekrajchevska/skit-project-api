package mk.finki.ukim.wp.studentsapi.model.exceptions;

public class InvalidIndexFormatException extends RuntimeException {

    String message = "Index not in 6-digit format";

    public InvalidIndexFormatException(){}

    public String getMessage(){
        return message;
    }
}
