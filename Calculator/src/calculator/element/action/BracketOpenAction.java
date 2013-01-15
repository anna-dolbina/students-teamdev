package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;


public class BracketOpenAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) {
        context.openBracket();
        context.setCurrentState(CalculatorContextState.EXPRESSION);
        context.moveToNextLexeme();
        return true;
    }
}
