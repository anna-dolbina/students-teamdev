package compiler.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class UnknownLexemeException extends CompilationException{
    public UnknownLexemeException(int position, String context){
        super(position,"Can't recognize lexeme at the beginning of "+context.substring(position));
    }
}
