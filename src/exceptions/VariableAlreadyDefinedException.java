package exceptions;

public class VariableAlreadyDefinedException extends Exception {
    public VariableAlreadyDefinedException(){
        super("The variable is already declared.");
    }
    public VariableAlreadyDefinedException(String message){
        super(message);
    }
}
