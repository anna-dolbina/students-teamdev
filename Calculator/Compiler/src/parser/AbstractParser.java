package parser;

import utils.Pair;

import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractParser<Lexem extends Enum,
        NonTerminal extends Enum,
        Context extends ParserContext<Lexem,NonTerminal,Result>,
        Table extends ActionTable<Lexem,NonTerminal>,
        Result>
     {
         public Result run(Context context){
             context.init(getActionTable().getStartNonTerminal());
             while(moveForward(context)){}
             return context.getResult();
         }

         private boolean moveForward(Context context){
            final Table actionTable= getActionTable();

            NonTerminal state=null;
            try{
                   state=context.getCurrentState();
            }catch(NoSuchElementException e){
                return false;
            }
            if(state==null) return false;
            final Pair<Lexem,String> lexem=context.getCurrentLexem();
            final Action action=actionTable.getAction(lexem,state);
            boolean result=false;
            try{
                 result=action.performAction(context);
            }catch(IllegalActionException e){
                deadlock(e);
            }
            return result;

         }

         protected abstract Table getActionTable();
         protected abstract void deadlock(Exception e);

     }
