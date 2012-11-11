package сalculator;

import parser.Action;
import parser.ActionTable;
import utils.Pair;
import сalculator.parser.action.*;


import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 11.11.12
 * Time: 1:00
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorActionTable implements ActionTable<CalculatorLexem,MachineState> {
    private final HashMap<Pair<CalculatorLexem,MachineState>,AbstractAction> actions=
            new HashMap<Pair<CalculatorLexem,MachineState>,AbstractAction>(){
        {
            put(new Pair(CalculatorLexem.NUMBER,MachineState.EXPRESSION),new ExpressionAction());
            put(new Pair(CalculatorLexem.NUMBER,MachineState.MULTIPLICATION_EXPRESSION),new MultiplicationExpressionAction());
            put(new Pair(CalculatorLexem.NUMBER,MachineState.POWER_EXPRESSION),new PowerExpressionAction());
            put(new Pair(CalculatorLexem.NUMBER,MachineState.ATOM_EXPRESSION),new OperandAddAction());

            put(new Pair(CalculatorLexem.BRACKET_OPEN,MachineState.EXPRESSION),new ExpressionAction());
            put(new Pair(CalculatorLexem.BRACKET_OPEN,MachineState.MULTIPLICATION_EXPRESSION),new MultiplicationExpressionAction());
            put(new Pair(CalculatorLexem.BRACKET_OPEN,MachineState.POWER_EXPRESSION),new PowerExpressionAction());
            put(new Pair(CalculatorLexem.BRACKET_OPEN,MachineState.ATOM_EXPRESSION),new BracketOpenAction());

            put(new Pair(CalculatorLexem.PLUS,MachineState.EXPRESSION_CONTINUE),new PlusOperatorAddAction());
            put(new Pair(CalculatorLexem.PLUS,MachineState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair(CalculatorLexem.PLUS,MachineState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.PLUS,MachineState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair(CalculatorLexem.MINUS,MachineState.EXPRESSION_CONTINUE),new MinusOperatorAddAction());
            put(new Pair(CalculatorLexem.MINUS,MachineState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair(CalculatorLexem.MINUS,MachineState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.MINUS,MachineState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair(CalculatorLexem.MULTIPLY,MachineState.EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.MULTIPLY,MachineState.MULTIPLICATION_EXPRESSION_CONTINUE),new MultiplyOperatorAddAction());
            put(new Pair(CalculatorLexem.MULTIPLY,MachineState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.MULTIPLY,MachineState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair(CalculatorLexem.DIVIDE,MachineState.EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.DIVIDE,MachineState.MULTIPLICATION_EXPRESSION_CONTINUE),new DivideOperatorAddAction());
            put(new Pair(CalculatorLexem.DIVIDE,MachineState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.DIVIDE,MachineState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair(CalculatorLexem.POWER,MachineState.POWER_EXPRESSION_CONTINUE),new PowerOperatorAddAction());

            put(new Pair(CalculatorLexem.BRACKET_CLOSE,MachineState.EXPRESSION_CONTINUE),new BracketCloseAction(1));
            put(new Pair(CalculatorLexem.BRACKET_CLOSE,MachineState.MULTIPLICATION_EXPRESSION_CONTINUE),new BracketCloseAction(2));
            put(new Pair(CalculatorLexem.BRACKET_CLOSE,MachineState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.BRACKET_CLOSE,MachineState.POWER_EXPRESSION_END),new BracketCloseAction(3));

            put(new Pair(CalculatorLexem.EOF,MachineState.EXPRESSION_CONTINUE),new ApplyOperatorAction(1));
            put(new Pair(CalculatorLexem.EOF,MachineState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair(CalculatorLexem.EOF,MachineState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair(CalculatorLexem.EOF,MachineState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));


        }
    };
    @Override
    public MachineState getStartNonTerminal() {
        return MachineState.EXPRESSION;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Action getAction(Pair<CalculatorLexem, String> lexem, MachineState controlSymbol) throws IllegalStateException {
        Action action=actions.get(new Pair<CalculatorLexem, MachineState>(lexem.getLeft(),controlSymbol));
        if(action==null) throw new IllegalStateException("No action for "+lexem.getLeft()+ " in state "+controlSymbol);
        return action;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
