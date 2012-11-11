package сalculator;

import lexer.UnknownLexemException;
import utils.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    public static BigDecimal evaluateExpression( String expression) throws UnknownLexemException {
        final CalculatorLexer lexer=new CalculatorLexer();
        ArrayList<Pair<CalculatorLexem,String>> lexemStream=lexer.generateLexemStream(expression);
        final CalculatorParserContext context=new CalculatorParserContext(lexemStream);
        final CalculatorParser parser=new CalculatorParser();
        final BigDecimal result=parser.run(context);
        return result;
    }
}
