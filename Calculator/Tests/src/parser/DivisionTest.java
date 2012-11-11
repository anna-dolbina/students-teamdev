package parser;

import lexer.UnknownLexemException;
import org.junit.Test;
import сalculator.Calculator;

import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class DivisionTest {
    @Test
    public void DivisionByZeroTest() throws Exception{
        String expression="5/0";

        try {
            Calculator.evaluateExpression(expression);
            fail("Exception wasn't thrown");
        } catch (IllegalStateException e) {
            System.out.println(e.getLocalizedMessage());
        } catch(UnknownLexemException e){
            e.printStackTrace();
        }
    }
}
