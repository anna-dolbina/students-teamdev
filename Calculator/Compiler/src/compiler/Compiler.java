package compiler;

import compiler.exception.CompilationException;


public interface Compiler<Result> {
    public Result compile(String input) throws CompilationException;

}
