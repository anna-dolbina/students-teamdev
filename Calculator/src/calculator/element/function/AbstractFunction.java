package calculator.element.function;



import calculator.element.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFunction implements Function{
    protected void checkArguments(BigDecimal... args) throws CalculationException {
        if(args.length==0) {
            throw new CalculationException("Cannot calculate calculator.element.function of 0 arguments");
        }
        for(BigDecimal argument:args){
            if(argument==null)
                throw new NullPointerException("One of operands is null.");
        }
    }
}
