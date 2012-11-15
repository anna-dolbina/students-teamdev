package compiler.parser;

import compiler.exception.CompilationException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 14.11.12
 * Time: 2:50
 * To change this template use File | Settings | File Templates.
 */
public interface Parser<LexemeType extends Enum,
        ContextState extends Enum,
        Context extends ParserContext<LexemeType,ContextState,Result>,
        Result> {
    public Result run(Context context) throws CompilationException;
}
