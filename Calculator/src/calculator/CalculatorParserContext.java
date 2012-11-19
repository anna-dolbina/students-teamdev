package calculator;

import calculator.element.exception.CalculationException;
import calculator.element.function.Function;
import calculator.element.operator.BinaryOperator;
import compiler.lexer.Lexeme;
import compiler.parser.ParserContext;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.NoSuchElementException;


public class CalculatorParserContext implements ParserContext<CalculatorLexeme,CalculatorContextState,BigDecimal> {
    private final Deque<CalculatorContextState> stateStack = new ArrayDeque<CalculatorContextState>();
    private ArrayList<Lexeme<CalculatorLexeme>> lexemeStream =null;
    private int currentPosition=0;/*The position in the lexeme stream*/

    private final Deque<BigDecimal> operandStack = new ArrayDeque<BigDecimal>();/*Main stack with operands*/
    private final Deque<BinaryOperator> operatorStack = new ArrayDeque<BinaryOperator>();/*Stack with binary operators*/
    private final Deque<Integer> bracketStack = new ArrayDeque<Integer>(); /*Stack with common brackets*/

    private final Deque<Function> functionStack = new ArrayDeque<Function>(); /*Stack with functions*/
    private final Deque<BigDecimal> argumentStack =new ArrayDeque<BigDecimal>(); /*Stack with arguments for functions*/
    private final Deque<Integer> functionBracketStack=new ArrayDeque<Integer>();/*Stack with markers; a marker shows where the arguments for a current function begin in argument stack*/
    private final Deque<Integer> argumentBracketStack = new ArrayDeque<Integer>(); /*Stack with markers; a marker shows where a function argument begins in operand stack */


    @Override
    public void init(ArrayList<Lexeme<CalculatorLexeme>> lexemeStream,CalculatorContextState startState) {
        this.lexemeStream=lexemeStream;
        stateStack.push(startState);
    }

    @Override
    public CalculatorContextState getCurrentState() throws NoSuchElementException{
        return stateStack.pop();
    }

    @Override
    public void setCurrentState(CalculatorContextState state) {
        stateStack.push(state);
    }

    @Override
    public Lexeme<CalculatorLexeme> getCurrentLexeme() {
        return lexemeStream.get(currentPosition);
    }
    @Override
      public BigDecimal getResult() {
        if(operandStack.size()!=1) throw new IllegalStateException("Operand stack must contain only one operand but:"
                +operandStack+"\nOperator stack: "+operatorStack+
                "\n Current lexeme: "+ lexemeStream.get(currentPosition).getLexemeType()+
                "\n Current state stack:"+stateStack);
        return operandStack.peek();
    }

    @Override
    public int getCurrentPosition() {
        int position=0;
        for(int i=0;i<currentPosition;i++){
            position+=lexemeStream.get(i).getRepresentation().length();
        }
        return position;
    }

    public void moveToNextLexeme(){
        currentPosition++;
    }


    public boolean isOperatorStackEmpty(){
        return operatorStack.isEmpty();
    }
    public void openBracket() {
        bracketStack.push(operandStack.size());
    }
    public void addOperand(BigDecimal operand) {
        operandStack.push(operand);
    }
    public void addOperator(BinaryOperator operator) {
        operatorStack.push(operator);
    }

    public void applyOperator() throws CalculationException {
        BinaryOperator operator;
        BigDecimal operand2;
        BigDecimal operand1;
        try{
            operator=operatorStack.pop();
        }catch(NoSuchElementException e){
            throw new CalculationException("Operator stack is empty");
        }
        try{
            operand2=operandStack.pop();
            operand1=operandStack.pop();
        }catch(NoSuchElementException e){
            throw new CalculationException("Cannot apply current operator: too few arguments in stack");
        }
        final BigDecimal result=operator.calculate(operand1,operand2);
        operandStack.push(result);
    }

    //Apply operators with this priority until the nearest opened bracket,
    //                                  or function bracket,
    //                                  or the operator wit another priority,
    //                                  or the beginning of the expression
    public void applyOperatorsWithPriority(int priority) throws CalculationException {
        int lastBracketOpened;
        if(isLastFunctionBracketOpened()) {
            lastBracketOpened=argumentBracketStack.peek();
        }
       else try{
            lastBracketOpened=bracketStack.peek();
        }catch(Exception e){
            lastBracketOpened=0;
        }
        while((!isOperatorStackEmpty())&&(getFirstOperatorPriority()==priority)&&(operandStack.size()!=(lastBracketOpened+1)))
            applyOperator();

    }
    public boolean isLastFunctionBracketOpened(){
        int lastBracketOpened;
        if(argumentBracketStack.isEmpty()) return false;

        try{
            lastBracketOpened=bracketStack.peek();
        }catch(Exception e){
            return true;
        }
        return lastBracketOpened < argumentBracketStack.peek();
    }
    //Apply operators inside brackets and close the bracket
    public void closeBracket() throws CalculationException {
        int lastBracketOpened;

        try{
            lastBracketOpened=bracketStack.pop();
        }catch(NoSuchElementException e){
            throw new CalculationException("Cannot close bracket: missing opening bracket");
        }

        while(operandStack.size()!=lastBracketOpened+1){
            applyOperator();
        }

    }
    public void openFunctionBracket(){
        functionBracketStack.push(argumentStack.size());
        argumentBracketStack.push(operandStack.size());
    }
    public void addFunction(Function function){
        functionStack.push(function);
    }
    public void addArgument(){

        argumentStack.push(operandStack.pop());
    }
    public void applyFunction() throws CalculationException {
        final int lastFunctionBracketOpened=functionBracketStack.pop();
        final BigDecimal[] arguments=new BigDecimal[argumentStack.size()-lastFunctionBracketOpened];
        Function function;
        try{
            function=functionStack.pop();
        }catch(NoSuchElementException e){
           throw new CalculationException("Cannot find function to apply");
        }
        for(int i= argumentStack.size()-lastFunctionBracketOpened-1;i>=0;i--){
            arguments[i]= argumentStack.pop();

        }

        BigDecimal result=function.calculate(arguments);
        operandStack.push(result);
        argumentBracketStack.pop();

    }
    public void closeFunctionBracket() throws CalculationException {
        addArgument();
        applyFunction();
    }


    public int getFirstOperatorPriority() {
        return operatorStack.peek().getPriority();
    }
}

