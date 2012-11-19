package compiler.exception;


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
