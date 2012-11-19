package compiler.lexer;

import utils.Pair;


public class Lexeme<LexemeType extends Enum>{
    private Pair<LexemeType,String> lexemeTypeStringPair=null;
    public Lexeme(LexemeType lexemeType, String representation) {
        lexemeTypeStringPair=new Pair<LexemeType,String>(lexemeType, representation);
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
