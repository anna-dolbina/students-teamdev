package сalculator;

import lexer.AbstractLexer;


/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorLexer extends AbstractLexer<CalculatorLexem,CalculatorLexemRecognizer> {
    private final CalculatorLexemRecognizer recognizer=new CalculatorLexemRecognizer();
    @Override
    protected CalculatorLexemRecognizer getLexemRecognizer() {
        return recognizer;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
