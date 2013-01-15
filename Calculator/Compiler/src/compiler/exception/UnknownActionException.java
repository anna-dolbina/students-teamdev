package compiler.exception;


public class UnknownActionException extends Exception {
    public UnknownActionException(String message){
        super("Can't understand the expression:\n"+message);
    }
}
