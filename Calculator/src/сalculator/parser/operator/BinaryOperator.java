package сalculator.parser.operator;

import сalculator.parser.exception.CalculationException;

import java.math.BigDecimal;

public interface BinaryOperator extends Comparable<BinaryOperator> {
    BigDecimal calculate(BigDecimal left, BigDecimal right) throws CalculationException, CalculationException;
    int getPriority();
}
