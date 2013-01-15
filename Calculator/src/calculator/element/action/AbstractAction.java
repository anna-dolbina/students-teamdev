package calculator.element.action;

import calculator.CalculatorContextState;
import calculator.CalculatorLexeme;
import compiler.parser.Action;
import calculator.CalculatorParserContext;

import java.math.BigDecimal;

public abstract class AbstractAction implements Action<CalculatorLexeme,CalculatorContextState,CalculatorParserContext,BigDecimal> {
}
