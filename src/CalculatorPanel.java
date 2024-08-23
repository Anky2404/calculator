import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.*;
import static java.awt.Font.PLAIN;
import static javax.swing.SwingConstants.RIGHT;

@SuppressWarnings("serial")
public class CalculatorPanel extends JPanel implements ButtonConstants {
    private static final int CALC_WIDTH = 265;
    private static final int CALC_HEIGHT = 375;
    private static final String type="Arial";

    private static final int style=Font.BOLD;

    private static final int size=32;

    private JLabel result;
    private JLabel calculation;
    final JButton[] buttons= new JButton[29];
    String[] names = {"MC","MR","M+","M-","MS","%","CE","C",DELETE,RECIPROCAL,X_SQUARED,SQUARE_ROOT,DIVISION,"7","8","9",MULTIPLICATION,"4","5","6",SUBTRACTION,"1","2","3",ADDITION,CHANGE_SIGN,"0",DECIMAL,EQUALS};
    private JPanel memoryPanel;
    private JPanel calcPanel;

    /**
     * Constructor for the Calculator Panel: Sets up the GUI
     */


    public CalculatorPanel() {
        init();
    }

    private void init() {
        this.setBackground(LIGHT_GRAY);
        this.setPreferredSize(new Dimension(CALC_WIDTH, CALC_HEIGHT));

        //create result JLabel to display the calculator operations
        result = new JLabel("", RIGHT);
        result.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        result.setPreferredSize(new Dimension(CALC_WIDTH-5,50));
        result.setFont(new Font(type, style, size));
        result.setBackground(WHITE);
        result.setOpaque(true);

        //create Calculation JLabel to hold the information for the current calculation
        calculation = new JLabel("Anky Singh Humraj",RIGHT);
        calculation.setBackground(LIGHT_GRAY);
        calculation.setPreferredSize(new Dimension(CALC_WIDTH-5,20));
        calculation.setFont(new Font("Helvetica",PLAIN,12));

        //create memory panel to add memory buttons
        memoryPanel=new JPanel();
        memoryPanel.setLayout(new GridLayout(1, 1,1,1));

        //create calcPanel to add  button
        calcPanel = new JPanel();
        calcPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        calcPanel.setLayout(new GridLayout(7, 4, 1, 1));

        for(int i=0; i< names.length; i++) {
            buttons[i] = new JButton(names[i]);
            // Set default size and font
            buttons[i].setPreferredSize(new Dimension(65, 40));
            buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 13));
            buttons[i].setForeground(Color.DARK_GRAY);
            calcPanel.add(buttons[i]);

