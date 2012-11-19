package compiler.lexer;

import compiler.exception.UnknownLexemeException;

/**
 *
 *
 */
public interface LexemeRecognizer<LexemeType extends Enum> {
    /**
     *
     *
     * @param context An input string. The recognizer must try to find a lexeme at he beginning of this string.
     *                If the string is empty, it must return a lexeme, which marks the end of lexeme stream
     * @param currentPosition  The current position in context
     * @return a lexeme the input string begins with
     * @throws compiler.exception.UnknownLexemeException  - if the lexeme is not recognized
     */
    Lexeme<LexemeType> recognizeLexeme(String context, int currentPosition) throws UnknownLexemeException;
    /**
     * @return lexeme which will mark the end of lexeme stream
     * */
    Lexeme<LexemeType> getEndOfInputLexeme();
}
