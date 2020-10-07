package calculator;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Calculator extends JFrame implements KeyListener {
    DisplayArea displayArea;
    NumberArea numberArea;
    FunctionArea functionArea;
    Scanner scanner;

    public Calculator(){
        super("Calculator 1.0");
        createUI();
        scanner = new Scanner(this);
    }

    private void createUI(){
        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.getContentPane().setLayout(null);

        displayArea = new DisplayArea();
        this.add(displayArea.displayField);
        
        numberArea = new NumberArea();
        this.add(numberArea.numberPanel);

        functionArea = new FunctionArea();
        this.add(functionArea.functionPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    void start(){
        scanner.execute();
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(scanner.stage == 0){
                char c = (char)(scanner.num_id + '0');
                displayArea.appendChar(c);
                scanner.stage = 1;
            }
            else if(scanner.stage == 1){
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
        Calculator calculator = new Calculator();
        calculator.start();
    }
}