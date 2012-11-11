package сalculator.parser.operator;

import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class PowerBinaryOperator extends AbstractBinaryOperator {
    public PowerBinaryOperator(int priority) {
        super(priority);
    }

    @Override
    public BigDecimal calculate(BigDecimal left, BigDecimal right) throws CalculationException, CalculationException {
        checkOperands(left, right);
        //System.out.println(left+"^"+right);
        if(right.doubleValue()-right.intValue()!=0.0) throw new CalculationException("A power can't be double");
        return left.pow(right.intValue());
    }
}
