package calculator.element.function;



import calculator.element.exception.CalculationException;

import java.math.BigDecimal;


public interface Function {
    BigDecimal calculate(BigDecimal... args) throws CalculationException;
}
