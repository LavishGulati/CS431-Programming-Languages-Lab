package calculator2;

// Libraries required by the program
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

// Number area contains all the numerical characters
public class NumberArea {
    public JPanel numberPanel;
    // List of numerical buttons
    public ArrayList<JButton> numberKeys;

    NumberArea(){
        numberKeys = new ArrayList<>();
        createUI();
    }

    // Creates UI for the number area
    private void createUI(){
        // Grid layout for the panel
        GridLayout layout = new GridLayout(4, 3);
        layout.setHgap(Constants.PADDING);
        layout.setVgap(Constants.PADDING);

        // Set size and location
        numberPanel = new JPanel(layout);
        numberPanel.setLocation(Constants.PADDING, Constants.DISPLAY_FIELD_HEIGHT+2*Constants.PADDING);
        numberPanel.setSize(Constants.WIDTH-2*Constants.PADDING, 4*Constants.FIELD_HEIGHT + 3*Constants.PADDING);

        // Initialize all the numerical buttons
        for(int i = 0; i < 10; i++){
            JButton numberKey = new JButton(Integer.toString(i));
            numberKey.setBackground(Color.YELLOW);
            numberKey.setOpaque(false);
            numberKeys.add(numberKey);
        }

        // Add the buttons to the panel
        for(int i = 7; i > 0; i -= 3){
            for(int j = i; j < i+3; j++){
                numberPanel.add(numberKeys.get(j));
            }
        }
        numberPanel.add(numberKeys.get(0));
    }
}
