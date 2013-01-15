package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorLexeme;
import calculator.element.exception.CalculationException;
import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.function.Function;
import calculator.element.function.FunctionFactory;

import java.math.BigDecimal;


public class FunctionAddAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        final String functionRepresentation=context.getCurrentLexeme().getRepresentation();
        final FunctionFactory functionFactory=new FunctionFactory();

        Function function;
        try{
            function= functionFactory.create(functionRepresentation);
        }catch (IllegalStateException e) {
            throw new IllegalActionException("Error when creating function: "+e.getLocalizedMessage());
        }
        context.moveToNextLexeme();
        if(context.getCurrentLexeme().getLexemeType()!= CalculatorLexeme.BRACKET_OPEN)
            throw new IllegalActionException("A function name must be followed with a (");

        context.moveToNextLexeme();
        if(context.getCurrentLexeme().getLexemeType()==CalculatorLexeme.BRACKET_CLOSE){
            try {
                BigDecimal[] args=new BigDecimal[0];
                BigDecimal value=function.calculate(args);
                context.addOperand(value);
                context.moveToNextLexeme();
            } catch (CalculationException e) {
                throw new IllegalActionException("Function with no arguments processing error:"+e.getLocalizedMessage());
            }
            return true;
        }
        context.addFunction(function);

        context.setCurrentState(CalculatorContextState.ARGUMENT_EXPRESSION_CONTINUE);
        context.setCurrentState(CalculatorContextState.EXPRESSION);
        context.openFunctionBracket();




        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
