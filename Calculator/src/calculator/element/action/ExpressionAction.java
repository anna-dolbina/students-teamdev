package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 2:43
 * To change this template use File | Settings | File Templates.
 */
public class ExpressionAction extends AbstractAction {
    @Override

    public boolean performAction(CalculatorParserContext context) {
        context.setCurrentState(CalculatorContextState.EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