            // add memory button in memory panel
            if (isMemoryButton(names[i])) {
                // Memory buttons
                buttons[i].setPreferredSize(new Dimension(48, 30));
                buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 12));
                buttons[i].setBackground(Color.LIGHT_GRAY);
                memoryPanel.add(buttons[i]);
            }
            //set clear button foreground color red
            else if(isClearButton(names[i]))
            {
                buttons[i].setForeground(Color.RED);
            }
            // set numeric buttons font size 13
            else if (isNumericButton(names[i])) {
                // Numeric buttons
                buttons[i].setFont(new Font("Helvetica", Font.BOLD, 13));
            }
            //set equals buttons background color green
            else if (isEqualsButton(names[i])) {
                buttons[i].setBackground(new Color(0.13f, 0.55f, 0.13f));
                buttons[i].setForeground(WHITE);
            }
            // Add the ActionListener to each button
            buttons[i].addActionListener(new ButtonListener());
        }


            this.add(calculation);
            this.add(result);
            this.add(memoryPanel);
            this.add(calcPanel);

    }

    // validation for memory button
    private boolean isMemoryButton(String label) {
        return label.equals("MC") || label.equals("MR")|| label.equals("MS") || label.equals("M+") || label.equals("M-");
    }

    //validation for clear buttons
    private boolean isClearButton(String label) {
        return label.equals("C") || label.equals("CE");
    }

    //validation for numeric buttons
    private boolean isNumericButton(String label) {
        return label.matches("[0-9]");
    }
    private boolean isEqualsButton(String label)
    {
        return label.equals("=");
    }


    // Inner class for button action events
    private class ButtonListener implements ActionListener {
        String num;
            public void actionPerformed(ActionEvent e) {
                // Retrieve the label from the JButton

                String buttonLabel = e.getActionCommand();

                // Use a switch statement to determine the action based on the button label
                switch (buttonLabel) {
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                        // Handle numeric button press
                        handleNumericButtonClick(buttonLabel);
                        break;
                    case ADDITION:
                    case SUBTRACTION:
                    case MULTIPLICATION:
                    case DIVISION:
                    case CHANGE_SIGN:
                    case X_SQUARED:
                    case SQUARE_ROOT:
                    case RECIPROCAL:
                    case  "%":
                        // Handle operator button press
                        handleOperatorButtonClick(buttonLabel);
                        break;
                    case EQUALS:
                        // Handle equals button press
                        handleEqualsButtonClick();
                        break;
                    case "CE":
                    case "C":
                        // Handle clear button press
                       handleClearButtonClick();
                        break;
                    case DELETE:
                        handleDelButtonClick();
                        break;
                    case DECIMAL: { num=result.getText();  result.setText(num+DECIMAL);}
                    break;
                    case "MR":
                    case "MC":
                    case "M+":
                    case "M-":
                    case "MS":
                        // Handle Memory button press
                        handleMemoryButtonClick(buttonLabel);
                    default:
                }
        }
        }
    String op;
    String lastOp;
    double num1;
    double num2;
    double memory=0;
    double lastMemory=0;

            //handle numeric button click
            private void handleNumericButtonClick(String numericButtonLabel) {

            // Get the current text from the result label
            String currentText = result.getText();

            // If the current text is "0" or the result label is empty, set it to the new numeric button label
            if (currentText.equals("0") || currentText.isEmpty()) {
                result.setText(numericButtonLabel);
            }
            else {
                // Append the new numeric button label to the end of the current text
                result.setText(currentText+numericButtonLabel);
                calculation.setText("");
            }
        }


        //handle operation button on click
        private void handleOperatorButtonClick(String operatorButtonLabel) {

            if(op==null)
            {
                // Retrieve the String from the JLabel
                String currentText =result.getText();
                // Parse the String to a double value
                num1 = Double.parseDouble(currentText);
                op=operatorButtonLabel;
                calculation.setText(currentText+op);
                result.setText("0");
            }

            lastOp=operatorButtonLabel;
            if(lastOp.equals("%")&&(op.equals(ADDITION)||op.equals(SUBTRACTION)))
            {
                // Retrieve the String from the JLabel
                String currentText =result.getText();
                // Parse the String to a double value
                num2 = Double.parseDouble(currentText);
                double result1=calculate(num1, num2, lastOp);
                result.setText(String.valueOf(result1));
                num2=result1;
                lastOp=null;
            }
            if(lastOp.equals("%")&&(op.equals(DIVISION)||op.equals(MULTIPLICATION)))
            {
                // Retrieve the String from the JLabel
                String currentText =result.getText();
                // Parse the String to a double value
                num2 = Double.parseDouble(currentText);
                result.setText(String.valueOf(num2/100));
                num2=num2/100;
            }


        }

        //handle Equals button on click
        private void handleEqualsButtonClick() {
            // Retrieve the String from the JLabel
            String currentText = result.getText();
            // Parse the String to a double value
            num2 = Double.parseDouble(currentText);
            if(op!=null) {
                double resultValue = calculate(num1, num2, op);

                 //Display the result on the JLabel

                String resultValue1 = String.valueOf(resultValue);
                resultValue1 = resultValue1.substring(resultValue1.indexOf("."));
                if (resultValue1.equalsIgnoreCase(".0")) {
                    int resultValue2 = (int) Math.round(resultValue);
                    result.setText(String.valueOf(resultValue2));
                }
                else {
                    result.setText(String.valueOf(resultValue));
                }

                //calculation level display
                String currentText1 = String.valueOf(num1);
                currentText1 = currentText1.substring(currentText1.indexOf("."));
                if (currentText1.equalsIgnoreCase(".0")) {
                    @SuppressWarnings("unused")
					int resultValue2 = (int) Math.round(resultValue);
                    if (!currentText.equals("0")) {
                        calculation.setText(String.valueOf(Math.round(num1)) + op + currentText + EQUALS + " ");
                    }
                    else {
                        calculation.setText(Math.round(num1) + op + EQUALS + " ");
                    }
                    if(op.equals("%"))
                    {
                        result.setText(String.valueOf(num1/100));
                        calculation.setText(Math.round(num1)+op+EQUALS+" ");
                    }
                } else {
                    if (!currentText.equals("0"))
                    {
                        calculation.setText(Math.round(num1) + op + currentText + EQUALS + " ");
                    }
                    else {
                        calculation.setText(Math.round(num1) + op + EQUALS + " ");
                    }
                }
            }

            op=null;

        }

        //handle clear button on Click
        private void handleClearButtonClick() {
            // Set num1 and num2 to 0
            num1 = 0;
            num2 = 0;
            // Set the text of the JLabel to '0'
            result.setText("0");
            calculation.setText("");
        }

        //handle Delete Button on Click
        private void handleDelButtonClick() {
            // Get the current text from the result label
            String currentText = result.getText();
            // Check if the current text has more than one character
            if (currentText.length() > 1) {
                // Remove the last character
                result.setText(currentText.substring(0, currentText.length() - 1));
            } else {
                // If there's only one character, set the display to '0'
                result.setText("0");
            }
        }

    // method to calculate the result based on the operator
    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case ADDITION:
                return num1 + num2;
            case SUBTRACTION:
                return num1 - num2;
            case MULTIPLICATION:
                return num1 * num2;
            case DIVISION:
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    // Handle division by zero
                    return Double.POSITIVE_INFINITY;
                }
            case CHANGE_SIGN:
                return -num1;
            case X_SQUARED:
                return Math.pow(num1,2);
            case SQUARE_ROOT:
               return Math.sqrt(num1);
            case RECIPROCAL:
                return 1/num1;
            case "%":
              return num1*num2/100;
                default:
                // Handle unexpected operators
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    //handle memory button on click
    private void handleMemoryButtonClick(String memoryButtonLabel) {
        if(memoryButtonLabel.equals("MS"))
        {
            // Get the current text from the result label
            String currentText = result.getText();
            memory=Double.parseDouble(currentText);
            calculation.setText("M");

        }
        else if (memoryButtonLabel.equals("M+")) {
            // Get the current text from the result label
            String currentText = result.getText();
            num1=Double.parseDouble(currentText);
            if(num1!=0)
            {
               lastMemory=num1;
                memory=memory+lastMemory;
            }
            else {
                memory=memory+lastMemory;
            }
            result.setText("0");
            calculation.setText("M+");
        } else if (memoryButtonLabel.equals("M-")) {
            // Get the current text from the result label
            String currentText = result.getText();
            num1=Double.parseDouble(currentText);
            if(num1!=0.0)
            {
                lastMemory=num1;
                memory=memory-lastMemory;
            }
            else {

                memory=memory-lastMemory;
            }
            result.setText("0");
            calculation.setText("M-");
        } else if (memoryButtonLabel.equals("MR")) {
            result.setText(String.valueOf(memory));
            calculation.setText("MR");

        } else if (memoryButtonLabel.equals("MC")) {
            memory=0;
            result.setText(String.valueOf(0));
            calculation.setText("MC");

        }
    }


}

