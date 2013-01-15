package compiler.parser;

import compiler.exception.CompilationException;


public interface Parser<LexemeType extends Enum,
        ContextState extends Enum,
        Context extends ParserContext<LexemeType,ContextState,Result>,
        Result> {
    public Result run(Context context) throws CompilationException;
}
