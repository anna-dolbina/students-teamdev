package сalculator;

import parser.AbstractParser;


import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorParser extends AbstractParser<CalculatorLexem,
        MachineState,
        CalculatorParserContext,
        CalculatorActionTable,
        BigDecimal> {
    private final CalculatorActionTable actionTable=new CalculatorActionTable();
    @Override
    protected CalculatorActionTable getActionTable() {
        return actionTable;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void deadlock(Exception e) {
        e.printStackTrace();
    }
}
