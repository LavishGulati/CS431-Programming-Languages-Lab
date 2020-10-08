package calculator2;

// Libraries required by the program
import javax.swing.SwingWorker;

// Number Scanner highlights the buttons in a periodic fashion
public class NumberScanner extends SwingWorker {
    private Calculator2 calculator;
    // ID of the current numerical button
    public int id;

    NumberScanner(Calculator2 calculator){
        this.calculator = calculator;
        // Initial ID point to first button in the panel
        id = 0;
    }

    // Do in background
    @Override
    protected String doInBackground() throws Exception {
        while(true){
            try{
                /* Highlight the button represented by number ID for
                SLEEP_TIME ms */
                calculator.numberArea.numberKeys.get(id).setOpaque(true);
                calculator.repaint();
                Thread.sleep(Constants.SLEEP_TIME);
                calculator.numberArea.numberKeys.get(id).setOpaque(false);
                calculator.repaint();
                // Increment number ID to highlight next button
                id = (id+1)%10;
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }
        }
        return null;
    }
}
