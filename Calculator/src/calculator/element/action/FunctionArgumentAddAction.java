package calculator.element.action;

import calculator.CalculatorContextState;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 12.11.12
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public class FunctionArgumentAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        context.addArgument();
        context.setCurrentState(CalculatorContextState.ARGUMENT_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.EXPRESSION);
        context.moveToNextLexeme();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
