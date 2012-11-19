package calculator.element.action;

import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.exception.CalculationException;


public class ApplyOperatorAction extends AbstractAction {
    private int priority=1;
    public ApplyOperatorAction(int priority) {
        super();
        this.priority=priority;
    }

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
       try {
            context.applyOperatorsWithPriority(priority);
        } catch (CalculationException e) {
            throw new IllegalActionException("Cannot apply operator: "+e.getLocalizedMessage());
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
