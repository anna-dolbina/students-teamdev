package compiler.parser;

import compiler.exception.UnknownActionException;
import compiler.lexer.Lexeme;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public interface ActionTable<LexemeType extends Enum,
        ContextState extends Enum> {
    ContextState getStartContextState();
    Action getAction(Lexeme<LexemeType> lexeme, ContextState controlSymbol) throws UnknownActionException;
}
