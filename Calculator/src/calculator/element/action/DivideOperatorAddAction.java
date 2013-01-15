package calculator.element.action;

import calculator.CalculatorContextState;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.operator.BinaryOperator;
import calculator.element.operator.DivisionBinaryOperator;


public class DivideOperatorAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new DivisionBinaryOperator(2);
        context.addOperator(operator);
        context.setCurrentState(CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.ATOM_EXPRESSION);
        context.moveToNextLexeme();
        return true;
    }
}
