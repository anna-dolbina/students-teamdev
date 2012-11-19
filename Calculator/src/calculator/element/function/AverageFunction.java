package calculator.element.function;



import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public class AverageFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        BigDecimal result=new SummingFunction().calculate(args);
        result=result.divide(new BigDecimal(args.length));
        return result;
    }
}
