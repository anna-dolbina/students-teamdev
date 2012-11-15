package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 4:50
 * To change this template use File | Settings | File Templates.
 */
public class BracketOpenAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) {
        context.openBracket();
        context.setCurrentState(CalculatorContextState.EXPRESSION);
        context.moveToNextLexeme();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
