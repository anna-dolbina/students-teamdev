package compiler;

import compiler.exception.CompilationException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 14.11.12
 * Time: 2:31
 * To change this template use File | Settings | File Templates.
 */
public interface Compiler<Result> {
    public Result compile(String input) throws CompilationException;

}
