package сalculator.parser.function;



import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class AverageFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        BigDecimal result=new SummingFunction().calculate(args);
        result.divide(new BigDecimal(args.length));
        return result;
    }
}
