package calculator.element.action;

import calculator.CalculatorContextState;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.operator.BinaryOperator;
import calculator.element.operator.SubtractionBinaryOperator;


public class MinusOperatorAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new SubtractionBinaryOperator(1);
        context.addOperator(operator);
        context.setCurrentState(CalculatorContextState.EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION);
        context.moveToNextLexeme();
        return true;
    }
}
