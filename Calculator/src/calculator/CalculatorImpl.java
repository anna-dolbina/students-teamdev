package calculator;

import compiler.*;
import compiler.Compiler;
import compiler.exception.CompilationException;

import java.math.BigDecimal;

/**
 */
public class CalculatorImpl implements Calculator{
    private static final CalculatorLexemeRecognizer lexemeRecognizer=new CalculatorLexemeRecognizer();
    private static final CalculatorActionTable actionTable=new CalculatorActionTable();
    private static final CalculatorParserContext parserContext=new CalculatorParserContext();

    private static final Compiler<BigDecimal>
            calculatorCompiler=new AbstractCompiler<CalculatorLexeme,
                    CalculatorLexemeRecognizer,
                    CalculatorActionTable,
                    CalculatorParserContext,
            CalculatorContextState,
                    BigDecimal>(){

        @Override
        protected CalculatorLexemeRecognizer getLexemeRecognizerImpl() {
            return lexemeRecognizer;
        }

        @Override
        protected CalculatorActionTable getActionTableImpl() {
            return actionTable;
        }

        @Override
        protected CalculatorParserContext getParserContextImpl() {
            return parserContext;
        }
    };
    public BigDecimal evaluate( String expression) throws CompilationException {

        final BigDecimal result=calculatorCompiler.compile(expression);
        return result;
    }
}
