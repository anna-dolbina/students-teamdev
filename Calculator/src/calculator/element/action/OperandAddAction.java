package calculator.element.action;

import calculator.CalculatorParserContext;

import java.math.BigDecimal;


public class OperandAddAction extends AbstractAction {


    @Override
    public boolean performAction(CalculatorParserContext context) {

        context.addOperand(new BigDecimal(Double.parseDouble(context.getCurrentLexeme().getRepresentation()))) ;
        context.moveToNextLexeme();
        return true;
    }
}
