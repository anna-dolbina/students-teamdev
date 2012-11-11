package сalculator.parser.function;



import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class MaximumFunction extends AbstractFunction {
    @Override
    public BigDecimal calculate(BigDecimal... args) throws CalculationException {
        checkArguments(args);
        BigDecimal result=args[0];
        for(BigDecimal argument:args){
            if(argument.compareTo(result)>0){
                result=argument;
            }
        }
        return result;
    }
}
