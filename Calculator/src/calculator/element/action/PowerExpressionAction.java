package calculator.element.action;

import calculator.CalculatorContextState;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;


public class PowerExpressionAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        context.setCurrentState(CalculatorContextState.POWER_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.ATOM_EXPRESSION);
        return true;
    }
}
