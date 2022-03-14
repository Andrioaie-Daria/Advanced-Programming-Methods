package exceptions;

public class InvalidAssignmentException extends Exception {
    public InvalidAssignmentException(){
        super("The types of the variable and the assigning value do not match.");
    }
    public InvalidAssignmentException(String message){
        super(message);
    }
}
