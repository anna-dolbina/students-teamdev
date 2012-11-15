package parser;

import calculator.CalculatorImpl;
import compiler.exception.CompilationException;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 */
public class DivisionTest {
    @Test
    public void divisionByZeroTest() throws Exception{
        String expression="5/0";

        try {
            new CalculatorImpl().evaluate(expression);
            fail("Exception wasn't thrown");
        } catch (CompilationException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
