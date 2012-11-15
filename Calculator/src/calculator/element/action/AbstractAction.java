package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorLexeme;
import compiler.parser.Action;
import calculator.CalculatorParserContext;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 2:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAction implements Action<CalculatorLexeme,CalculatorContextState,CalculatorParserContext,BigDecimal> {
}
