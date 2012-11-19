package calculator.element.function;

import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public class SquareRootFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        if(args.length!=1) throw new CalculationException("A square root must take 1 and only 1 argument");
        return new BigDecimal(Math.sqrt(args[0].doubleValue()));
    }
}
