package calculator;

// Libraries required by the package
import javax.swing.JTextField;
import java.util.Stack; 
import java.awt.Color;
import java.awt.Font;

// Display Area prints the input buffer and output for the calculator
public class DisplayArea {
    public JTextField displayField;

    DisplayArea(){
        createUI();
    }

     // Creates UI for display field
    private void createUI(){
        // Initialize with empty field
        displayField = new JTextField("");
        displayField.setLocation(Constants.PADDING, Constants.PADDING);

        // Change font size and background color
        Font font = new Font("SansSerif", Font.PLAIN, 20);
        displayField.setFont(font);
        displayField.setBackground(Color.WHITE);

        // Set size and other properties
        displayField.setEditable(false);
        displayField.setSize(Constants.WIDTH-2*Constants.PADDING, Constants.DISPLAY_FIELD_HEIGHT);
    }

    // Checks whether it is safe to append the operator
    private boolean validate(){
        String cur = displayField.getText();
        /* If field is empty, we cannot append an operator (e.g. '+ 2' is
        incorrect) */
        if(cur.length() == 0){
            return false;
        }

        // Cannot append two operators consecutively (e.g. '+ *' is incorrect)
        char last = cur.charAt(cur.length()-1);
        if(!(last >= '0' && last <= '9')){
            return false;
        }

        return true;
    }

    // Appends a character to the display field
    public void appendChar(char c){
        // In case of operator, validate before appending
        if(!(c >= '0' && c <= '9')){
            if(!validate()){
                System.out.println("Operation not allowed.");
                return;
            }
        }
        
        // Append the character and reset the field
        String cur = displayField.getText();
        if(c >= '0' && c <= '9'){
            cur += c;
        }
        else{
            cur += " "+c+" ";
        }
        displayField.setText(cur);
    }

    // Clears the display field
    public void clearText(){
        displayField.setText("");
    }

    // Compares precedence of the operators: *, / have more precedence than +, -
    private static boolean hasPrecedence(char op1, char op2){ 
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')){
            return false;
        } 
        return true; 
    }

    // Applies the operator to the integers and returns the result
    private static int applyOp(char op, int b, int a){ 
        switch (op){ 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case '*': 
            return a * b; 
        case '/':
            // Cannot divide by zero
            if (b == 0){
                throw new UnsupportedOperationException("Cannot divide by zero");
            } 
            return a / b; 
        } 
        return 0;
    } 

    // Evaluates the expression denoted by display field
    public void evaluate(){
        String expression = displayField.getText();
        // If empty expression, do nothing
        if(expression.length() == 0){
            return;
        }

        char[] tokens = expression.toCharArray();
        /* If last token is an operator, throw error and return (e.g. '2 + 3 -'
        is incorrect) */
        char c = tokens[tokens.length-1];
        if(!(c >= '0' && c <= '9')){
            System.out.println("Operation not allowed.");
            return;
        }

        // Stack for operands and operators
        Stack<Integer> values = new Stack<Integer>(); 
        Stack<Character> ops = new Stack<Character>();

        boolean isNeg = false;
        // Iterate over all tokens
        for(int i = 0; i < tokens.length; i++){
            // If space, continue
            if(tokens[i] == ' '){
                isNeg = false;
                continue;
            }

            // Handle negative integer condition
            if(tokens[i] == '-' && i+1 < tokens.length && (tokens[i+1] >= '0' && tokens[i+1] <= '9')){
                isNeg = true;
            }
            // Scan an integer and push it into the stack
            else if (tokens[i] >= '0' && tokens[i] <= '9'){ 
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9'){
                    sbuf.append(tokens[i++]);
                }
                // If negative, push -x
                if(isNeg){
                    values.push(-Integer.parseInt(sbuf.toString()));
                }
                // If positive, push +x
                else{
                    values.push(Integer.parseInt(sbuf.toString()));
                }
                isNeg = false;
            }
            // Scan an operator and compute the result if applicable
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') { 
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())){
                    values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
                }

                ops.push(tokens[i]);
                isNeg = false;
            }
        }

        // Scan the remaining operators and compute the final result
        while (!ops.empty()){
            values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
        }

        // Set the final reult to the display field
        displayField.setText(Integer.toString(values.pop()));
    }
}
