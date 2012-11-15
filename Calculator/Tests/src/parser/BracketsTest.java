package parser;

import calculator.CalculatorImpl;
import compiler.exception.CompilationException;
import compiler.exception.UnknownLexemeException;
import org.junit.Test;

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
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Test failed: invalid calculation",result.doubleValue()==2655.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the brackets processing.\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void FunctionTest() throws Exception{
        String expression="sum(2,3)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);
            System.out.println(result.doubleValue());
            //assertTrue("Test failed: invalid calculation",result.doubleValue()==2655.0);
        } catch (UnknownLexemeException e) {
            fail("Exception was thrown. " + e.getLocalizedMessage());
        } catch (Exception e) {
            fail("Exception was thrown. " + e.getLocalizedMessage());
        }
    }
}
