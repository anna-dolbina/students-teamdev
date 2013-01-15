import calculator.Calculator;
import calculator.CalculatorImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;


public class CalculatorFrame extends JFrame {
    private JLabel instructionsLabel=null;
    private JTextField inputField=null;
    private JTextArea resultField=null;
    private final Calculator calculatingModule=new CalculatorImpl();

    public CalculatorFrame() {
        super();
       initialize();
    }
    private void initialize() {
        this.setSize(325, 280);
        this.setTitle("Calculator 1.0");
        this.setLayout(null);
        this.add(getInstructionsLabel());
        this.add(getInputField());
        this.add(getResultField());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public JComponent getInstructionsLabel() {
        if(instructionsLabel==null){
            instructionsLabel=new JLabel("Enter an expression to evaluate:");
            instructionsLabel.setBounds(0,0,318,25);
        }
        return instructionsLabel;
    }



    public JTextField getInputField() {
        if(inputField==null){
            inputField=new JTextField();
            inputField.setBounds(0,25,318,25);
            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        BigDecimal result=calculatingModule.evaluate(getInputField().getText());
                        getResultField().setText("Result:\n"+result.doubleValue());
                    } catch (compiler.exception.CompilationException e1) {
                        getResultField().setText(e1.getLocalizedMessage());
                        getInputField().setCaretPosition(e1.getErrorPosition());
                    }
                }
            });
        }
        return inputField;
    }

    public JTextArea getResultField() {
        if(resultField==null){
            resultField=new JTextArea("Results:");
            resultField.setBounds(0,52,315,188);
            resultField.setEditable(false);
        }
        return resultField;
    }


}