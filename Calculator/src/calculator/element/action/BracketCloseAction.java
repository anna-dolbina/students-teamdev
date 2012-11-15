package calculator.element.action;

import compiler.exception.IllegalActionException;
import calculator.CalculatorParserContext;
import calculator.element.exception.CalculationException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class BracketCloseAction extends AbstractAction {
    private int priority;
    public BracketCloseAction(int priority) {
        super();
        this.priority=priority;
    }

    @Override
    public boolean performAction(CalculatorParserContext context) throws IllegalActionException {
        try {
            context.closeBracket();
            context.applyOperatorsWithPriority(priority);
        } catch (CalculationException e) {
            throw new IllegalActionException("Cannot close bracket: "+e.getLocalizedMessage());
        }
        context.moveToNextLexeme();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
