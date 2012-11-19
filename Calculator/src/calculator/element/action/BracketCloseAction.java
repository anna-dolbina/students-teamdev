package calculator.element.action;

import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.exception.CalculationException;


public class BracketCloseAction extends AbstractAction {
    private int priority;
    public BracketCloseAction(int priority) {
        super();
        this.priority=priority;
    }

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        try {
            if(context.isLastFunctionBracketOpened()){
                context.applyOperatorsWithPriority(priority);
                return true;
            }
            context.closeBracket();
            context.applyOperatorsWithPriority(priority);
        } catch (CalculationException e) {
            throw new IllegalActionException("Cannot close bracket: "+e.getLocalizedMessage());
        }
        context.moveToNextLexeme();
        return true;
    }
}
