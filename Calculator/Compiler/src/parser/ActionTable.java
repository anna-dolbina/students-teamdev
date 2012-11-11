package parser;

import utils.Pair;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public interface ActionTable<Lexem extends Enum,
        NonTerminal extends Enum> {
    NonTerminal getStartNonTerminal();
    Action getAction(Pair<Lexem, String> lexem, NonTerminal controlSymbol) throws IllegalStateException;
}
