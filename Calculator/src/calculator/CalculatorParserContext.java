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

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorParserContext implements ParserContext<CalculatorLexeme,CalculatorContextState,BigDecimal> {
    private final Deque<CalculatorContextState> controlSymbols = new ArrayDeque<CalculatorContextState>();
    private ArrayList<Lexeme<CalculatorLexeme>> lexemeStream =null;
    private int currentPosition=0;
    private final Deque<BigDecimal> operandStack = new ArrayDeque<BigDecimal>();
    private final Deque<BinaryOperator> operatorStack = new ArrayDeque<BinaryOperator>();
    private final Deque<Function> functionStack = new ArrayDeque<Function>();
    private final Deque<Integer> functionBracketStack=new ArrayDeque<Integer>();
    private final Deque<BigDecimal>argumentsStack=new ArrayDeque<BigDecimal>();
    private final Deque<Integer> bracketStack = new ArrayDeque<Integer>();
    private final Deque<Integer> argumentBracketStack = new ArrayDeque<Integer>();


    @Override
    public void init(ArrayList<Lexeme<CalculatorLexeme>> lexemeStream,CalculatorContextState startState) {
        this.lexemeStream=lexemeStream;
        controlSymbols.push(startState);
    }

    @Override
    public CalculatorContextState getCurrentState() throws NoSuchElementException{
        System.out.println("---> "+controlSymbols);
        return controlSymbols.pop();
    }

    @Override
    public void setCurrentState(CalculatorContextState state) {
        controlSymbols.push(state);
        System.out.println(controlSymbols);
    }

    @Override
    public Lexeme<CalculatorLexeme> getCurrentLexeme() {
        return lexemeStream.get(currentPosition);
    }
    @Override
      public BigDecimal getResult() {
        if(operandStack.size()!=1) throw new IllegalStateException("Operand stack must contain only one operand but:"
                +operandStack+"\nOperator stack: "+operatorStack+
                "\n Current lexem: "+ lexemeStream.get(currentPosition).getLexemeType());
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
        System.out.println(getCurrentLexeme().getLexemeType()+" "+getCurrentLexeme().getRepresentation());
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
    //Свернуть одноприоритетные операторы до ближайшей скобки
    //                                  или оператора с другим приоритетом,
    //                                  или начала выражения
    public void applyOperatorsWithPriority(int priority) throws CalculationException {
        int previousSize;
        try{
            previousSize=bracketStack.peek();
        }catch(Exception e){
            previousSize=0;
        }
        while((!isOperatorStackEmpty())&&(getFirstOperatorPriority()==priority)&&(operandStack.size()!=previousSize+1))
            applyOperator();

    }
    //Свернуть операторы внутри скобок и закрыть скобку
    public void closeBracket() throws CalculationException {
        int previousSize, lastArgumentStart=0;
        try{
            lastArgumentStart=argumentBracketStack.peek();
        }catch(Exception e){}
        try{
          previousSize=bracketStack.pop();
        }catch(NoSuchElementException e){
            throw new CalculationException("Cannot close bracket: missing opening bracket");
        }

        while(operandStack.size()!=previousSize+1){
            applyOperator();
        }

    }
    public void openFunctionBracket(){
        functionBracketStack.push(argumentsStack.size());
        argumentBracketStack.push(operandStack.size());
    }
    public void addFunction(Function function){
        functionStack.push(function);
    }
    public void addArgument(){

        argumentsStack.push(operandStack.pop());
        System.out.println(argumentsStack);
    }
    public void applyFunction() throws CalculationException {

        final int previousSize=functionBracketStack.pop();
        final BigDecimal[] arguments=new BigDecimal[argumentsStack.size()-previousSize];
        Function function;
        try{
            function=functionStack.pop();
        }catch(NoSuchElementException e){
           throw new CalculationException("Cannot find function to apply");
        }
        for(int i=argumentsStack.size()-previousSize-1;i>=0;i--){
            arguments[i]=argumentsStack.pop();
        }
        BigDecimal result=function.calculate(arguments);
        argumentsStack.push(result);
        argumentBracketStack.pop();

    }
    public void closeFunctionBracket() throws CalculationException {
        applyFunction();
        if(isFunctionStackEmpty()){
            operandStack.push(argumentsStack.pop());
        }
    }

    private boolean isFunctionStackEmpty() {
        return functionStack.isEmpty();
    }


    public int getFirstOperatorPriority() {
        return operatorStack.peek().getPriority();
    }
}

