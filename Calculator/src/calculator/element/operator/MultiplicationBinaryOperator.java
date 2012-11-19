package calculator.element.operator;

import java.math.BigDecimal;

public class MultiplicationBinaryOperator extends AbstractBinaryOperator {

    public MultiplicationBinaryOperator(int priority) {
        super(priority);
    }

    @Override
    public BigDecimal calculate(BigDecimal left, BigDecimal right) {

        checkOperands(left, right);
        return left.multiply(right);
    }
}
