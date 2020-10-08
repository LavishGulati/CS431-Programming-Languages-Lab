package calculator;

// Libraries required by the program
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;

// Function area contains all the operators: +, -, *, /, =, C
public class FunctionArea {
    public JPanel functionPanel;
    // Declare all required buttons
    public JButton addKey;
    public JButton subtractKey;
    public JButton productKey;
    public JButton divisionKey;
    public JButton evaluateKey;
    public JButton clearKey;

    FunctionArea(){
        createUI();
    }

    // Creates UI for the function area
    void createUI(){
        // Grid layout for the panel
        GridLayout layout = new GridLayout(2, 3);
        layout.setHgap(Constants.PADDING);
        layout.setVgap(Constants.PADDING);

        // Set size and location
        functionPanel = new JPanel(layout);
        functionPanel.setLocation(Constants.PADDING, Constants.DISPLAY_FIELD_HEIGHT+6*Constants.PADDING+4*Constants.FIELD_HEIGHT);
        functionPanel.setSize(Constants.WIDTH-2*Constants.PADDING, 2*Constants.FIELD_HEIGHT+Constants.PADDING);

        // Add all the required buttons
        addKey = new JButton("+");
        addKey.setBackground(Color.CYAN);
        addKey.setOpaque(false);
        functionPanel.add(addKey);

        subtractKey = new JButton("-");
        subtractKey.setBackground(Color.CYAN);
        subtractKey.setOpaque(false);
        functionPanel.add(subtractKey);
        
        productKey = new JButton("*");
        productKey.setBackground(Color.CYAN);
        productKey.setOpaque(false);
        functionPanel.add(productKey);

        divisionKey = new JButton("/");
        divisionKey.setBackground(Color.CYAN);
        divisionKey.setOpaque(false);
        functionPanel.add(divisionKey);

        evaluateKey = new JButton("=");
        evaluateKey.setBackground(Color.CYAN);
        evaluateKey.setOpaque(false);
        functionPanel.add(evaluateKey);

        clearKey = new JButton("C");
        clearKey.setBackground(Color.CYAN);
        clearKey.setOpaque(false);
        functionPanel.add(clearKey);
    }
}
