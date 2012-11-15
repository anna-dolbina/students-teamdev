package calculator.element.action;

import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.exception.CalculationException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 13.11.12
 * Time: 0:05
 * To change this template use File | Settings | File Templates.
 */
public class FunctionEvaluateAction extends AbstractAction {

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        try{
        context.closeFunctionBracket();
        context.closeBracket();
        }catch (CalculationException e){
            throw new IllegalActionException("Error occured when evaluating function: " +e.getLocalizedMessage());
        }
        context.moveToNextLexeme();
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
