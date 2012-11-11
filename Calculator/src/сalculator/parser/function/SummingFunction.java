package сalculator.parser.function;



import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class SummingFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        checkArguments(args);
        BigDecimal result=new BigDecimal(0);
        for(BigDecimal argument:args){
                result.add(argument);
        }
        return result;
    }
}
