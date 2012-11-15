package compiler.exception;

/**
* Created with IntelliJ IDEA.
* User: Администратор
* Date: 14.11.12
* Time: 3:08
* To change this template use File | Settings | File Templates.
*/
public class CompilationException extends Exception {
    private int errorPosition;
    public CompilationException(int errorPosition,String message) {
        super("An error occurred at position "+errorPosition+":\n"+message);
        this.errorPosition=errorPosition;
    }

    public int getErrorPosition() {
        return errorPosition;
    }


}
