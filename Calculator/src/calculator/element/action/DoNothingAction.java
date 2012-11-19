package calculator.element.action;

import calculator.CalculatorParserContext;


public class DoNothingAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) {
        return true;
    }
}
