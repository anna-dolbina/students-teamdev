package сalculator.parser.action;

import parser.Action;
import сalculator.CalculatorLexem;
import сalculator.CalculatorParserContext;
import сalculator.MachineState;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 2:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAction implements Action<CalculatorLexem,MachineState,CalculatorParserContext,BigDecimal> {
}
