package compiler.parser;

import compiler.exception.UnknownActionException;
import compiler.lexer.Lexeme;


public interface ActionTable<LexemeType extends Enum,
        ContextState extends Enum> {
    ContextState getStartContextState();
    Action getAction(Lexeme<LexemeType> lexeme, ContextState controlSymbol) throws UnknownActionException;


}
