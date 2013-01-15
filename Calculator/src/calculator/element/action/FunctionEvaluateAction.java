package calculator.element.action;

import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.exception.CalculationException;


public class FunctionEvaluateAction extends AbstractAction {

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        try{
        context.closeFunctionBracket();

        }catch (CalculationException e){
            throw new IllegalActionException("Error occurred when evaluating function: \n" +e.getLocalizedMessage());
        }
        context.moveToNextLexeme();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
