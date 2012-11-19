package compiler.exception;


public class IllegalActionException extends Exception {
    public IllegalActionException(String message){
        super("Can't perform action:\n"+message);
    }
}
