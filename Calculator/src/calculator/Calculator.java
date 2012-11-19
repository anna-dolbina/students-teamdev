package calculator;

import compiler.exception.CompilationException;

import java.math.BigDecimal;


public interface Calculator {
    public BigDecimal evaluate(String input) throws CompilationException;
}
