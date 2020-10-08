package calculator2;

// Libraries required by the program
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Calculator 2.0
public class Calculator2 extends JFrame implements KeyListener {
    DisplayArea displayArea;
    NumberArea numberArea;
    FunctionArea functionArea;
    // Number Scanner highlights the number area
    NumberScanner numberScanner;
    // Function Scanner highlights the function area
    FunctionScanner functionScanner;

    // Constructor for Calculator2
    public Calculator2(){
        super("Calculator 2.0");
        // Creates UI for the calculator
        createUI();
        numberScanner = new NumberScanner(this);
        functionScanner = new FunctionScanner(this);
    }

    /* Creates UI for the calculator which consists of display area, number
    area and function area */
    private void createUI(){
        // Sets size of the frame
        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        // Clears layout of the frame
        this.getContentPane().setLayout(null);

        // Adds display area
        displayArea = new DisplayArea();
        this.add(displayArea.displayField);
        
        // Adds number area
        numberArea = new NumberArea();
        this.add(numberArea.numberPanel);

        // Adds function area
        functionArea = new FunctionArea();
        this.add(functionArea.functionPanel);

        // Setting other properties of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    // Starts the calculator
    void start(){
        numberScanner.execute();
        functionScanner.execute();
    }

    // Handle all the key press events
    public void keyPressed(KeyEvent e){
        // Enter key
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            // Appends the numerical character to the display area
            char c = (char)(numberScanner.id + '0');
            displayArea.appendChar(c);
        }
        // Space key
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            // Calls the required function for the operator
            switch(functionScanner.id){
                case 0:
                    displayArea.appendChar('+');
                    break;
                case 1:
                    displayArea.appendChar('-');
                    break;
                case 2:
                    displayArea.appendChar('*');
                    break;
                case 3:
                    displayArea.appendChar('/');
                    break;
                case 4:
                    displayArea.evaluate();
                    break;
                case 5:
                    displayArea.clearText();
                    break;
            }
        }

        this.repaint();
    }

    public void keyReleased(KeyEvent e){
        
    }

    public void keyTyped(KeyEvent e){
        
    }

    public static void main(String[] args){
        // Initializes the calculator and starts it
        Calculator2 calculator = new Calculator2();
        calculator.start();
    }
}