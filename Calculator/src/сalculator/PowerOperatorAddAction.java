package сalculator;

import parser.IllegalActionException;
import сalculator.parser.action.AbstractAction;
import сalculator.parser.operator.BinaryOperator;
import сalculator.parser.operator.PowerBinaryOperator;


/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class PowerOperatorAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new PowerBinaryOperator(3);
        context.addOperator(operator);
        context.setCurrentState(MachineState.POWER_EXPRESSION_END);
        context.setCurrentState(MachineState.ATOM_EXPRESSION);
        context.moveToNextLexem();
        return true;
    }
}
