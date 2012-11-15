package parser;

import calculator.CalculatorImpl;
import compiler.exception.UnknownLexemeException;
import org.junit.Test;

import static org.junit.Assert.fail;

/**

 */
public class CommonCalculatorTest {
    @Test
    public void EmptyStringTest() throws Exception{
        String expression="";

        try {
            new CalculatorImpl().evaluate(expression);
            fail("Exception wasn't thrown");
        } catch (IllegalStateException e) {
            System.out.println(e.getLocalizedMessage());
        } catch(UnknownLexemeException e){
            e.printStackTrace();
        }

    }


}
