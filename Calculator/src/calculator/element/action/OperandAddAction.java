package calculator.element.action;

import calculator.CalculatorParserContext;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 4:00
 * To change this template use File | Settings | File Templates.
 */
public class OperandAddAction extends AbstractAction {


    @Override
    public boolean performAction(CalculatorParserContext context) {

        context.addOperand(new BigDecimal(Double.parseDouble(context.getCurrentLexeme().getRepresentation()))) ;
        context.moveToNextLexeme();
        return true;
    }
}
