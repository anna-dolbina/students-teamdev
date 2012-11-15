package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;
import calculator.element.operator.AdditionBinaryOperator;
import calculator.element.operator.BinaryOperator;

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
        context.setCurrentState(CalculatorContextState.EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION);
        context.moveToNextLexeme();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
