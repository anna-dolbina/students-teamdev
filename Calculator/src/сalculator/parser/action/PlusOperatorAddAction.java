package сalculator.parser.action;

import сalculator.CalculatorParserContext;
import сalculator.MachineState;
import сalculator.parser.operator.AdditionBinaryOperator;
import сalculator.parser.operator.BinaryOperator;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class PlusOperatorAddAction extends AbstractAction {


    @Override
    public boolean performAction(CalculatorParserContext context) {
        BinaryOperator operator=new AdditionBinaryOperator(1);
        context.addOperator(operator);
        context.setCurrentState(MachineState.EXPRESSION_CONTINUE);
        context.setCurrentState(MachineState.MULTIPLICATION_EXPRESSION);
        context.moveToNextLexem();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
