package compiler.lexer;

import utils.Pair;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 14.11.12
 * Time: 2:02
 * To change this template use File | Settings | File Templates.
 */
public class Lexeme<LexemeType extends Enum>{
    private Pair<LexemeType,String> lexemeTypeStringPair=null;
    public Lexeme(LexemeType lexemeType, String representation) {
        lexemeTypeStringPair=new Pair(lexemeType, representation);
    }
    public LexemeType getLexemeType(){
        return lexemeTypeStringPair.getLeft();
    }
    public String getRepresentation(){
        return lexemeTypeStringPair.getRight();
    }
    public String toString(){
        return "["+lexemeTypeStringPair.getLeft()+";"+lexemeTypeStringPair.getRight()+"]";
    }
}
