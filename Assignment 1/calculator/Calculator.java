package calculator;

// Libraries required by the program
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Calculator 1.0
public class Calculator extends JFrame implements KeyListener {
    DisplayArea displayArea;
    NumberArea numberArea;
    FunctionArea functionArea;
    // Scanner highlights the number area and function area
    Scanner scanner;

    // Constructor for Calculator
    public Calculator(){
        super("Calculator 1.0");
        // Creates UI for the calculator
        createUI();
        scanner = new Scanner(this);
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
        scanner.execute();
    }

    // Handle all the key press events
    public void keyPressed(KeyEvent e){
        // Enter key
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            // If scanner is highlighting number area
            if(scanner.stage == 0){
                /* Appends the numerical character to the display area and
                change the stage to function area */
                char c = (char)(scanner.num_id + '0');
                displayArea.appendChar(c);
                scanner.stage = 1;
            }
            // If scanner is highlighting function area
            else if(scanner.stage == 1){
                /* Calls the required function for the operator and changes the
                stage of scanner */
                switch(scanner.func_id){
                    case 0:
                        displayArea.appendChar('+');
                        scanner.stage = 0;
                        break;
                    case 1:
                        displayArea.appendChar('-');
                        scanner.stage = 0;
                        break;
                    case 2:
                        displayArea.appendChar('*');
                        scanner.stage = 0;
                        break;
                    case 3:
                        displayArea.appendChar('/');
                        scanner.stage = 0;
                        break;
                    case 4:
                        displayArea.evaluate();
                        scanner.stage = 1;
                        break;
                    case 5:
                        displayArea.clearText();
                        scanner.stage = 0;
                        break;
                }
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
        Calculator calculator = new Calculator();
        calculator.start();
    }
}