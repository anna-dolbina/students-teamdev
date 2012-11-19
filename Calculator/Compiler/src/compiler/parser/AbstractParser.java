package compiler.parser;

import compiler.exception.CompilationException;
import compiler.exception.IllegalActionException;
import compiler.exception.UnknownActionException;
import compiler.lexer.Lexeme;

import java.util.NoSuchElementException;


public abstract class AbstractParser<LexemeType extends Enum,
        ContextState extends Enum,
        Context extends ParserContext<LexemeType,ContextState,Result>,
        Table extends ActionTable<LexemeType,ContextState>,
        Result> implements Parser<LexemeType,ContextState,Context,Result>
     {
         public Result run(Context context) throws CompilationException {

             try{
             while(moveForward(context)){}
             return context.getResult();
             }catch (Exception e){
                 throw new CompilationException(context.getCurrentPosition(),e.getMessage());
             }
         }

         private boolean moveForward(Context context) throws UnknownActionException, IllegalActionException {
            final Table actionTable= getActionTable();

            ContextState state;
            try{
                   state=context.getCurrentState();
            }catch(NoSuchElementException e){
                return false;
            }
            if(state==null) return false;
            final Lexeme<LexemeType> lexeme=context.getCurrentLexeme();
            final Action action=actionTable.getAction(lexeme, state);
            boolean result;
            result=action.performAction(context);

            return result;

         }

         protected abstract Table getActionTable();


     }
