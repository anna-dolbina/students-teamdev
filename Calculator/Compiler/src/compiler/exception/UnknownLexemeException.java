package compiler.exception;


public class UnknownLexemeException extends CompilationException{
    public UnknownLexemeException(int position, String context){
        super(position,"Can't recognize lexeme at the beginning of "+context.substring(position));
    }
}
