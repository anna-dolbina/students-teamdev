package calculator.element.function;



import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public class MinimumFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        checkArguments(args);
        BigDecimal result=args[0];
        for(BigDecimal argument:args){
            if(argument.compareTo(result)<0){
                result=argument;
            }
        }
        return result;
    }
}
