package parser;

import lexer.UnknownLexemException;
import org.junit.Test;
import сalculator.Calculator;

import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 4:28
 * To change this template use File | Settings | File Templates.
 */
public class CommonCalculatorTest {
    @Test
    public void EmptyStringTest() throws Exception{
        String expression="";

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
