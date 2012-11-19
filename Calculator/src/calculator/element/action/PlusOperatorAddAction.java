package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;
import calculator.element.operator.AdditionBinaryOperator;
import calculator.element.operator.BinaryOperator;


public class PlusOperatorAddAction extends AbstractAction {


    @Override
    public boolean performAction(CalculatorParserContext context) {
        BinaryOperator operator=new AdditionBinaryOperator(1);
        context.addOperator(operator);
        context.setCurrentState(CalculatorContextState.EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION);
        context.moveToNextLexeme();
        return true;
    }
}
