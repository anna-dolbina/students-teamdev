package calculator.element.operator;

import java.math.BigDecimal;

public class SubtractionBinaryOperator extends AbstractBinaryOperator {

    public SubtractionBinaryOperator(int priority) {
        super(priority);
    }

    @Override
    public BigDecimal calculate(BigDecimal left, BigDecimal right) {

        checkOperands(left, right);
        return left.subtract(right);
    }
}
