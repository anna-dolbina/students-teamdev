package compiler.parser;

import compiler.lexer.Lexeme;

import java.util.ArrayList;
import java.util.NoSuchElementException;


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
