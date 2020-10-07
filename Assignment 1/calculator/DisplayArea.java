package calculator;

import javax.swing.JTextField;
import java.util.Stack; 
import java.awt.Color;
import java.awt.Font;

public class DisplayArea {
    public JTextField displayField;

    DisplayArea(){
        createUI();
    }

    private void createUI(){
        displayField = new JTextField("");
        displayField.setLocation(Constants.PADDING, Constants.PADDING);

        Font font = new Font("SansSerif", Font.PLAIN, 20);
        displayField.setFont(font);
        displayField.setBackground(Color.WHITE);

        displayField.setEditable(false);
        displayField.setSize(Constants.WIDTH-2*Constants.PADDING, Constants.DISPLAY_FIELD_HEIGHT);
    }

    private boolean validate(){
        String cur = displayField.getText();
        if(cur.length() == 0){
            return false;
        }

        char last = cur.charAt(cur.length()-1);
        if(!(last >= '0' && last <= '9')){
            return false;
        }

        return true;
    }

    public void appendChar(char c){
        if(!(c >= '0' && c <= '9')){
            if(!validate()){
                System.out.println("Operation not allowed.");
                return;
            }
        }
        
        String cur = displayField.getText();
        if(c >= '0' && c <= '9'){
            cur += c;
        }
        else{
            cur += " "+c+" ";
        }
        displayField.setText(cur);
    }

    public void clearText(){
        displayField.setText("");
    }

    private static boolean hasPrecedence(char op1, char op2){ 
        if (op2 == '(' || op2 == ')'){
            return false; 
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')){
            return false;
        } 
        return true; 
    }

    private static int applyOp(char op, int b, int a) 
    { 
        switch (op){ 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case '*': 
            return a * b; 
        case '/': 
            if (b == 0){
                throw new UnsupportedOperationException("Cannot divide by zero");
            } 
            return a / b; 
        } 
        return 0;
    } 

    public void evaluate(){
        String expression = displayField.getText();
        if(expression.length() == 0){
            return;
        }

        char[] tokens = expression.toCharArray();
        char c = tokens[tokens.length-1];
        if(!(c >= '0' && c <= '9')){
            System.out.println("Operation not allowed.");
            return;
        }

        Stack<Integer> values = new Stack<Integer>(); 
        Stack<Character> ops = new Stack<Character>();

        boolean isNeg = false;
        for(int i = 0; i < tokens.length; i++){
            if(tokens[i] == ' '){
                isNeg = false;
                continue;
            }

            if(tokens[i] == '-' && i+1 < tokens.length && (tokens[i+1] >= '0' && tokens[i+1] <= '9')){
                isNeg = true;
            }
            else if (tokens[i] >= '0' && tokens[i] <= '9'){ 
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9'){
                    sbuf.append(tokens[i++]);
                }
                if(isNeg){
                    values.push(-Integer.parseInt(sbuf.toString()));
                }
                else{
                    values.push(Integer.parseInt(sbuf.toString()));
                }
                isNeg = false;
            }
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') { 
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())){
                    values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
                }

                ops.push(tokens[i]);
                isNeg = false;
            }
        }

        while (!ops.empty()){
            values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
        }

        displayField.setText(Integer.toString(values.pop()));
    }
}
