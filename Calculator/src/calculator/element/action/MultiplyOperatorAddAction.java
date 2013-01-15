package calculator.element.action;

import calculator.CalculatorContextState;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.operator.BinaryOperator;
import calculator.element.operator.MultiplicationBinaryOperator;


public class MultiplyOperatorAddAction extends AbstractAction {

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new MultiplicationBinaryOperator(2);
        context.addOperator(operator);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.ATOM_EXPRESSION);
        context.moveToNextLexeme();
        return true;
    }
}
