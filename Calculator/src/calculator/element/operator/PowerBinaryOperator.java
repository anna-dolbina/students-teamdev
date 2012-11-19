package calculator.element.operator;

import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public class PowerBinaryOperator extends AbstractBinaryOperator {
    public PowerBinaryOperator(int priority) {
        super(priority);
    }

    @Override
    public BigDecimal calculate(BigDecimal left, BigDecimal right) throws CalculationException, CalculationException {
        checkOperands(left, right);
        if(right.doubleValue()-right.intValue()!=0.0) throw new CalculationException("A power can't be double");
        return left.pow(right.intValue());
    }
}
