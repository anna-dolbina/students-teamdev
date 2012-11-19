package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorParserContext;
import compiler.exception.IllegalActionException;
import calculator.element.operator.BinaryOperator;
import calculator.element.operator.PowerBinaryOperator;

public class PowerOperatorAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        BinaryOperator operator=new PowerBinaryOperator(3);
        context.addOperator(operator);
        context.setCurrentState(CalculatorContextState.POWER_EXPRESSION_END);
        context.setCurrentState(CalculatorContextState.ATOM_EXPRESSION);
        context.moveToNextLexeme();
        return true;
    }
}
