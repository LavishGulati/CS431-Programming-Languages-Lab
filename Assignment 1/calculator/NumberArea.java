package calculator;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

public class NumberArea {
    public JPanel numberPanel;
    public ArrayList<JButton> numberKeys;

    NumberArea(){
        numberKeys = new ArrayList<>();
        createUI();
    }

    private void createUI(){
        GridLayout layout = new GridLayout(4, 3);
        layout.setHgap(Constants.PADDING);
        layout.setVgap(Constants.PADDING);

        numberPanel = new JPanel(layout);
        numberPanel.setLocation(Constants.PADDING, Constants.DISPLAY_FIELD_HEIGHT+2*Constants.PADDING);
        numberPanel.setSize(Constants.WIDTH-2*Constants.PADDING, 4*Constants.FIELD_HEIGHT + 3*Constants.PADDING);

        for(int i = 0; i < 10; i++){
            JButton numberKey = new JButton(Integer.toString(i));
            numberKey.setBackground(Color.YELLOW);
            numberKey.setOpaque(false);
            numberKeys.add(numberKey);
        }

        for(int i = 7; i > 0; i -= 3){
            for(int j = i; j < i+3; j++){
                numberPanel.add(numberKeys.get(j));
            }
        }
        numberPanel.add(numberKeys.get(0));
    }
}
