package calculator;

import compiler.exception.UnknownLexemeException;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeRecognizer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 22:53
 *
 */
public class CalculatorLexemeRecognizer implements LexemeRecognizer<CalculatorLexeme> {
    private static final Map<String, CalculatorLexeme>
            LEXEMS = new HashMap<String,CalculatorLexeme>() {{
        put("+", CalculatorLexeme.PLUS);
        put("-", CalculatorLexeme.MINUS);
        put("*", CalculatorLexeme.MULTIPLY);
        put("/", CalculatorLexeme.DIVIDE);
        put("^", CalculatorLexeme.POWER);
        put("(", CalculatorLexeme.BRACKET_OPEN);
        put(")", CalculatorLexeme.BRACKET_CLOSE);
        put(",", CalculatorLexeme.COMMA);
        put("sum", CalculatorLexeme.FUNCTION);
        put("min", CalculatorLexeme.FUNCTION);
        put("max", CalculatorLexeme.FUNCTION);
        put("avg", CalculatorLexeme.FUNCTION);
    }};


    private static final DecimalFormat NUMBER_PARSER =
            new DecimalFormat("0.0",new DecimalFormatSymbols(new Locale("en", "EN")) {{this.setDecimalSeparator('.');}});

    @Override
    public Lexeme<CalculatorLexeme> recognizeLexeme(String context, int currentPosition) throws UnknownLexemeException {
        String parseString=context.substring(currentPosition);
        if(parseString.isEmpty()) return new Lexeme<CalculatorLexeme>(CalculatorLexeme.EOF,"");

        for(String representation:LEXEMS.keySet()){

            if(parseString.startsWith(representation)){

                return new Lexeme<CalculatorLexeme>(LEXEMS.get(representation),representation);
            }
        }
        NUMBER_PARSER.setParseIntegerOnly(false);
        Number number=null;
        try {
            number = NUMBER_PARSER.parse(parseString);
        } catch (ParseException e) {
            throw new UnknownLexemeException(currentPosition,context);
        }

        return new Lexeme<CalculatorLexeme>(CalculatorLexeme.NUMBER,number.toString());
    }

    @Override
    public Lexeme<CalculatorLexeme> getEndOfInputLexeme() {
        return new Lexeme<CalculatorLexeme>(CalculatorLexeme.EOF,"");
    }
}
