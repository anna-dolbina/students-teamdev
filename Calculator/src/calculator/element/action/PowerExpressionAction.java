package calculator.element.action;

import calculator.CalculatorContextState;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class PowerExpressionAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        context.setCurrentState(CalculatorContextState.POWER_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.ATOM_EXPRESSION);
        return true;
    }
}
