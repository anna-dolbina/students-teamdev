package compiler;

import compiler.exception.CompilationException;
import compiler.lexer.AbstractLexer;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeRecognizer;
import compiler.lexer.Lexer;
import compiler.parser.AbstractParser;
import compiler.parser.ActionTable;
import compiler.parser.Parser;
import compiler.parser.ParserContext;

import java.lang.*;
import java.util.ArrayList;

/**
 */
public abstract class AbstractCompiler<LexemeType extends Enum,
        LexemeRecognizerImpl extends LexemeRecognizer<LexemeType>,
        ActionTableImpl extends ActionTable<LexemeType,ContextState>,
        ParserContextImpl extends ParserContext<LexemeType,ContextState,Result>,
        ContextState extends Enum,
        Result>
        implements Compiler<Result> {

    @Override
    public Result compile(String input) throws CompilationException {
        final Lexer<LexemeType> lexer=new AbstractLexer<LexemeType,LexemeRecognizerImpl>(){

            @Override
            protected LexemeRecognizerImpl getLexemeRecognizer() {
                return getLexemeRecognizerImpl();
            }
        };
        final ParserContextImpl context=getParserContextImpl();

        final Parser<LexemeType,ContextState,ParserContextImpl,Result> parser=
                new AbstractParser<LexemeType,ContextState, ParserContextImpl, ActionTableImpl,Result>(){
                    @Override
                    protected ActionTableImpl getActionTable() {
                        return getActionTableImpl();
                    }
        };
        ArrayList<Lexeme<LexemeType>> lexemeStream =lexer.generateLexemeStream(input);
        context.init(lexemeStream, getActionTableImpl().getStartContextState());

        return parser.run(context);
    }


    protected abstract LexemeRecognizerImpl getLexemeRecognizerImpl() ;
    protected abstract ActionTableImpl getActionTableImpl();
    protected abstract ParserContextImpl getParserContextImpl();
}
