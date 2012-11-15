package calculator.element.operator;

import java.math.BigDecimal;

public class AdditionBinaryOperator extends AbstractBinaryOperator {

    public AdditionBinaryOperator(int priority) {
        super(priority);
    }

    @Override
    public BigDecimal calculate(BigDecimal left, BigDecimal right) {

        checkOperands(left, right);
        System.out.println(left+"+"+right);
        return left.add(right);
    }
}
