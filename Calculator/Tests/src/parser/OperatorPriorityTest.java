package parser;

import calculator.CalculatorImpl;
import compiler.exception.CompilationException;
import compiler.exception.UnknownLexemeException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class OperatorPriorityTest {
    @Test
    public void simpleOperatorPriorityTest() throws Exception{
        String expression="2+2*2";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Operator priority test failed: invalid calculation of the expression "+expression,result.doubleValue()==6.0);
        } catch (CompilationException e) {
            fail("Exception was thrown. Something is wrong with binary operators processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage() );
        }
    }
    @Test
    public void complexOperatorPriorityTest() throws Exception{
        String expression="3+123.45*4+3*(2*33+(2*3-5*(3-2))-6)";
        try {
            BigDecimal result= new CalculatorImpl().evaluate(expression);

            assertTrue("Complex operator priority test failed: invalid calculation",result.doubleValue()==679.8);
        } catch (UnknownLexemeException e) {
            fail("Exception was thrown.Something is wrong with binary operators processing.\nExpression: "+expression+"\nDetails: " + e.getLocalizedMessage());
        }
    }
}
