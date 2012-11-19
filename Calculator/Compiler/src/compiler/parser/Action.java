package compiler.parser;


import compiler.exception.IllegalActionException;


public interface Action<LexemeType extends Enum,
        ContextState extends Enum,
        Context extends ParserContext<LexemeType, ContextState,Result>,
        Result> {
    public boolean performAction(Context context) throws IllegalActionException;
}
