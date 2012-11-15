package compiler.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 15.11.12
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */
public class UnknownActionException extends Exception {
    public UnknownActionException(String message){
        super("Can't find corresponding action in table:\n"+message);
    }
}
