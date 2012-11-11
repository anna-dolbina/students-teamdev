package сalculator;

import lexer.LexemRecognizer;
import lexer.UnknownLexemException;
import utils.Pair;

import java.text.NumberFormat;
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
public class CalculatorLexemRecognizer implements LexemRecognizer<CalculatorLexem> {
    private static final Map<String, CalculatorLexem>
            LEXEMS = new HashMap<String,CalculatorLexem>() {{
        put("+", CalculatorLexem.PLUS);
        put("-", CalculatorLexem.MINUS);
        put("*", CalculatorLexem.MULTIPLY);
        put("/", CalculatorLexem.DIVIDE);
        put("^", CalculatorLexem.POWER);
        put("(", CalculatorLexem.BRACKET_OPEN);
        put(")", CalculatorLexem.BRACKET_CLOSE);
        put(",", CalculatorLexem.COMMA);
        put("sum", CalculatorLexem.FUNCTION);
        put("min", CalculatorLexem.FUNCTION);
        put("max", CalculatorLexem.FUNCTION);
    }};
    /*-----ВОПРОС------
    Сейчас числа распознаются иначе, чем остальные лексемы. Каким образом можно унифицировать механизм
    распознавания лексемы? Возможно, ввести какие-то регулярные выражения, по которым затем распознавать числа?
     */
    private static final NumberFormat NUMBER_PARSER =
            NumberFormat.getInstance(new Locale("en", "EN"));
    @Override
    public Pair<CalculatorLexem,String> recognizeLexem(String context) throws UnknownLexemException {
        if(context.isEmpty()) return new Pair<CalculatorLexem, String>(CalculatorLexem.EOF,"");
        for(String representation:LEXEMS.keySet()){

            if(context.startsWith(representation)){

                return new Pair<CalculatorLexem,String>(LEXEMS.get(representation),representation);
            }
        }
        NUMBER_PARSER.setParseIntegerOnly(false);
        Number number=null;
        try {
            number = NUMBER_PARSER.parse(context);
        } catch (ParseException e) {
            throw new UnknownLexemException(context);
        }

        return new Pair<CalculatorLexem,String>(CalculatorLexem.NUMBER,number.toString());
    }

    @Override
    public Pair<CalculatorLexem, String> getEndOfInputLexem() {
        return new Pair<CalculatorLexem, String>(CalculatorLexem.EOF,"");
    }
}
