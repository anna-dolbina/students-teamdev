package parser;


/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public interface Action<Lexem extends Enum,
        NonTerminal extends Enum,
        Context extends ParserContext<Lexem,NonTerminal,Result>,
        Result> {
    public boolean performAction(Context context) throws IllegalActionException;
}
