package сalculator.parser.action;

import parser.IllegalActionException;
import сalculator.CalculatorParserContext;
import сalculator.MachineState;

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
        context.setCurrentState(MachineState.POWER_EXPRESSION_CONTINUE);
        context.setCurrentState(MachineState.ATOM_EXPRESSION);
        return true;
    }
}
