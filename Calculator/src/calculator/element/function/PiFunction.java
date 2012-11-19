package calculator.element.function;

import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public class PiFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        if(args.length!=0)throw new CalculationException("Pi function must have no arguments");
        return new BigDecimal(Math.PI);
    }
}
