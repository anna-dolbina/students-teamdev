package compiler.lexer;

import compiler.exception.UnknownLexemeException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:15
 * Абстрактный лексер осуществляет преобразование строки в поток лексем с помощью
 * распознавателя лексем.
 */
public abstract class AbstractLexer<LexemeType extends Enum,
        Recognizer extends LexemeRecognizer<LexemeType>> implements Lexer<LexemeType>{

   public  final ArrayList<Lexeme<LexemeType>> generateLexemeStream(String input) throws UnknownLexemeException {

       final Recognizer recognizer=getLexemeRecognizer();
       final ArrayList<Lexeme<LexemeType>> lexemeStream= new ArrayList<Lexeme<LexemeType>>();
       final Lexeme<LexemeType> endLexeme=recognizer.getEndOfInputLexeme();
       int currentPosition=0;
       Lexeme<LexemeType> recognizedLexeme=null;

       currentPosition=skipWhitespaces(input,currentPosition);

       while ((recognizedLexeme=recognizer.recognizeLexeme(input,currentPosition)).getLexemeType()!=endLexeme.getLexemeType()) {
           lexemeStream.add(recognizedLexeme);
           currentPosition+=recognizedLexeme.getRepresentation().length();
           currentPosition=skipWhitespaces(input, currentPosition);
       }
       lexemeStream.add(endLexeme);
       return lexemeStream;


   }

    private int skipWhitespaces(String input, int currentPosition) {

        while (currentPosition < input.length() &&
                Character.isWhitespace(input.charAt(currentPosition))) {
            currentPosition++;
        }
        return currentPosition;
    }

    protected abstract Recognizer getLexemeRecognizer();


}
