package calculator;

import compiler.exception.CompilationException;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 15.11.12
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public interface Calculator {
    public BigDecimal evaluate(String input) throws CompilationException;
}
