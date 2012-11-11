package parser;

import lexer.UnknownLexemException;
import org.junit.Test;
import сalculator.Calculator;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class BracketsTest {
    @Test
    public void BracketsTest() throws Exception{
        String expression="3+(2+3)*(123.45*4)+3*(2*33+(2*3-5*(3-2))-6)";
        try {
            BigDecimal result= Calculator.evaluateExpression(expression);
            System.out.println(result.doubleValue());
            assertTrue("Test failed: invalid calculation",result.doubleValue()==2655.0);
        } catch (UnknownLexemException e) {
            fail("Exception was thrown. " + e.getLocalizedMessage());
        } catch (Exception e) {
            fail("Exception was thrown. " + e.getLocalizedMessage());
        }
    }
}
