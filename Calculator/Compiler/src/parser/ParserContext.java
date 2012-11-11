package parser;

import utils.Pair;

import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class ParserContext<Lexem extends Enum,
        NonTerminal extends Enum,
        Result> {
    public abstract void init(NonTerminal startState);

    public abstract NonTerminal getCurrentState()throws NoSuchElementException;
    public abstract void setCurrentState(NonTerminal state);
    public abstract Pair<Lexem,String> getCurrentLexem();

    public abstract Result getResult();


}
