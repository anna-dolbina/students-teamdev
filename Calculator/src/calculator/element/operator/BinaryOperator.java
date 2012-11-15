package calculator.element.operator;

import calculator.element.exception.CalculationException;

import java.math.BigDecimal;

public interface BinaryOperator extends Comparable<BinaryOperator> {
    BigDecimal calculate(BigDecimal left, BigDecimal right) throws CalculationException, CalculationException;
    int getPriority();
}
