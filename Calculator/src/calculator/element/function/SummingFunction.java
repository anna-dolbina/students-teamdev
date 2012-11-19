package calculator.element.function;



import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public class SummingFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        checkArguments(args);
        BigDecimal result=new BigDecimal(0);
        for(BigDecimal argument:args){
                result=result.add(argument);
        }
        return result;
    }
}
