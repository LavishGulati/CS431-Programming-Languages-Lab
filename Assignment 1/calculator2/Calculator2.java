package calculator2;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Calculator2 extends JFrame implements KeyListener {
    DisplayArea displayArea;
    NumberArea numberArea;
    FunctionArea functionArea;
    NumberScanner numberScanner;
    FunctionScanner functionScanner;

    public Calculator2(){
        super("Calculator 2.0");
        createUI();
        numberScanner = new NumberScanner(this);
        functionScanner = new FunctionScanner(this);
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
        numberScanner.execute();
        functionScanner.execute();
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            char c = (char)(numberScanner.id + '0');
            displayArea.appendChar(c);
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){
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
        Calculator2 calculator = new Calculator2();
        calculator.start();
    }
}