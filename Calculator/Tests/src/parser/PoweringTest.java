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
public class PoweringTest {
    @Test
    public void PowerTest() throws Exception{
        String expression="33+3^(1+2^2)+5";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);
            System.out.println(result.doubleValue());
            assertTrue("Test failed: invalid calculation",result.doubleValue()==281.0);
        } catch (UnknownLexemeException e) {
            fail("Exception was thrown. " + e.getLocalizedMessage());
        }
    }
}
