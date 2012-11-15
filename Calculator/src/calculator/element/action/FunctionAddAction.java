package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorLexeme;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.function.Function;
import calculator.element.function.FunctionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 12.11.12
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */
public class FunctionAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        final String functionRepresentation=context.getCurrentLexeme().getRepresentation();
        final FunctionFactory functionFactory=new FunctionFactory();
        System.out.println("Function "+functionRepresentation+" found");
        Function function;
        try{
            function= functionFactory.create(functionRepresentation);
        }catch (IllegalStateException e) {
            throw new IllegalActionException("Error when creating function: "+e.getLocalizedMessage());
        }
        context.moveToNextLexeme();
        if(context.getCurrentLexeme().getLexemeType()!= CalculatorLexeme.BRACKET_OPEN)
            throw new IllegalActionException("A function name must be followed with a (");

        context.addFunction(function);
        System.out.println("Function added");
        context.setCurrentState(CalculatorContextState.ARGUMENT_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.EXPRESSION);
        context.openFunctionBracket();
        context.openBracket();

        context.moveToNextLexeme();

        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
