package compiler.lexer;

import compiler.exception.UnknownLexemeException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 14.11.12
 * Time: 2:17
 * To change this template use File | Settings | File Templates.
 */
public interface Lexer<LexemeType extends Enum> {
    public ArrayList<Lexeme<LexemeType>> generateLexemeStream(String input) throws UnknownLexemeException;
}
