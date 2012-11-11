package сalculator.parser.action;

import сalculator.CalculatorParserContext;
import сalculator.MachineState;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 4:50
 * To change this template use File | Settings | File Templates.
 */
public class BracketOpenAction extends AbstractAction {
    @Override
    public boolean performAction(CalculatorParserContext context) {
        context.openBracket();
        context.setCurrentState(MachineState.EXPRESSION);
        context.moveToNextLexem();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
