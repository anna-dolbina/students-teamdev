import calculator.Calculator;
import calculator.CalculatorImpl;
import compiler.exception.CompilationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorConsoleClient {
    public static void main(String[] args){
 try {
            System.out.println("Введите выражение для вычисления.");
            BufferedReader input= new BufferedReader(new InputStreamReader(System.in));
            String expression=input.readLine();
            final Calculator calculator=new CalculatorImpl();
            BigDecimal result= calculator.evaluate(expression);
            System.out.println("Результат вычислений: "+result.doubleValue());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CompilationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
