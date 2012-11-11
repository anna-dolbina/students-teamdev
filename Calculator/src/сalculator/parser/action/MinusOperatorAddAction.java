package сalculator.parser.action;

import parser.IllegalActionException;
import сalculator.CalculatorParserContext;
import сalculator.MachineState;
import сalculator.parser.operator.BinaryOperator;
import сalculator.parser.operator.SubtractionBinaryOperator;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class MinusOperatorAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new SubtractionBinaryOperator(1);
        context.addOperator(operator);
        context.setCurrentState(MachineState.EXPRESSION_CONTINUE);
        context.setCurrentState(MachineState.MULTIPLICATION_EXPRESSION);
        context.moveToNextLexem();
        return true;
    }
}
