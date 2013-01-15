package calculator.element.operator;

import java.math.BigDecimal;

abstract public class AbstractBinaryOperator
        implements BinaryOperator {

    private final int priority;

    protected AbstractBinaryOperator(int priority) {
        this.priority = priority;
    }

    protected void checkOperands(BigDecimal left, BigDecimal right) {

        if (left == null) {
            throw new NullPointerException("Left operand is null.");
        }
        if (right == null) {
            throw new NullPointerException("Right operand is null.");
        }
    }

    @Override
    public int compareTo(BinaryOperator o) {
        final AbstractBinaryOperator operator =
                (AbstractBinaryOperator) o;
        return new Integer(priority).compareTo(operator.priority);
    }
    public int getPriority(){
        return this.priority;
    }
}
