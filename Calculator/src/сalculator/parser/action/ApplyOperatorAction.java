package сalculator.parser.action;

import parser.IllegalActionException;
import сalculator.CalculatorParserContext;
import сalculator.parser.exception.CalculationException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
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
