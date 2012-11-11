package сalculator.parser.operator;

import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class DivisionBinaryOperator extends AbstractBinaryOperator {
    public DivisionBinaryOperator(int priority) {
        super(priority);
    }

    @Override
    public BigDecimal calculate(BigDecimal left, BigDecimal right) throws CalculationException {
        checkOperands(left, right);
        //System.out.println(left+"/"+right);
        if(right.doubleValue()==0.0) throw new CalculationException("A number can't be divided by zero");
        return left.divide(right);
    }
}
