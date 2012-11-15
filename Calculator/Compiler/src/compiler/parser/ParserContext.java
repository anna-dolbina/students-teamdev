package compiler.parser;

import compiler.lexer.Lexeme;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public interface ParserContext<LexemeType extends Enum,
        ContextState extends Enum,
        Result> {
    public  void init(ArrayList<Lexeme<LexemeType>> lexemeStream, ContextState startState);

    public ContextState getCurrentState()throws NoSuchElementException;
    public void setCurrentState(ContextState state);
    public Lexeme<LexemeType> getCurrentLexeme();

    public  Result getResult();
    public int getCurrentPosition();
}
