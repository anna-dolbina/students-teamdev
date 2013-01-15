package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;


public class ExpressionAction extends AbstractAction {
    @Override

    public boolean performAction(CalculatorParserContext context) {
        context.setCurrentState(CalculatorContextState.EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION);
        return true;
    }
}
