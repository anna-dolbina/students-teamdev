package сalculator.parser.function;



import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public interface Function {
    BigDecimal calculate(BigDecimal... args) throws CalculationException;
}
