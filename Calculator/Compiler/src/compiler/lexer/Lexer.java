package compiler.lexer;

import compiler.exception.UnknownLexemeException;

import java.util.ArrayList;


public interface Lexer<LexemeType extends Enum> {
    public ArrayList<Lexeme<LexemeType>> generateLexemeStream(String input) throws UnknownLexemeException;
}
