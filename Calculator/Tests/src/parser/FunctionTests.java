package parser;

import calculator.Calculator;
import calculator.CalculatorImpl;
import compiler.exception.CompilationException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class FunctionTests {
    private final Calculator calculator=new CalculatorImpl();
    @Test
    public void simpleFunctionTest() throws Exception{
        String expression="sum(2*2,3,8+5)";
            try {

                BigDecimal result= calculator.evaluate(expression);

                assertTrue("Test failed: invalid calculation of the expression with a function: "+expression,result.doubleValue()==20.0);
            } catch (CompilationException e) {
                fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
            }

    }
    @Test
    public void functionWithInnerBracketsTest() throws Exception{
        String expression="sum(2*(2+1),(5+5)*(6+6))";

            try {

                BigDecimal result= calculator.evaluate(expression);

                assertTrue("Test failed: invalid calculation of the expression with a function containing inner brackets: "+expression,result.doubleValue()==126.0);
            } catch (CompilationException e) {
                fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
            }

    }
    @Test
    public void functionInContextTest() throws Exception{
        String expression="3*sum(2*(2+1),(5+5)*(6+6))+11";

            try {

                    BigDecimal result= calculator.evaluate(expression);

                assertTrue("Test failed: invalid calculation of the expression with a function containing inner brackets: "+expression,result.doubleValue()==389.0);
            } catch (CompilationException e) {
                fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
            }

    }
    @Test
    public void functionWithInnerFunctionsTest() throws Exception{
        String expression="sum(min(4,min(2,8),5),max(2*(3+5),11))";

            try {

                    BigDecimal result= calculator.evaluate(expression);

                assertTrue("Test failed: invalid calculation of the expression with a function containing inner brackets: "+expression,result.doubleValue()==18.0);
            } catch (CompilationException e) {
                fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
            }

    }
    @Test
    public void complexFunctionTest() throws Exception{
        String expression="3+sum(min(4,min(2,8),5),max(2*(3+5),11)/2+4,13*4+5)";

        try {

            BigDecimal result= calculator.evaluate(expression);

            assertTrue("Test failed: invalid calculation of the expression with a function containing inner brackets: "+expression,result.doubleValue()==74.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void squareRootTest() throws Exception{
        String expression="3+sqrt(max(4,min(2,8),9))";

        try {

            BigDecimal result= calculator.evaluate(expression);

            assertTrue("Test failed: invalid calculation of the expression with a square root function: "+expression,result.doubleValue()==6.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void piFunctionTest() throws Exception{
        String expression="3+sqrt(max(4,2*pi()+1,9))";

        try {

            BigDecimal result= calculator.evaluate(expression);

            assertTrue("Test failed: invalid calculation of the expression with a square root function: "+expression,result.doubleValue()==6.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the function processing.\nDetails: " + e.getLocalizedMessage() );
        }
    }
}
