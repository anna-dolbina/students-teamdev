package parser;

import calculator.CalculatorImpl;
import compiler.exception.CompilationException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class BracketsTest {
    @Test
    public void emptyBracketsTest(){
        String expression="5+()";

        try {
            new CalculatorImpl().evaluate(expression);
            fail("Exception wasn't thrown when testing empty brackets");
        } catch (CompilationException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    @Test
    public void singleValueInBracketsTest() throws Exception{
        String expression="3*(25)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Single value in brackets test failed: invalid calculation of the expression "+expression,result.doubleValue()==75.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the brackets processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void simpleBracketsTest() throws Exception{
        String expression="3*(2+3)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Simple brackets test failed: invalid calculation of the expression "+expression,result.doubleValue()==15.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the brackets processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void innerBracketsTest() throws Exception{
        String expression="4*(2+3*(6+7))";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Inner brackets test failed: invalid calculation of the expression "+expression,result.doubleValue()==164.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the brackets processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void bracketsSequenceTest() throws Exception{
        String expression="(2+3)*(3+5)+(4-8)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Brackets sequence test failed: invalid calculation of the expression "+expression,result.doubleValue()==36.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the brackets processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void complexBracketsTest() throws Exception{
        String expression="3+(2+3)*(123.45*4)+3*(2*33+(2*3-5*(3-2))-6)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Complex brackets test failed: invalid calculation of the expression "+expression,result.doubleValue()==2655.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with the brackets processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
}
