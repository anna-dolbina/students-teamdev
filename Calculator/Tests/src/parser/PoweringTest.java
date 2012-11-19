package parser;

import calculator.CalculatorImpl;
import compiler.exception.CompilationException;
import compiler.exception.UnknownLexemeException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PoweringTest {
    @Test
    public void simplePoweringTest() throws Exception{
        String expression="2^5";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Simple powering test failed.\nInvalid calculation of the expression "+expression,result.doubleValue()==32.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the power processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void poweringPriorityTest() throws Exception{
        String expression="2^5*8";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Powering priority test failed.\nInvalid calculation of the expression "+expression,result.doubleValue()==256.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the power processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void noPowerTest(){
        String expression="5^*3";

        try {
            new CalculatorImpl().evaluate(expression);
            fail("Exception wasn't thrown when calculating expression with missing power");
        } catch (CompilationException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    @Test
    public void poweringBracketsTest() throws Exception{
        String expression="(1+1)^(3+2)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Powering brackets test failed.\nInvalid calculation of the expression "+expression,result.doubleValue()==32.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the power processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }

    @Test
    public void complexPoweringTest() throws Exception{
        String expression="33+3^(1+(2)^2)+5";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Complex powering test failed: invalid calculation",result.doubleValue()==281.0);
        } catch (UnknownLexemeException e) {
            fail("Exception was thrown.Something is wrong with the power processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage());
        }
    }
}
