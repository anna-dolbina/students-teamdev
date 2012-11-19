package calculator;

import calculator.element.action.*;
import compiler.exception.UnknownActionException;
import compiler.lexer.Lexeme;
import compiler.parser.Action;
import compiler.parser.ActionTable;
import utils.Pair;

import java.util.HashMap;

public class CalculatorActionTable implements ActionTable<CalculatorLexeme,CalculatorContextState> {
    private final HashMap<Pair<CalculatorLexeme,CalculatorContextState>,AbstractAction> actions=
            new HashMap<Pair<CalculatorLexeme,CalculatorContextState>,AbstractAction>(){
        {
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.NUMBER, CalculatorContextState.EXPRESSION),new ExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.NUMBER, CalculatorContextState.MULTIPLICATION_EXPRESSION),new MultiplicationExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.NUMBER, CalculatorContextState.POWER_EXPRESSION),new PowerExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.NUMBER, CalculatorContextState.ATOM_EXPRESSION),new OperandAddAction());

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_OPEN, CalculatorContextState.EXPRESSION),new ExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_OPEN, CalculatorContextState.MULTIPLICATION_EXPRESSION),new MultiplicationExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_OPEN, CalculatorContextState.POWER_EXPRESSION),new PowerExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_OPEN, CalculatorContextState.ATOM_EXPRESSION),new BracketOpenAction());

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.PLUS, CalculatorContextState.EXPRESSION_CONTINUE),new PlusOperatorAddAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.PLUS, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.PLUS, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.PLUS, CalculatorContextState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MINUS, CalculatorContextState.EXPRESSION_CONTINUE),new MinusOperatorAddAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MINUS, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MINUS, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MINUS, CalculatorContextState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MULTIPLY, CalculatorContextState.EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MULTIPLY, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new MultiplyOperatorAddAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MULTIPLY, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.MULTIPLY, CalculatorContextState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.DIVIDE, CalculatorContextState.EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.DIVIDE, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new DivideOperatorAddAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.DIVIDE, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.DIVIDE, CalculatorContextState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.POWER, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new PowerOperatorAddAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.POWER, CalculatorContextState.EXPRESSION_CONTINUE),new DoNothingAction());

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.FUNCTION, CalculatorContextState.EXPRESSION),new ExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.FUNCTION, CalculatorContextState.MULTIPLICATION_EXPRESSION),new MultiplicationExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.FUNCTION, CalculatorContextState.POWER_EXPRESSION),new PowerExpressionAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.FUNCTION, CalculatorContextState.ATOM_EXPRESSION),new FunctionAddAction());

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.COMMA, CalculatorContextState.ARGUMENT_EXPRESSION_CONTINUE),new FunctionArgumentAddAction());

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.COMMA, CalculatorContextState.EXPRESSION_CONTINUE),new ApplyOperatorAction(1));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.COMMA, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.COMMA, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.COMMA, CalculatorContextState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_CLOSE, CalculatorContextState.ARGUMENT_EXPRESSION_CONTINUE),new FunctionEvaluateAction());

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_CLOSE, CalculatorContextState.EXPRESSION_CONTINUE),new BracketCloseAction(1));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_CLOSE, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new BracketCloseAction(2));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_CLOSE, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.BRACKET_CLOSE, CalculatorContextState.POWER_EXPRESSION_END),new BracketCloseAction(3));

            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.EOF, CalculatorContextState.EXPRESSION_CONTINUE),new ApplyOperatorAction(1));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.EOF, CalculatorContextState.MULTIPLICATION_EXPRESSION_CONTINUE),new ApplyOperatorAction(2));
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.EOF, CalculatorContextState.POWER_EXPRESSION_CONTINUE),new DoNothingAction());
            put(new Pair<CalculatorLexeme,CalculatorContextState>(CalculatorLexeme.EOF, CalculatorContextState.POWER_EXPRESSION_END),new ApplyOperatorAction(3));


        }
    };


    @Override
    public CalculatorContextState getStartContextState() {
        return CalculatorContextState.EXPRESSION;
    }

    @Override
    public Action getAction(Lexeme<CalculatorLexeme> lexeme, CalculatorContextState controlSymbol) throws UnknownActionException {
        Action action=actions.get(new Pair<CalculatorLexeme, CalculatorContextState>(lexeme.getLexemeType(),controlSymbol));
        if(action==null) throw new UnknownActionException("Symbol '"+lexeme.getRepresentation()+ "' cannot be placed here");
        return action;
    }

}
