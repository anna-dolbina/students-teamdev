package lexer;

import utils.Pair;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 *
 * Интерфейс, который должен реализовывать распознаватель лексем.
 * Распознается лексема, с которой начинается строка.
 * Если лексема не распознана - исключение UnknownLexemException
 */
public interface LexemRecognizer<Lexem extends Enum> {

    Pair<Lexem, String> recognizeLexem(String context) throws UnknownLexemException;
    Pair<Lexem, String> getEndOfInputLexem();
}
