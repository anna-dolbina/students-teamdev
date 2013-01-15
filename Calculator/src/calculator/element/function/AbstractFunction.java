package calculator.element.function;



import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public abstract class AbstractFunction implements Function{
    protected void checkArguments(BigDecimal... args) throws CalculationException {
        if(args.length==0) {
            throw new CalculationException("Cannot calculate this function of 0 arguments");
        }
        for(BigDecimal argument:args){
            if(argument==null)
                throw new CalculationException("One of function arguments is null.");
        }
    }
}
