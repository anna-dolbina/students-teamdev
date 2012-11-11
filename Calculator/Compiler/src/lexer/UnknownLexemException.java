package lexer;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class UnknownLexemException  extends Throwable{
    public UnknownLexemException( String context){
        super("Can't recognize start lexem for "+context);
    }
}
