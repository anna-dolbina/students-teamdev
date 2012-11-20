import calculator.Calculator;
import calculator.CalculatorImpl;
import compiler.exception.CompilationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;


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
            e.printStackTrace();
        } catch (CompilationException e) {
                e.printStackTrace();
        }
    }
}
