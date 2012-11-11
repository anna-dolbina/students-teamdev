package lexer;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import utils.Pair;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:15
 * Абстрактный лексер осуществляет преобразование строки в поток лексем с помощью
 * распознавателя лексем.
 */
public abstract class AbstractLexer<Lexem extends Enum,
        Recognizer extends LexemRecognizer<Lexem>>{

   public  final ArrayList<Pair<Lexem,String>> generateLexemStream(String input) throws UnknownLexemException {

       final Recognizer recognizer=getLexemRecognizer();
       final ArrayList<Pair<Lexem,String>> lexemStream=new  ArrayList<Pair<Lexem,String>>();
       final Pair<Lexem,String> endLexem=recognizer.getEndOfInputLexem();
       Pair<Lexem,String> recognizedLexem=null;

       input=skipWhitespaces(input);

       while ((recognizedLexem=recognizer.recognizeLexem(input)).getLeft()!=endLexem.getLeft()) {
           lexemStream.add(recognizedLexem);
           input=input.substring(recognizedLexem.getRight().length());
           input=skipWhitespaces(input);
       }
       lexemStream.add(endLexem);
       return lexemStream;

   }

    private String skipWhitespaces(String input) {
        int currentPosition=0;
        while (currentPosition < input.length() &&
                Character.isWhitespace(input.charAt(currentPosition))) {
            currentPosition++;
        }
        return input.substring(currentPosition);
    }

    protected abstract Recognizer getLexemRecognizer();


}
