package сalculator.parser.action;

import parser.IllegalActionException;
import сalculator.CalculatorParserContext;
import сalculator.MachineState;
import сalculator.parser.operator.BinaryOperator;
import сalculator.parser.operator.MultiplicationBinaryOperator;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class MultiplyOperatorAddAction extends AbstractAction {

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new MultiplicationBinaryOperator(2);
        context.addOperator(operator);
        context.setCurrentState(MachineState.MULTIPLICATION_EXPRESSION_CONTINUE);
        context.setCurrentState(MachineState.ATOM_EXPRESSION);
        context.moveToNextLexem();
        return true;
    }
}
