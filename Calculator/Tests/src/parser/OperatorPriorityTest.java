package parser;

import calculator.CalculatorImpl;
import compiler.exception.UnknownLexemeException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class OperatorPriorityTest {
    @Test
    public void OperatorPriorityTest() throws Exception{
        String expression="3+123.45*4+3*(2*33+(2*3-5*(3-2))-6)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);
            System.out.println(result.doubleValue());
            assertTrue("Test failed: invalid calculation",result.doubleValue()==679.8);
        } catch (UnknownLexemeException e) {
            fail("Exception was thrown. " + e.getLocalizedMessage());
        }
    }
}
